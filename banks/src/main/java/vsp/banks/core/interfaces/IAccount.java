package vsp.banks.core.interfaces;

import vsp.banks.values.Player;

/**
 * Created by alex on 11/18/15.
 */
public interface IAccount {

  /**
   * @return current balance of this account.
   */
  public int getSaldo();

  /**
   * @return id of player owning this account.
   */
  public Player getPlayer();

}
