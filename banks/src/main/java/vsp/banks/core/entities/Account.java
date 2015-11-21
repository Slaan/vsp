package vsp.banks.core.entities;

import vsp.banks.core.interfaces.IAccount;
import vsp.banks.values.Player;

import static vsp.banks.helper.ObjectHelper.checkNotNull;

/**
 * Created by alex on 11/18/15.
 */
public class Account implements IAccount {

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

  public boolean withdraw(int money) {
    int newSaldo = this.saldo - money;
    if (newSaldo > 0) {
      this.saldo = newSaldo;
      return true;
    }
    return false;
  }

  public void deposit(int money) {
    this.saldo = this.saldo + money;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Account)) {
      return false;
    }
    Account account = (Account) o;
    if (saldo != account.saldo) {
      return false;
    }
    return !(player != null ? !player.equals(account.player) : account.player != null);

  }

  public int hashCode() {
    int result = player != null ? player.hashCode() : 0;
    result = 31 * result + saldo;
    return result;
  }
}
