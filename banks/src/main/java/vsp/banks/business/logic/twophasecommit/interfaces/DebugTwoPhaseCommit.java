package vsp.banks.business.logic.twophasecommit.interfaces;

import vsp.banks.business.logic.bank.interfaces.IBanksLogicLockableMutable;

import java.util.Set;

/**
 * Created by alex on 1/19/16.
 */
public interface DebugTwoPhaseCommit {

  /**
   * Get all remote and local services.
   * @return a set of all service replications.
   */
  Set<IBanksLogicLockableMutable> getAllServices();
}
