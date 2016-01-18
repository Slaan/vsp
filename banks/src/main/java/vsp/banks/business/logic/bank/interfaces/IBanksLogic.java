package vsp.banks.business.logic.bank.interfaces;

import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.data.entities.Account;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

/**
 * Created by alex on 11/18/15.
 */
public interface IBanksLogic extends IBanksLogicImmutable, IBanksLogicLockableMutable {

}
