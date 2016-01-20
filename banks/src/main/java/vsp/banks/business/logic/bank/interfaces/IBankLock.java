package vsp.banks.business.logic.bank.interfaces;

import vsp.banks.business.adapter.exceptions.NetworkException;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;

/**
 * Created by alex on 1/18/16.
 */
public interface IBankLock {

  /**
   * Locks bank which holds given game id.
   * @param gameId to identify.
   * @return if and only if the bank was not locked and has been successfully locked.
   */
  boolean lock(String gameId) throws BankNotFoundException, NetworkException;

  /**
   * Locks bank which holds given game id.
   * @param gameId to identify.
   * @return if and only if the bank was locked and has been successfully unlocked.
   */
  boolean unlock(String gameId) throws BankNotFoundException, NetworkException;

}
