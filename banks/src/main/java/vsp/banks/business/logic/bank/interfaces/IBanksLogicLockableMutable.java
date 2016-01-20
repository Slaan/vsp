package vsp.banks.business.logic.bank.interfaces;

import vsp.banks.business.adapter.exceptions.NetworkException;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;

/**
 * Created by alex on 1/18/16.
 */
public interface IBanksLogicLockableMutable extends IBankLock, IBanksLogicMutable {

  /**
   * Checks if given bank is locked.
   * @param gameId of bank to check.
   * @return true, if and only if bank is locked.
   */
  boolean isLocked(String gameId) throws BankNotFoundException, NetworkException;

  /**
   * @return true, if and only if bank is a remote service.
   */
  boolean isRemote();

}
