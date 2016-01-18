package vsp.banks.business.logic.twophasecommit;

import vsp.banks.business.adapter.interfaces.ICloneServiceAdapter;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.business.logic.twophasecommit.interfaces.ITwoPhaseCommit;
import vsp.banks.data.entities.Account;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

import java.util.HashSet;
import java.util.Set;

import static vsp.banks.helper.ObjectHelper.checkNotNull;

/**
 * Created by alex on 1/17/16.
 */
public class TwoPhaseCommitLogic implements ITwoPhaseCommit {

  Set<String> cloneServices;

  IBanksLogic logic;

  ICloneServiceAdapter cloneServiceAdapter;

  public TwoPhaseCommitLogic(IBanksLogic logic, ICloneServiceAdapter cloneServiceAdapter) {
    checkNotNull(logic, cloneServiceAdapter);
    this.logic = logic;
    this.cloneServiceAdapter = cloneServiceAdapter;
  }

  @Override
  public boolean lockAllBanksOnAllServices(String gameId) {
    if (!this.logic.lock(gameId)) {
      return false;
    }
    Set<String> locked = new HashSet<>();
    for (String uri : this.cloneServices) {
      if (cloneServiceAdapter.lockBank(uri, gameId)) {
        locked.add(uri);
      } else {
        // unlock all banks, which were locked
        // when not able to lock a single bank
        unlockBank(locked, gameId);
        this.logic.unlock(gameId);
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean unlockAllBanksOnAllServices(String bankId) {
    unlockBank(cloneServices, bankId);
    this.logic.unlock(bankId);
    return true;
  }

  @Override
  public Set<String> getCloneServices() {
    return new HashSet<>(cloneServices);
  }

  @Override
  public void setGame(Game game) {

  }

  @Override
  public boolean registerPlayerForGame(String gameId, Account playerAccount) {
    return false;
  }

  @Override
  public boolean applyTransferInGame(String gameId, Transfer transfer)
      throws PlayerNotFoundException {
    if (!logic.transferIsPossible(gameId, transfer)) {
      return false;
    }
    if (!lockAllBanksOnAllServices(gameId)) {
      return false;
    }
    for (String cloneService : this.cloneServices) {
      this.cloneServiceAdapter.applyTransfer(cloneService, gameId, transfer);
    }
    this.logic.applyTransferInGame(gameId, transfer);
    this.unlockAllBanksOnAllServices(gameId);
    return true;
  }

  @Override
  public synchronized boolean registerCloneServices(Set<String> uris) {
    return cloneServices.addAll(uris);
  }

  /**
   * Unlocks all services
   * @param urisToUnlock
   * @param bankId
   */
  private void unlockBank(Set<String> urisToUnlock, String bankId) {
    for (String uriToUnlock : urisToUnlock) {
      cloneServiceAdapter.unlockBank(uriToUnlock, bankId);
    }
  }
}
