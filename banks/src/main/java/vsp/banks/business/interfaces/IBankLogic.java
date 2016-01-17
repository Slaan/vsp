package vsp.banks.business.interfaces;

import vsp.banks.data.entities.Account;
import vsp.banks.business.exceptions.PlayerNotFoundException;
import vsp.banks.data.values.Event;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

import java.util.List;
import java.util.Set;

/**
 * Created by alex on 11/18/15.
 */
public interface IBankLogic {

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
   * Returns player account.
   *
   * @param gameId   in which player is.
   * @param playerId of account.
   * @return player account.
   */
  Account getAccount(String gameId, String playerId) throws PlayerNotFoundException;

  /**
   * Returns all player accounts.
   *
   * @param gameId in which accounts are.
   * @return player accounts.
   */
  Set<Account> getAccounts(String gameId);

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


  /**
   * Get all events of a player.
   *
   * @param gameId   in which player is.
   * @param playerId of player the events are retrieved.
   * @return a list of all events a player happened.
   */
  List<Event> getEventsOfPlayer(String gameId, String playerId);

  /**
   * Returns all transfers of given game.
   *
   * @param gameId of game in bank.
   * @return list of transfers happened in bank.
   */
  List<Transfer> getTransfersOfBank(String gameId);
}
