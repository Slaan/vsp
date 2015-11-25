package vsp.banks.core;

import vsp.banks.core.entities.Account;
import vsp.banks.core.entities.Bank;
import vsp.banks.core.exceptions.PlayerNotFoundException;
import vsp.banks.core.interfaces.IBankLogic;
import vsp.banks.values.*;

import java.util.*;

/**
 * Created by alex on 11/19/15.
 */
public class BanksLogic implements IBankLogic {

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
      // how to handle dis?
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
  public List<Event> getEventsOfPlayer(String gameId, String playerId) {
    return null;
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
