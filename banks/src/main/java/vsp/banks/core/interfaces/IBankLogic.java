package vsp.banks.core.interfaces;

import vsp.banks.values.Account;
import vsp.banks.values.Event;
import vsp.banks.values.Game;
import vsp.banks.values.Transfer;

import java.util.List;

/**
 * Created by alex on 11/18/15.
 */
public interface IBankLogic {

  /**
   * Saves a game. When game exists, it will be overwritten.
   *
   * @param game to store.
   */
  public void setGame(Game game);

  /**
   * Registers a player.
   *
   * @param gameId        to register player.
   * @param playerAccount to register.
   * @return true if and only if player wasn't already registered.
   */
  public boolean registerPlayerForGame(String gameId, Account playerAccount);

  /**
   * Returns player account.
   *
   * @param gameId   in which player is.
   * @param playerId of account.
   * @return player account.
   */
  public Account getAccount(String gameId, String playerId);

  /**
   * Returns all player accounts.
   *
   * @param gameId in which accounts are.
   * @return player accounts.
   */
  public List<Account> getAccounts(String gameId);

  /**
   * Takes money from player account and puts it into banks account.
   * <p>This is a transaction. It has to run isolated!</p>
   *
   * @param gameId  in which player is.
   * @return true if and only if enough money was on account and money has been withdrawn.
   */
  public boolean withdrawMoneyFromPlayer(String gameId, Transfer transfer);

  /**
   * Gives money to player from bank.
   * <p>This is a transaction. It has to run isolated!</p>
   *
   */
  public void giveMoneyToPlayer(String gameId, Transfer transfer);

  /**
   * Transfers an amount of money from sender to receiver.
   * <p>This is a transaction. It has to run isolated!</p>
   *
   * @return true if and only if enough money was on account and money has been withdrawn.
   */
  public boolean transferFromPlayerToPlayer(String gameId, Transfer transfer);

  /**
   * Get all events of a player.
   *
   * @param gameId   in which player is.
   * @param playerId of player the events are retrieved.
   * @return a list of all events a player happened.
   */
  public List<Event> getEventsOfPlayer(String gameId, String playerId);
}
