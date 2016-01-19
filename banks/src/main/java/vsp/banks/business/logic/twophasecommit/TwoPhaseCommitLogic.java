package vsp.banks.business.logic.twophasecommit;

import vsp.banks.business.adapter.CloneService;
import vsp.banks.business.adapter.interfaces.ICloneService;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.business.logic.bank.interfaces.IBanksLogicLockableMutable;
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

  public static final int maxTriesToLock = 15;

  public static final int maxTimeToWaitInMs = 5000;

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
  }

  @Override
  public boolean lockBankOnAllServices(String gameId) throws BankNotFoundException {
    Set<IBanksLogicLockableMutable> lockedServices = new HashSet<>();
    for (IBanksLogicLockableMutable service : this.getAllServices()) {
      if (!service.lock(gameId)) {
        if (!unlockAll(lockedServices, gameId)) {
          handleLockedNotAbleToUnlock();
        }
        return false;
      }
    }
    return true;
  }

  /**
   * Locks
   * @param gameId
   * @return
   * @throws BankNotFoundException
   */
  public boolean tryToLockBankOnAllServices(String gameId) throws BankNotFoundException {
    int triesToLock = 0;
    while (triesToLock < maxTriesToLock) {
      if (lockBankOnAllServices(gameId)) {
        return true;
      }
      triesToLock++;
      Random random = new Random(System.currentTimeMillis());
      int timeToWait = random.nextInt(this.maxTimeToWaitInMs);
      try {
        Thread.sleep(timeToWait);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  @Override
  public boolean unlockBankOnAllServices(String bankId) throws BankNotFoundException {
    for (IBanksLogicLockableMutable replicate : this.getAllServices()) {
      if (!replicate.unlock(bankId)) {
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
  public void setGame(Game game) {
    try {
      if (tryToLockBankOnAllServices(game.getGameid())) {
        handleToManyLockTries();
      }
    } catch (BankNotFoundException exception) {

    } finally {
      for (IBanksLogicLockableMutable service : getAllServices()) {
        service.setGame(game);
      }
    }
  }

  @Override
  public boolean registerPlayerForGame(String gameId, Account playerAccount)
      throws BankNotFoundException {
    if (tryToLockBankOnAllServices(gameId)) {
      handleToManyLockTries();
    }
    for (IBanksLogicLockableMutable service : this.getAllServices()) {
      service.registerPlayerForGame(gameId, playerAccount);
    }
    if (!this.unlockBankOnAllServices(gameId)) {
      handleLockedNotAbleToUnlock();
    }
    return false;
  }

  @Override
  public boolean applyTransferInGame(String gameId, Transfer transfer)
      throws NotFoundException {
    if (!logic.transferIsPossible(gameId, transfer)) {
      return false;
    }
    if (tryToLockBankOnAllServices(gameId)) {
      handleToManyLockTries();
    }
    for (IBanksLogicLockableMutable replicate : this.getAllServices()) {
      if (!replicate.applyTransferInGame(gameId, transfer)) {
        handleInconsistency();
      }
    }
    if (!unlockBankOnAllServices(gameId)) {
      handleLockedNotAbleToUnlock();
    }
    return true;
  }

  @Override
  public synchronized boolean registerCloneServices(Set<String> uris) {
    Set<ICloneService> newCloneServices;
    newCloneServices = uris.stream().map(uri -> new CloneService(uri)).collect(toSet());
    return this.remoteCloneServices.addAll(newCloneServices);
  }

  /**
   * Unlocks given bank identified by gameId on every given service.
   * @param services to unlock.
   * @param gameId to unlock on every service.
   * @return true, if and only if all banks were unlocked.
   * @throws BankNotFoundException if and only if, no bank with
   */
  private boolean unlockAll(Set<IBanksLogicLockableMutable> services, String gameId)
      throws BankNotFoundException {
    for (IBanksLogicLockableMutable service : services) {
      if (service.unlock(gameId)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Get all remote and local services.
   * @return a set of all service replications.
   */
  private Set<IBanksLogicLockableMutable> getAllServices() {
    Set<IBanksLogicLockableMutable> services = new HashSet<>(this.remoteCloneServices);
    services.add(logic);
    return services;
  }

  private void handleToManyLockTries() {
    throw new RuntimeException("Tried " + maxTriesToLock + " times to lock resource.");
  }

  private void handleLockedNotAbleToUnlock() {
    throw new RuntimeException("Couldn't unlock replicates which were locked by me.");
  }

  private void handleInconsistency() {
    throw new RuntimeException("Found inconsistent replicate!");
  }
}
