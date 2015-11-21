package vsp.banks.core.interfaces;

import vsp.banks.values.Event;
import vsp.banks.values.Money;

import java.util.List;

/**
 * Created by alex on 11/18/15.
 */
public interface IBankLogic {

  /**
   * Registers a player.
   *
   * @param gameId   to register player.
   * @param playerId of player to register.
   * @return true if and only if player wasn't already registered.
   */
  public boolean registerPlayerForGame(String gameId, String playerId);

  /**
   * Returns player account.
   *
   * @param gameId   in which player is.
   * @param playerId of account.
   * @return player account.
   */
  public IAccount getAccount(String gameId, String playerId);

  /**
   * Takes money from player account and puts it into banks account.
   * <p>
   * <p>This is a transaction. It has to run isolated!</p>
   *
   * @param gameId  in which player is.
   * @param sponsor of account.
   * @param amount  of money to withdraw.
   * @param reason  of transfer.
   * @return true if and only if enough money was on account and money has been withdrawn.
   */
  public boolean withdrawMoneyFromPlayer(String gameId, String sponsor, Money amount, String reason);

  /**
   * Gives money to player from bank.
   * <p>This is a transaction. It has to run isolated!</p>
   *
   * @param gameId   in which player is.
   * @param receiver of the money.
   * @param amount   of money to deposit.
   * @param reason  of transfer.
   */
  public void giveMoneyToPlayer(String gameId, String receiver, Money amount, String reason);

  /**
   * Transfers an amount of money from sender to receiver.
   * <p>
   * <p>This is a transaction. It has to run isolated!</p>
   *
   * @param gameId   in which player is.
   * @param sender   of the money.
   * @param receiver of the money.
   * @param amount   of money to transfer.
   * @param reason  of transfer.
   * @return true if and only if enough money was on account and money has been withdrawn.
   */
  public boolean transferFromPlayerToPlayer(String gameId, String sender, String receiver,
      Money amount, String reason);

  /**
   * Get all events of a player.
   * @param gameId   in which player is.
   * @param playerId of player the events are retrieved.
   * @return a list of all events a player happened.
   */
  public List<Event> getEventsOfPlayer(String gameId, String playerId);
}
