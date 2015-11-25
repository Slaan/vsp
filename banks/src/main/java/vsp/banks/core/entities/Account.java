package vsp.banks.core.entities;

import static vsp.banks.helper.ObjectHelper.checkNotNull;

/**
 * Created by alex on 11/18/15.
 */
public class Account {

  private Player player;

  private int saldo;

  /**
   * Account is a wrapper for a player and an addition 'saldo', which is the amount of money.
   */
  public Account(Player player, int startSaldo) {
    checkNotNull(player, startSaldo);
    this.player = player;
    this.saldo = startSaldo;
  }

  public int getSaldo() {
    return this.saldo;
  }

  public Player getPlayer() {
    return this.player;
  }

  /**
   * Takes away money from this account. Saldo will be reduced!
   * @param money to take away from saldo.
   * @return true if and only if enough saldo is there. When money is bigger then saldo, false
   *         will be returned. Since saldo can't be less then zero.
   */
  public boolean withdraw(int money) {
    int newSaldo = this.saldo - money;
    if (newSaldo >= 0) {
      this.saldo = newSaldo;
      return true;
    }
    return false;
  }

  /**
   * @param money to add to account.
   */
  public void deposit(int money) {
    this.saldo += money;
  }

  public boolean isPlayerId(String playerId) {
    return this.player.getId().equals(playerId);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Account)) {
      return false;
    }
    Account account = (Account) object;
    return !(player != null ? !player.equals(account.player) : account.player != null);

  }

  @Override
  public int hashCode() {
    return player != null ? player.hashCode() : 0;
  }
}
