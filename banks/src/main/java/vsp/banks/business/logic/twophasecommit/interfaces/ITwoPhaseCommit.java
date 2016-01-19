package vsp.banks.business.logic.twophasecommit.interfaces;

import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogicMutable;

import java.util.Set;

/**
 * Created by alex on 1/17/16.
 */
public interface ITwoPhaseCommit extends IBanksLogicMutable, DebugTwoPhaseCommit {

  /**
   * Locks a bank on all services. No matter if remote or local.
   * @param gameId of bank to lock.
   * @return true, if and only if successfully locked on all banks.
   */
  boolean lockBankOnAllServices(String gameId) throws BankNotFoundException;

  /**
   * Unlocks a bank on all services. No matter if remote or local.
   * @param gameId of bank to unlock.
   * @return true, if and only if successfully unlocked on all banks.
   */
  boolean unlockBankOnAllServices(String gameId) throws BankNotFoundException;

  /**
   * Checks if given bank is locked on all services.
   * @param gameId of bank to check.
   * @return true, if and only if bank is on all services locked.
   */
  boolean isLocked(String gameId) throws BankNotFoundException;

  /**
   * Registers all given uris to sync with.
   * @param uris to register.
   * @return true, if and only if there is at least one uri which has been added.
   */
  boolean registerCloneServices(Set<String> uris);

  /**
   * Returns all registered uris.
   */
  Set<String> getUris();

}
