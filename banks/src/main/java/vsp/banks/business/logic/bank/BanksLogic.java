package vsp.banks.business.logic.bank;

import vsp.banks.data.entities.Account;
import vsp.banks.data.entities.Bank;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.data.values.*;

import java.util.*;

/**
 * Created by alex on 11/19/15.
 */
public class BanksLogic implements IBanksLogic {

  private Set<Bank> banks;

  public BanksLogic() {
    this.banks = new HashSet<>();
  }

  @Override
  public void setGame(Game game) {
    for (Bank bank : banks) {
      if (bank.hasGameId(game)) {
        bank.setGame(game);
        return;
      }
    }
    Bank bank = new Bank(game);
    banks.add(bank);
  }

  @Override
  public boolean registerPlayerForGame(String gameId, Account playerAccount) {
    Bank bank = findBankByGameId(gameId);
    if (bank == null) {
      return false;
    }
    return bank.registerAccount(playerAccount);
  }

  @Override
  public Account getAccount(String gameId, String playerId) throws PlayerNotFoundException {
    Bank bank = findBankByGameId(gameId);
    if (bank == null) {
      return null;
    }
    return bank.getAccountByPlayerId(playerId);
  }

  @Override
  public Set<Account> getAccounts(String gameId) {
    Bank bank = findBankByGameId(gameId);
    return bank.getAccounts();
  }

  @Override
  public boolean applyTransferInGame(String gameId, Transfer transfer)
      throws PlayerNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.applyTransfer(transfer);
  }

  @Override
  public boolean lock(String gameId) {
    Bank bank = findBankByGameId(gameId);
    return bank.lock();
  }

  @Override
  public boolean unlock(String gameId) {
    Bank bank = findBankByGameId(gameId);
    return bank.unlock();
  }

  @Override
  public boolean transferIsPossible(String gameId, Transfer transfer)
      throws PlayerNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.canBeApplied(transfer);
  }

  @Override
  public List<Event> getEventsOfPlayer(String gameId, String playerId) {
    return null;
  }

  public boolean isLocked(String gameId) {
    return false;
  }

  @Override
  public List<Transfer> getTransfersOfBank(String gameId) {
    Bank bank = findBankByGameId(gameId);
    return bank.getTransfers();
  }

  /**
   * @return bank whose game has the same id as given. When no bank found, null returned.
   */
  private Bank findBankByGameId(String gameId) {
    for (Bank bank : this.banks) {
      if (bank.hasGameId(gameId)) {
        return bank;
      }
    }
    return null;
  }

}
