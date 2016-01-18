package vsp.banks.business.logic.twophasecommit.interfaces;

import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogicMutable;

import java.util.Set;

/**
 * Created by alex on 1/17/16.
 */
public interface ITwoPhaseCommit extends IBanksLogicMutable {

  /**
   *
   * @param gameId
   * @return
   */
  boolean lockBankOnAllServices(String gameId) throws BankNotFoundException;

  /**
   *
   * @param gameId
   * @return
   */
  boolean unlockBankOnAllServices(String gameId) throws BankNotFoundException;

  /**
   *
   * @param uri
   * @return
   */
  boolean registerCloneServices(Set<String> uri);

  /**
   *
   * @return
   */
  Set<String> getUris();

}
