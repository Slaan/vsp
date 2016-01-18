package vsp.banks.business.logic.twophasecommit;

import vsp.banks.business.adapter.CloneService;
import vsp.banks.business.adapter.interfaces.ICloneService;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.business.logic.bank.interfaces.IBanksLogicLockableMutable;
import vsp.banks.business.logic.twophasecommit.interfaces.ITwoPhaseCommit;
import vsp.banks.data.entities.Account;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static vsp.banks.helper.ObjectHelper.checkNotNull;
import static vsp.banks.helper.StringHelper.checkNotEmpty;

/**
 * Created by alex on 1/17/16.
 */
public class TwoPhaseCommitLogic implements ITwoPhaseCommit {

  /**
   * Don't use his set raw. Work with it via. <code>getAllServices()</code>.
   */
  Set<ICloneService> cloneServices;

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

    return true;
  }

  @Override
  public Set<String> getUris() {
    Set<String> uris = this.cloneServices.stream().map(ICloneService::getUri).collect(toSet());
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
  public boolean registerPlayerForGame(String gameId, Account playerAccount) {
    return false;
  }

  @Override
  public boolean applyTransferInGame(String gameId, Transfer transfer)
      throws PlayerNotFoundException, BankNotFoundException {
    if (!logic.transferIsPossible(gameId, transfer)) {
      return false;
    }
    lockBankOnAllServices(gameId);
    getAllServices().stream().forEach(iBanksLogicLockableMutable -> {

    });
    unlockBankOnAllServices(gameId)
    return true;
  }

  @Override
  public synchronized boolean registerCloneServices(Set<String> uris) {
    Set<ICloneService> newCloneServices;
    newCloneServices = uris.stream().map(uri -> new CloneService(uri)).collect(toSet());
    return this.cloneServices.addAll(newCloneServices);
  }

  private boolean unlockAll(Set<IBanksLogicLockableMutable> asdf, String gameId)
      throws BankNotFoundException {
    for (IBanksLogicLockableMutable asd : asdf) {
      if (asd.unlock(gameId)) {
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
    Set<IBanksLogicLockableMutable> services = new HashSet<>(this.cloneServices);
    services.add(logic);
    return services;
  }
}
