package vsp.banks.core.entities;

import vsp.banks.core.exceptions.PlayerNotFoundException;
import vsp.banks.values.Game;
import vsp.banks.values.Player;
import vsp.banks.values.Transfer;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static vsp.banks.helper.ObjectHelper.*;

/**
 * Created by alex on 11/24/15.
 */
public class Bank {

  private Game game;

  private Set<Account> accounts;

  private Map<Account, Transfer> transferMap;

  /**
   * The bank is matched exactly one game. It administrates
   * transfers and accounts.
   */
  public Bank(Game game) {
    checkNotNull(game);
    this.game = game;
    accounts = new HashSet<>();
  }

  /**
   * Registers an player account.
   *
   * @param accountToRegister will be
   * @return true if and only if account has been added.
   */
  public boolean registerAccount(Account accountToRegister) {
    checkNotNull(accountToRegister);
    Set<Player> players = game.getPlayers();
    // check if player in game
    if (!players.contains(accountToRegister.getPlayer())) {
      return false;
    }
    return accounts.add(accountToRegister);
  }

  /**
   * Sets the game context.
   * @param game to set.
   * @return true when successfully set game.
   */
  public boolean setGame(Game game) {
    checkNotNull(game);
    this.game = game;
    return true;
  }

  /**
   * Checks if gameId of given game is equals to banks games.
   */
  public boolean hasGameId(Game game) {
    return this.game.getGameId().equals(game.getGameId());
  }

  /**
   * Checks if given gameId is equals to banks games.
   */
  public boolean hasGameId(String game) {
    return this.game.getGameId().equals(game);
  }

  /**
   * @return account with playerId. When not found, null will be returned.
   */
  public Account getAccountByPlayerId(String playerId) throws PlayerNotFoundException {
    for (Account account : this.accounts) {
      if (account.isPlayerId(playerId)) {
        return account;
      }
    }
    throw new PlayerNotFoundException("Did not found player with id: '" + playerId + "'.");
  }

  public Set<Account> getAccounts() {
    return new HashSet<>(accounts);
  }

  /**
   * Transfers an amount of money from one account to another.
   *
   * @param transfer contains the information about the accounts and amount.
   * @return true when successfully transferred from A to B. When A has not enough money, he isn't
   *         able to transfer and false will be returned.
   */
  public synchronized boolean applyTransfer(Transfer transfer) throws PlayerNotFoundException {
    int amount = transfer.getAmount();
    String from = transfer.getFrom();
    if (!from.equals(Transfer.bankName)) {
      Account accountFrom = this.getAccountByPlayerId(from);
      if (!accountFrom.withdraw(amount)) {
        return false;
      }
    }
    String to = transfer.getTo();
    if (!to.equals(Transfer.bankName)) {
      Account accountTo = this.getAccountByPlayerId(to);
      accountTo.deposit(amount);
    }
    // note event
    return true;
  }
}
