package vsp.banks.values;

import static vsp.banks.helper.ObjectHelper.checkNotNull;

/**
 * Created by alex on 11/18/15.
 */
public class Account {

  private Player player;

  private int saldo;

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

  public Account withdraw(int money) {
    int newSaldo = this.saldo - money;
    if (newSaldo > 0) {
      return new Account(player, newSaldo);
    }
    return null;
  }

  public Account deposit(int money) {
    return new Account(player, this.getSaldo() + money);
  }

  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Account)) {
      return false;
    }
    Account account = (Account) object;
    if (saldo != account.saldo) {
      return false;
    }
    return !(player != null ? !player.equals(account.player) : account.player != null);
  }

  public boolean isPlayerId(String playerId) {
    return this.player.getId().equals(playerId);
  }

  public int hashCode() {
    int result = player != null ? player.hashCode() : 0;
    result = 31 * result + saldo;
    return result;
  }
}
