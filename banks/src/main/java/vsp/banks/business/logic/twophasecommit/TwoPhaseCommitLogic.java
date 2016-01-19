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
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static vsp.banks.helper.ObjectHelper.checkNotNull;
import static vsp.banks.helper.StringHelper.checkNotEmpty;

/**
 * Created by alex on 1/17/16.
 */
public class TwoPhaseCommitLogic implements ITwoPhaseCommit {

  /**
   * This set contains only remote replicates.
   *
   * To get all replicates use <code>getAllServices()</code>
   */
  Set<ICloneService> remoteCloneServices;

  String ownUri;

  IBanksLogic logic;

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
        // when we can not lock a single service, unlock all
        // e.g.:
        //  lockedServices.unlock(..)
        return false;
      }
    }
    return true;
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
    Set<String> uris = this.remoteCloneServices.stream().map(ICloneService::getUri).collect(toSet());
    uris.add(this.ownUri);
    return uris;
  }

  @Override
  public void setGame(Game game) {
    try {
      this.logic.lock(game.getGameid());
      lockBankOnAllServices(game.getGameid());
    } catch (BankNotFoundException e) {
      e.printStackTrace();
    } finally {

    }
  }

  @Override
  public boolean registerPlayerForGame(String gameId, Account playerAccount)
      throws BankNotFoundException {
    if (!this.lockBankOnAllServices(gameId)) {

    }
    if (!this.unlockBankOnAllServices(gameId)) {

    }
    return false;
  }

  @Override
  public boolean applyTransferInGame(String gameId, Transfer transfer)
      throws NotFoundException {
    if (!logic.transferIsPossible(gameId, transfer)) {
      return false;
    }
    if (!lockBankOnAllServices(gameId)) {
      // TODO: How handle no lockable ?!
      // relock five times
    }
    for (IBanksLogicLockableMutable replicate : this.getAllServices()) {
      if (!replicate.applyTransferInGame(gameId, transfer)) {
        handleInconsistency();
      }
    }
    if (!unlockBankOnAllServices(gameId)) {
      String exceptionMessage;
      exceptionMessage = "Couldn't unlock replicates which were locked by this service before";
      throw new RuntimeException(exceptionMessage);
    }
    return true;
  }

  private void handleInconsistency() {
    throw new RuntimeException("Found inconsistent replicate!");
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
}
