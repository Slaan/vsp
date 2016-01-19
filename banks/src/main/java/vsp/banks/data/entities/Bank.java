package vsp.banks.data.entities;

import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.data.values.Event;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

import java.util.*;

import static vsp.banks.helper.ObjectHelper.*;

/**
 * Created by alex on 11/24/15.
 */
public class Bank {

  private Game game;

  private Set<Account> accounts;

  private List<Transfer> transfers;

  private Map<Account, List<Event>> events;

  private boolean isLocked;

  /**
   * The bank is matched exactly one game. It administrates
   * transfers and accounts.
   */
  public Bank(Game game) {
    checkNotNull(game);
    this.game = game;
    this.accounts = new HashSet<>();
    this.transfers = new ArrayList<>();
    this.isLocked = false;
  }

  /**
   * Registers an player account.
   *
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
    String ownId = this.game.getGameid();
    String otherId = game.getGameid();
    return ownId.equals(otherId);
  }

  private synchronized void setLock(boolean isLocked) {
    this.isLocked = isLocked;
  }

  /**
   * Checks if given gameId is equals to banks games.
   */
  public boolean hasGameId(String game) {
    return this.game.getGameid().equals(game);
  }

  public synchronized boolean isLocked() {
    return isLocked;
  }

  /**
   * Locks if and only if, bank is not locked.
   * This function is thread-safe.
   * @return true when successfully locked.
   */
  public synchronized boolean lock() {
    if (!isLocked()) {
      setLock(true);
      return true;
    }
    return false;
  }

  /**
   * Unlocks if and only if, bank is locked.
   * This function is thread-safe.
   * @return true when successfully unlocked.
   */
  public synchronized boolean unlock() {
    if (isLocked()) {
      setLock(false);
      return true;
    }
    return false;
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
   * Appends the transfer to transfer list.
   */
  private void noteTransfer(Transfer transfer) {
    this.transfers.add(transfer);
  }

  /**
   * @return a new instance of all transfers happened in this bank.
   */
  public List<Transfer> getTransfers() {
    return new ArrayList<>(this.transfers);
  }


  /**
   * Checks if given transfer can be applied to account which holds the 'from' id.
   *
   * @param transfer to check.
   * @return true, if and only if given transfers 'from' id is a bank or player has enough money.
   * @throws PlayerNotFoundException when play
   */
  public boolean canBeApplied(Transfer transfer) throws PlayerNotFoundException {
    if (!transfer.isBankToPlayer()) {
      int amount = transfer.getAmount();
      String playerId = transfer.getFrom();
      Account player = this.getAccountByPlayerId(playerId);
      return player.canWithdraw(amount);
    }
    return true;
  }

  /**
   * Transfers an amount of money from one account to another.
   *
   * @param transfer contains the information about the accounts and amount.
   * @return true when successfully transferred from A to B. When A has not enough money, it isn't
   *         able to transfer and false will be returned.
   */
  public synchronized boolean applyTransfer(Transfer transfer) throws PlayerNotFoundException {
    int amount = transfer.getAmount();
    String from = transfer.getFrom();
    if (!transfer.isBankToPlayer()) {
      Account accountFrom = this.getAccountByPlayerId(from);
      if (!accountFrom.withdraw(amount)) {
        return false;
      }
    }
    String to = transfer.getTo();
    if (!transfer.isPlayerToBank()) {
      Account accountTo = this.getAccountByPlayerId(to);
      accountTo.deposit(amount);
    }
    noteTransfer(transfer);
    return true;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Bank)) {
      return false;
    }
    Bank bank = (Bank) other;
    return game != null ? game.equals(bank.game) : bank.game == null;

  }

  @Override
  public int hashCode() {
    return game != null ? game.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Bank{"
        + "game=" + game
        + ", accounts=" + accounts
        + ", transfers=" + transfers
        + ", events=" + events
        + ", isLocked=" + isLocked
        + '}';
  }
}
