package vsp.banks.business.logic.bank.interfaces;

import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.data.entities.Account;
import vsp.banks.data.values.Event;
import vsp.banks.data.values.Transfer;

import java.util.List;
import java.util.Set;

/**
 * Created by alex on 1/17/16.
 */
public interface IBanksLogicImmutable {


  /**
   * Returns player account.
   *
   * @param gameId   in which player is.
   * @param playerId of account.
   * @return player account.
   */
  Account getAccount(String gameId, String playerId)
      throws PlayerNotFoundException, BankNotFoundException;

  /**
   * Returns all player accounts.
   *
   * @param gameId in which accounts are.
   * @return player accounts.
   */
  Set<Account> getAccounts(String gameId) throws BankNotFoundException;


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
  List<Transfer> getTransfersOfBank(String gameId) throws BankNotFoundException;


  boolean transferIsPossible(String gameId, Transfer transfer)
      throws PlayerNotFoundException, BankNotFoundException;
}
