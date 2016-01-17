package vsp.banks.business.logic.bank.interfaces;

import vsp.banks.business.logic.twophasecommit.interfaces.IBankLogicImmutable;
import vsp.banks.data.entities.Account;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.data.values.Event;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

import java.util.List;
import java.util.Set;

/**
 * Created by alex on 11/18/15.
 */
public interface IBankLogic extends IBankLogicImmutable{

  /**
   * Saves a game. When game exists, it will be overwritten.
   *
   * @param game to store.
   */
  void setGame(Game game);

  /**
   * Registers a player.
   *
   * @param gameId        to register player.
   * @param playerAccount to register.
   * @return true if and only if player wasn't already registered.
   */
  boolean registerPlayerForGame(String gameId, Account playerAccount);


  /**
   * Takes an amount of money from one account and puts it into another.
   * Both accounts are found in transfer, same as the amount.
   *
   * <p>An transfer has to run isolated from other transfers.</p>
   *
   * @param gameId   in which player is.
   * @param transfer contains the information about the transfer, e.g. both accounts.
   * @return true if and only if enough money was on account and money has been withdrawn.
   */
  boolean applyTransferInGame(String gameId, Transfer transfer) throws PlayerNotFoundException;

  boolean transferIsPossible(String gameId, Transfer transfer) throws PlayerNotFoundException;

  /**
   *
   * @param gameId
   * @return
   */
  boolean lock(String gameId);

  boolean unlock(String gameId);
}
