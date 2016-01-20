package vsp.banks.business.logic.twophasecommit;

import vsp.banks.business.adapter.CloneService;
import vsp.banks.business.adapter.exceptions.NetworkException;
import vsp.banks.business.adapter.interfaces.ICloneService;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.business.logic.bank.interfaces.IBanksLogicLockableMutable;
import vsp.banks.business.logic.twophasecommit.exceptions.ServiceInconsistentException;
import vsp.banks.business.logic.twophasecommit.interfaces.ITwoPhaseCommit;
import vsp.banks.data.entities.Account;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static vsp.banks.helper.ObjectHelper.checkNotNull;
import static vsp.banks.helper.StringHelper.checkNotEmpty;

/**
 * Created by alex on 1/17/16.
 */
public class TwoPhaseCommitLogic implements ITwoPhaseCommit {

  public static final int lockTries = 100;

  public static final int maxTimeToWaitInMs = 7 * 1000;

  public static final int minTimeToWaitInMs = 5 * 1000;

  /**
   * This set contains only remote replicates.
   * To get all replicates use <code>getAllServices()</code>
   */
  Set<ICloneService> remoteCloneServices;

  String ownUri;

  IBanksLogic logic;

  /**
   * Creates a new instance of a two phase commit instance.
   * @param logic as local bank service.
   * @param ownUri to tell other services on request. This uri contains the port.
   */
  public TwoPhaseCommitLogic(IBanksLogic logic, String ownUri) {
    checkNotNull(logic, ownUri);
    checkNotEmpty(ownUri);
    this.logic = logic;
    this.ownUri = ownUri;
    this.remoteCloneServices = new HashSet<>();
  }

  @Override
  public boolean lockBankOnAllServices(String gameId) throws BankNotFoundException,
          NetworkException {
    Set<IBanksLogicLockableMutable> lockedServices = new HashSet<>();
    for (IBanksLogicLockableMutable service : this.getAllServices()) {
      if (!service.lock(gameId)) {
        if (!unlockAll(lockedServices, gameId)) {
          handleLockedNotAbleToUnlock();
        }
        return false;
      }
      lockedServices.add(service);
    }
    return true;
  }

  /**
   * Tries to lock bank identified by gameId on all services.
   * @param gameId to identify bank.
   * @return true when successfully locked bank on all services.
   * @throws BankNotFoundException when the bank does not exist.
   */
  private boolean tryToLockBankOnAllServices(String gameId) throws BankNotFoundException,
          NetworkException {
    int triesToLock = 0;
    while (triesToLock < lockTries) {
      if (lockBankOnAllServices(gameId)) {
        return true;
      }
      triesToLock++;
      Random random = new Random();
      int timeToWait = random.nextInt() % this.maxTimeToWaitInMs;
      timeToWait += minTimeToWaitInMs;
      try {
        Thread.sleep(timeToWait);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  @Override
  public boolean unlockBankOnAllServices(String bankId) throws BankNotFoundException,
          NetworkException {
    for (IBanksLogicLockableMutable service : this.getAllServices()) {
      if (!service.unlock(bankId)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isLocked(String gameId) throws BankNotFoundException, NetworkException {
    for (IBanksLogicLockableMutable service : this.getAllServices()) {
      // Note: This might be a bug.
      if (!service.isLocked(gameId)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Set<String> getUris() {
    Set<String> uris;
    uris = this.remoteCloneServices.stream().map(ICloneService::getUri).collect(toSet());
    uris.add(this.ownUri);
    return uris;
  }

  @Override
  public void setGame(Game game) throws NetworkException {
    checkNotNull(game);
    try {
      if (tryToLockBankOnAllServices(game.getGameid())) {
//        handleTooManyLockTries();
      }
    } catch (BankNotFoundException exception) {
      // no op
    } finally {
      for (IBanksLogicLockableMutable service : getAllServices()) {
        service.setGame(game);
      }
      try {
        unlockBankOnAllServices(game.getGameid());
      } catch (BankNotFoundException e) {
        handleLockedNotAbleToUnlock();
      }
    }
  }

  @Override
  public boolean registerPlayerForGame(String gameId, Account playerAccount)
          throws BankNotFoundException, NetworkException {
    checkNotNull(gameId, playerAccount);
    checkNotEmpty(gameId);
    if (!tryToLockBankOnAllServices(gameId)) {
      handleLockedNotAbleToUnlock();
    }
    for (IBanksLogicLockableMutable service : this.getAllServices()) {
      if (!service.registerPlayerForGame(gameId, playerAccount)) {
        this.unlockBankOnAllServices(gameId);
        return false;
      }
    }
    if (!this.unlockBankOnAllServices(gameId)) {
      handleLockedNotAbleToUnlock();
    }
    return true;
  }

  @Override
  public boolean applyTransferInGame(String gameId, Transfer transfer)
          throws NotFoundException, NetworkException {
    checkNotNull(gameId, transfer);
    checkNotEmpty(gameId);
    if (!logic.transferIsPossible(gameId, transfer)) {
      return false;
    }
    if (!tryToLockBankOnAllServices(gameId)) {
      handleTooManyLockTries();
    }
    for (IBanksLogicLockableMutable replicate : this.getAllServices()) {
      if (!replicate.applyTransferInGame(gameId, transfer)) {
        unlockBankOnAllServices(gameId);
        return false;
      }
    }
    if (!unlockBankOnAllServices(gameId)) {
      handleLockedNotAbleToUnlock();
    }
    return true;
  }

  @Override
  public boolean deleteCloneService(String uri) {
    for (ICloneService service : this.remoteCloneServices) {
      if (service.getUri().equals(uri)) {
        if (this.remoteCloneServices.remove(service)) {
          System.out.println("[" + this.ownUri + "] Removed service with uri: " + uri);
          return true;
        } else {
          throw new RuntimeException("This shouldn't happen");
        }
      }
    }
    return false;
  }


  @Override
  public synchronized boolean registerCloneServices(String uri) {
    if (this.remoteCloneServices.add(new CloneService(uri))) {
      System.out.println("[" + this.ownUri + "] Registered replicate: " + uri);
      return true;
    }
    return false;
  }

  /**
   * Unlocks given bank identified by gameId on every given service.
   * @param services to unlock.
   * @param gameId to unlock on every service.
   * @return true, if and only if all banks were unlocked.
   * @throws BankNotFoundException if and only if, no bank with
   */
  private boolean unlockAll(Set<IBanksLogicLockableMutable> services, String gameId)
          throws BankNotFoundException, NetworkException {
    for (IBanksLogicLockableMutable service : services) {
      // wot, wtf does this not need a negation?!
      if (service.unlock(gameId)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Set<IBanksLogicLockableMutable> getAllServices() {
    Set<IBanksLogicLockableMutable> services = new HashSet<>(this.remoteCloneServices);
    services.add(logic);
    return services;
  }

  @Override
  public synchronized void deleteReplicateOnAllReplicates(String replicateUri) {
    if (!this.deleteCloneService(replicateUri)) {
      System.err.println("[" + ownUri + "] Didn't delete local '" + replicateUri + "'");
    }
    for (ICloneService replicate : this.remoteCloneServices) {
      try {
        replicate.deleteService(replicateUri);
      } catch (Exception exception) {
        String logMessage = "[" + ownUri + "] Couldn't delete on remote service: ";
        System.err.println(logMessage + replicate.getUri());
      }
    }
  }

  private void handleTooManyLockTries() {
    throw new ServiceInconsistentException("Too many lock tries!");
  }

  private void handleLockedNotAbleToUnlock() {
    throw new ServiceInconsistentException("Couldn't unlock replicates which were locked by me.");
  }
}
