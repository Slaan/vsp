package vsp.banks.business.logic.twophasecommit.interfaces;

import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.data.entities.Player;
import vsp.banks.data.values.Transfer;

import java.util.Set;

/**
 * Created by alex on 1/17/16.
 */
public interface ITwoPhaseCommit {

  /**
   *
   * @param bankId
   * @return
   */
  boolean lockAllServices(String bankId);

  /**
   *
   * @param bankId
   * @return
   */
  boolean unlockAllServices(String bankId);

  /**
   *
   * @param bankId
   * @param transfer
   * @return
   */
  boolean applyTransfer(String bankId, Transfer transfer) throws PlayerNotFoundException;

  /**
   *
   * @param bankId
   * @param playerId
   * @return
   */
  boolean addPlayer(String bankId, Player playerId);

  /**
   *
   * @param uri
   * @return
   */
  boolean registerCloneService(String uri);

  Set<String> getCloneServices();
}
