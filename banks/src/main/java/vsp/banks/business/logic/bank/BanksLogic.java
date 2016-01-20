package vsp.banks.business.logic.bank;

import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
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
    try {
      Bank bank = findBankByGameId(game.getGameid());
      bank.setGame(game);
    } catch (BankNotFoundException e) {
      Bank bank = new Bank(game);
      banks.add(bank);
    }
  }

  @Override
  public boolean registerPlayerForGame(String gameId, Account playerAccount)
      throws BankNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.registerAccount(playerAccount);
  }

  @Override
  public Account getAccount(String gameId, String playerId)
      throws PlayerNotFoundException, BankNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.getAccountByPlayerId(playerId);
  }

  @Override
  public Set<Account> getAccounts(String gameId) throws BankNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.getAccounts();
  }

  @Override
  public boolean applyTransferInGame(String gameId, Transfer transfer)
      throws PlayerNotFoundException, BankNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.applyTransfer(transfer);
  }

  @Override
  public boolean lock(String gameId) throws BankNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.lock();
  }

  @Override
  public boolean unlock(String gameId) throws BankNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.unlock();
  }

  @Override
  public boolean transferIsPossible(String gameId, Transfer transfer)
      throws PlayerNotFoundException, BankNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.canBeApplied(transfer);
  }

  @Override
  public List<Event> getEventsOfPlayer(String gameId, String playerId) {
    return null;
  }

  @Override
  public boolean isLocked(String gameId) throws BankNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.isLocked();
  }

  public boolean isRemote() {
    return false;
  }

  @Override
  public List<Transfer> getTransfersOfBank(String gameId) throws BankNotFoundException {
    Bank bank = findBankByGameId(gameId);
    return bank.getTransfers();
  }

  /**
   * @return bank whose game has the same id as given. When no bank found, null returned.
   */
  private Bank findBankByGameId(String gameId) throws BankNotFoundException {
    for (Bank bank : this.banks) {
      if (bank.hasGameId(gameId)) {
        return bank;
      }
    }
    throw new BankNotFoundException("Bank with gameId '" + gameId + "' not found.");
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof BanksLogic)) {
      return false;
    }

    BanksLogic that = (BanksLogic) other;

    return banks != null ? banks.equals(that.banks) : that.banks == null;

  }

  @Override
  public int hashCode() {
    return banks != null ? banks.hashCode() : 0;
  }
}
