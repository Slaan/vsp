package vsp.banks.business.logic.bank.interfaces;

import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.data.entities.Account;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

/**
 * Created by alex on 1/17/16.
 */
public interface IBanksLogicMutable {

  /**
   * Saves a game. When game exists, it will be overwritten.
   *
   * @param game to store.
   */
  void setGame(Game game);

  /**
   * Registers a player.
   *
   * @param gameId        to register player.
   * @param playerAccount to register.
   * @return true if and only if player wasn't already registered.
   */
  boolean registerPlayerForGame(String gameId, Account playerAccount) throws BankNotFoundException;

  /**
   * Takes an amount of money from one account and puts it into another.
   * Both accounts are found in transfer, same as the amount.
   *
   * <p>An transfer has to run isolated from other transfers.</p>
   *
   * @param gameId   in which player is.
   * @param transfer contains the information about the transfer, e.g. both accounts.
   * @return true if and only if enough money was on account and money has been withdrawn.
   */
  boolean applyTransferInGame(String gameId, Transfer transfer)
      throws NotFoundException;

}
