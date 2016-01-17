package vsp.banks.business.logic.twophasecommit;

import vsp.banks.business.adapter.interfaces.ICloneServiceAdapter;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.business.logic.twophasecommit.interfaces.ITwoPhaseCommit;
import vsp.banks.business.logic.bank.interfaces.IBankLogic;
import vsp.banks.data.entities.Player;
import vsp.banks.data.values.Transfer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alex on 1/17/16.
 */
public class TwoPhaseCommit implements ITwoPhaseCommit {

  Set<String> cloneServices;

  IBankLogic logic;

  ICloneServiceAdapter cloneServiceAdapter;

  @Override
  public boolean lockAllServices(String bankId) {
    if (!this.logic.lock(bankId)) {
      return false;
    }
    Set<String> locked = new HashSet<>();
    for (String uri : this.cloneServices) {
      if (cloneServiceAdapter.lockBank(uri, bankId)) {
        locked.add(uri);
      } else {
        // unlock all banks, which were locked
        // when not able to lock a single bank
        unlockBank(locked, bankId);
        this.logic.unlock(bankId);
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean unlockAllServices(String bankId) {
    unlockBank(cloneServices, bankId);
    this.logic.unlock(bankId);
    return true;
  }

  @Override
  public boolean applyTransfer(String bankId, Transfer transfer) throws PlayerNotFoundException {
    // MAYBE check if transfer is possible.
    if (!logic.transferIsPossible(bankId, transfer)) {
      return false;
    }
    if (!lockAllServices(bankId)) {
      return false;
    }
    //  when not, unlock resource and return false
    this.cloneServices
        .stream().forEach(s -> this.cloneServiceAdapter.applyTransfer(s, bankId, transfer));
    this.logic.unlock(bankId);
    this.unlockAllServices(bankId);
    return true;
  }

  @Override
  public boolean addPlayer(String bankId, Player playerId) {
    return false;
  }

  @Override
  public boolean registerCloneService(String uri) {
    return cloneServices.add(uri);
  }

  @Override
  public Set<String> getCloneServices() {
    return new HashSet<>(cloneServices);
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
