package vsp.banks.core;

import vsp.banks.core.interfaces.IBankLogic;
import vsp.banks.values.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by alex on 11/19/15.
 */
public class BanksLogic implements IBankLogic {

  Set<Bank> banks;

  public void setGame(Game game) {
    for (Bank bank : banks) {
      if (bank.hasGameId(game)) {
        bank.setGame(game);
        return;
      }
    }
    Bank bank = new Bank(game);
    banks.add(bank);
  }

  public boolean registerPlayerForGame(String gameId, Account playerAccount) {
    // get game
    // when no game; return false
    // check if account exists
    // when no account exists, create one and return true!

    // else return false
    return false;
  }

  public Account getAccount(String gameId, String playerId) {
    Bank bank = findBankByGameId(gameId);
    if (bank == null) {
      return null;
    }
    return bank.getAccountByPlayerId(playerId);
  }

  public List<Account> getAccounts(String gameId) {
    return null; //findGameByGameId(gameId);
  }

  public synchronized boolean withdrawMoneyFromPlayer(String gameId, Transfer transfer) {
    return false;
  }

  public synchronized void giveMoneyToPlayer(String gameId, Transfer transfer) {

  }

  public synchronized boolean transferFromPlayerToPlayer(String gameId, Transfer transfer) {
    return false;
  }

  public List<Event> getEventsOfPlayer(String gameId, String playerId) {
    return null;
  }

  /**
   * @return bank whose game has the same id as given. When no bank found, null returned.
   */
  private Bank findBankByGameId(String gameId) {
    for (Bank bank : this.banks) {
      if (bank.hasGameId(gameId)) {
        return bank;
      }
    }
    return null;
  }

}
