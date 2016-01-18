package vsp.banks.business.logic.twophasecommit.interfaces;

import vsp.banks.business.logic.bank.interfaces.IBanksLogicMutable;

import java.util.Set;

/**
 * Created by alex on 1/17/16.
 */
public interface ITwoPhaseCommit extends IBanksLogicMutable {

  /**
   *
   * @param bankId
   * @return
   */
  boolean lockAllBanksOnAllServices(String bankId);

  /**
   *
   * @param bankId
   * @return
   */
  boolean unlockAllBanksOnAllServices(String bankId);

  /**
   *
   * @param uri
   * @return
   */
  boolean registerCloneServices(Set<String> uri);

  Set<String> getCloneServices();

}
