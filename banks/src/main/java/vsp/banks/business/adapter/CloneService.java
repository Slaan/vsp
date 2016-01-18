package vsp.banks.business.adapter;

import vsp.banks.business.adapter.interfaces.ICloneService;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.data.entities.Account;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

/**
 * Created by alex on 1/17/16.
 */
public class CloneService implements ICloneService {

  private final String uri;

  public CloneService(String uri) {
    this.uri = uri;
  }

  @Override
  public boolean lock(String gameId) throws BankNotFoundException {
    return false;
  }

  @Override
  public boolean unlock(String gameId) throws BankNotFoundException {
    return false;
  }

  @Override
  public void setGame(Game game) {

  }

  @Override
  public boolean registerPlayerForGame(String gameId, Account playerAccount)
      throws BankNotFoundException {
    return false;
  }

  @Override
  public boolean applyTransferInGame(String gameId, Transfer transfer)
      throws PlayerNotFoundException, BankNotFoundException {
    return false;
  }

  @Override
  public String getUri() {
    return uri;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof CloneService)) {
      return false;
    }

    CloneService that = (CloneService) other;

    return uri != null ? uri.equals(that.uri) : that.uri == null;

  }

  @Override
  public int hashCode() {
    return uri != null ? uri.hashCode() : 0;
  }

}
