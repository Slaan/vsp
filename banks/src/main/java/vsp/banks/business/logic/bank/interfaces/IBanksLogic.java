package vsp.banks.business.logic.bank.interfaces;

import vsp.banks.data.entities.Account;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

/**
 * Created by alex on 11/18/15.
 */
public interface IBanksLogic extends IBanksLogicImmutable, IBanksLogicMutable {

  /**
   *
   * @param gameId
   * @return
   */
  boolean lock(String gameId);

  /**
   *
   * @param gameId
   * @return
   */
  boolean unlock(String gameId);
}
