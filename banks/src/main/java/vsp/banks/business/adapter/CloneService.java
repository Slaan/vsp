package vsp.banks.business.adapter;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import vsp.banks.business.adapter.interfaces.ICloneService;
import vsp.banks.business.converter.TransferConverter;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.data.dtos.TransferCommitDto;
import vsp.banks.data.entities.Account;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

import static vsp.banks.data.values.StatusCodes.*;

/**
 * Created by alex on 1/17/16.
 */
public class CloneService implements ICloneService {

  private final String uri;

  private TransferConverter transferConverter;

  private Gson jsonConverter;

  /**
   * This is an adapter to given uri. It communicates with a 'CommitFacade' of the service.
   * @param uri of clone service.
   */
  public CloneService(String uri) {
    this.uri = uri;
    this.transferConverter = new TransferConverter();
    this.jsonConverter = new Gson();
  }

  @Override
  public boolean lock(String gameId) throws BankNotFoundException {
    String requestUri = this.getUri() + "/replicate/banks/" + gameId + "/lock";
    try {
      int statusCode = Unirest.put(requestUri).asJson().getStatus();
      if (statusCode == ok) {
        return true;
      }
      if (statusCode == conflict) {
        return false;
      }
      if (statusCode == notFound) {
        throw new BankNotFoundException(requestUri);
      }
    } catch (UnirestException e) {
      e.printStackTrace();
    }
    throw logErr(requestUri, gameId, null, null);
  }

  @Override
  public boolean unlock(String gameId) throws BankNotFoundException {
    String requestUri = this.getUri() + "/replicate/banks/" + gameId + "/lock";
    try {
      int statusCode = Unirest.delete(requestUri).asJson().getStatus();
      if (statusCode == ok) {
        return true;
      }
      if (statusCode == noContent) {
        return false;
      }
      if (statusCode == notFound) {
        throw new BankNotFoundException(requestUri);
      }
    } catch (UnirestException e) {
      e.printStackTrace();
    }
    throw logErr(requestUri, gameId, null, null);
  }

  @Override
  public void setGame(Game game) {
    String requestUri = getUri() + "/replicate/banks/" + game.getGameid();
    String gameAsJson = jsonConverter.toJson(game);
    try {
      int statusCode = Unirest.put(requestUri).body(gameAsJson).asJson().getStatus();
      if (statusCode == ok) {
        return;
      }
      throw new RuntimeException("No ok received.");
    } catch (UnirestException e) {
      e.printStackTrace();
    }
    throw logErr(requestUri, game.getGameid(), game, gameAsJson);
  }

  @Override
  public boolean registerPlayerForGame(String gameId, Account playerAccount)
      throws BankNotFoundException {
    String requestUri = getUri() + "/banks/" + gameId + "/players";
    String accountAsJson = this.jsonConverter.toJson(playerAccount);
    try {
      int statusCode = Unirest.post(requestUri).body(accountAsJson).asString().getStatus();
      if (statusCode == created) {
        return true;
      } else if (statusCode == conflict) {
        return false;
      }
    } catch (UnirestException e) {
      e.printStackTrace();
    }
    throw logErr(requestUri, gameId, playerAccount, accountAsJson);
  }

  @Override
  public boolean applyTransferInGame(String gameId, Transfer transfer)
      throws NotFoundException {
    TransferCommitDto dto = this.transferConverter.entityToDto(transfer);
    String transferAsJson = this.jsonConverter.toJson(dto);
    String requestUri = getUri() + "/replicate/banks/" + gameId + "/transfer";
    try {
      int statusCode = Unirest.post(requestUri).body(transferAsJson).asString().getStatus();
      if (statusCode == forbidden) {
        return false;
      } else if (statusCode == notFound) {
        throw new NotFoundException(requestUri);
      } else if (statusCode == created) {
        return true;
      }
    } catch (UnirestException e) {
      e.printStackTrace();
    }
    throw logErr(requestUri, gameId, transfer, transferAsJson);
  }

  @Override
  public boolean isLocked(String gameId) throws BankNotFoundException {
    String requestUri = this.getUri() + "/replicate/banks/" + gameId + "/lock";
    try {
      int statusCode = Unirest.get(requestUri).asString().getStatus();
      if (statusCode == ok) {
        return true;
      } else if (statusCode == conflict) {
        return false;
      } else if (statusCode == notFound) {
        throw new BankNotFoundException("Bank with gameId " + gameId + " not found.");
      }
    } catch (UnirestException e) {
      e.printStackTrace();
    }
    throw logErr(requestUri, gameId, null, null);
  }

  public boolean isRemote() {
    return true;
  }

  @Override
  public String getUri() {
    return "http://" + uri;
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

  /**
   * This method simplifies error call.
   */
  private RuntimeException logErr(String requestUri, String gameId, Object object, String asJson) {
    String exceptionMessage;
    exceptionMessage = "Unexpected with gameId: " + gameId + "\n";
    exceptionMessage += " Object:" + object + "\n";
    exceptionMessage += requestUri + "\n";
    exceptionMessage += " Json:\n";
    exceptionMessage += asJson + "\n";
    return new RuntimeException(exceptionMessage);
  }

}
