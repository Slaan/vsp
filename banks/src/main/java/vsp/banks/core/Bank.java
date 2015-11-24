package vsp.banks.core;

import vsp.banks.values.Account;
import vsp.banks.values.Game;
import vsp.banks.values.Player;
import vsp.banks.values.Transfer;

import java.util.Map;
import java.util.Set;

/**
 * Created by alex on 11/24/15.
 */
public class Bank {

  private Game game;

  private Set<Account> accounts;

  private Map<Account, Transfer> transferMap;

  public Bank(Game game) {
    this.game = game;
  }

  private Account registerPlayer(Player player) {
    return null;
  }

  public boolean setGame(Game game) {
    // TODO validate this!
    this.game = game;
    return true;
  }

  public boolean hasGameId(Game game) {
    return this.game.getGameId().equals(game.getGameId());
  }

  public boolean hasGameId(String game) {
    return this.game.getGameId().equals(game);
  }

  public Account getAccountByPlayerId(String playerId) {
    for (Account account : this.accounts) {
      if (account.isPlayerId(playerId)) {
        return account;
      }
    }
    return null;
  }
}
