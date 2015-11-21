package vsp.banks.core;

import vsp.banks.core.entities.Account;
import vsp.banks.core.interfaces.IAccount;
import vsp.banks.core.interfaces.IBankLogic;
import vsp.banks.values.Event;
import vsp.banks.values.Money;

import java.util.*;

/**
 * Created by alex on 11/19/15.
 */
public class BanksLogic implements IBankLogic {

  private Map<String, Set<Account>> gameToAccounts;

  public final static Money startMoney = new Money(1490);

  public BanksLogic() {
    this.gameToAccounts = new HashMap<>();
  }

  /**
   * Registers a player to a game.
   * @param gameId   to register player.
   * @param playerId of player to register.
   * @return true if and only if player wasn't registered and has been registered.
   */
  public boolean registerPlayerForGame(String gameId, String playerId) {
    if (!gameToAccounts.containsKey(gameId)) {
      gameToAccounts.put(gameId, new HashSet<>());
    }
    if (findAccountByGameAndPlayer(gameId, playerId) == null) {
      Account newAccount = new Account(playerId, startMoney);
      // register account
      Set<Account> accounts = gameToAccounts.get(gameId);
      accounts.add(newAccount);
      gameToAccounts.put(gameId, accounts);
      return true;
    }
    return false;
  }

  public IAccount getAccount(String gameId, String playerId) {
    return findAccountByGameAndPlayer(gameId, playerId);
  }

  public List<Event> getEventsOfPlayer(String gameId, String playerId) {
    Account player = findAccountByGameAndPlayer(gameId, playerId);
    if (player == null) {
      return null;
    }
    return player.getEvents();
  }

  public synchronized boolean withdrawMoneyFromPlayer(String gameId, String sponsor, Money amount,
      String reason) {
    Account account = findAccountByGameAndPlayer(gameId, sponsor);
    return account.withdraw(amount);
  }

  public synchronized void giveMoneyToPlayer(String gameId, String receiver, Money amount,
      String reason) {
    Account account = findAccountByGameAndPlayer(gameId, receiver);
    account.deposit(amount);
  }

  public synchronized boolean transferFromPlayerToPlayer(String gameId, String sender,
      String receiver, Money amount, String reason) {
    Account senderAccount = findAccountByGameAndPlayer(gameId, sender);
    Account receiverAccount = findAccountByGameAndPlayer(gameId, receiver);
    if (senderAccount.withdraw(amount)) {
      receiverAccount.deposit(amount);
      return true;
    }
    return false;
  }

  /**
   * @return null when not found.
   */
  private Account findAccountByGameAndPlayer(String gameId, String playerId) {
    Set<Account> accounts = gameToAccounts.get(gameId);
    for (Account account : accounts) {
      String accountPlayerId = account.getPlayerId();
      if (accountPlayerId.equals(playerId)) {
        return account;
      }
    }
    return null;
  }
}
