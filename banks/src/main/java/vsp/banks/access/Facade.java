package vsp.banks.access;

import vsp.banks.business.adapter.exceptions.NetworkException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.business.logic.bank.interfaces.IBanksLogicImmutable;
import vsp.banks.business.logic.twophasecommit.interfaces.ITwoPhaseCommit;
import vsp.banks.data.entities.Account;
import vsp.banks.data.values.Event;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

import java.util.List;
import java.util.Set;

import static spark.Spark.*;
import static vsp.banks.data.values.StatusCodes.*;
/**
 * Created by alex on 11/18/15.
 */
public class Facade extends AbstractFacade {

  private IBanksLogicImmutable bankLogic;

  private ITwoPhaseCommit twoPhaseCommit;

  /**
   * Creates the RESTful API for banks.
   * @param logic of bank.
   * @param twoPhaseCommit to make changes on all banks.
   */
  public Facade(IBanksLogic logic, ITwoPhaseCommit twoPhaseCommit) {
    super();
    this.bankLogic = logic;
    this.twoPhaseCommit = twoPhaseCommit;
    bindAllMethods();
  }

  @Override
  public void bindAllMethods() {
    bindPostServiceUri();
    bindDeleteServiceUri();
    bindGetBanks();
    bindPutBank();
    bindGetTransfer();
    bindPostBankTransferTo();
    bindPostBankTransferFromTo();
    bindPostBankTransferFrom();
    bindGetBankPlayers();
    bindPostBankAccount();
    bindGetBankPlayer();
  }

  /**
   * Registers given service replicates.
   *
   * <code>POST /service</code>
   */
  public void bindPostServiceUri() {
    post("/replicate", (request, response) -> {
      String uri = request.body();
      response.status(created);
      if (!this.twoPhaseCommit.registerCloneServices(uri)) {
        response.status(conflict);
        response.body("Uri already exists.");
      }
      Set<String> uris = this.twoPhaseCommit.getUris();
      return jsonConverter.toJson(uris);
    });
  }

  /**
   * Registers given service replicates.
   *
   * <code>Delete /service</code>
   * Body contains the uri.
   */
  public void bindDeleteServiceUri() {
    delete("/replicate", (request, response) -> {
      String uri = request.body();
      response.status(ok);
      if (!this.twoPhaseCommit.deleteCloneService(uri)) {
        response.status(noContent);
        response.body("Uri already exists.");
      }
      Set<String> uris = this.twoPhaseCommit.getUris();
      return jsonConverter.toJson(uris);
    });
  }

  /**
   * Fetches all banks.
   * <code>GET /banks</code>
   */
  public void bindGetBanks() {
    get("/banks", (request, response) -> {
      // no op
      return "";
    });
  }

  /**
   * Creates a new bank for a game.
   * The game itself is in body.
   * <code>PUT /banks/{gameId}</code>
   */
  public void bindPutBank() {
    put("/banks/:gameId", (request, response) -> {
      while (true) {
        try {
          Game game = this.jsonConverter.fromJson(request.body(), Game.class);
          this.twoPhaseCommit.setGame(game);
          response.status(ok);
          return "";
        } catch (NetworkException networkException) {
          String uri = networkException.getUri();
          this.twoPhaseCommit.deleteReplicateOnAllReplicates(uri);
        }
      }
    });
  }

  /**
   * Fetch transfer with given transferId.
   * <code>GET /banks/{gameId}/transfers/{transferId}</code>
   */
  public void bindGetTransfer() {
    get("banks/:gameId/transfers", (request, response) -> {
      String gameId = request.params(":gameId");
      List<Transfer> transfers = this.bankLogic.getTransfersOfBank(gameId);
      return this.jsonConverter.toJson(transfers);
    });
  }

  /**
   * Transfers an amount of money from bank to player.implements IAccount
   * <code>post /banks/{gameId}/transfer/to/{to}/{amount}</code>
   */
  public void bindPostBankTransferTo() {
    post("/banks/:gameId/transfer/to/:to/:amount", (request, response) -> {
      while (true) {
        try {
          String gameId = request.params(":gameId");
          String toPlayer = request.params(":to");
          int amount = Integer.parseInt(request.params(":amount"));
          String reason = request.body();
          Transfer transfer = Transfer.bankToPlayer(toPlayer, amount, reason, null);
          try {
            if (this.twoPhaseCommit.applyTransferInGame(gameId, transfer)) {
              response.status(created);
              List<Event> events = this.bankLogic.getEventsOfPlayer(gameId, toPlayer);
              return this.jsonConverter.toJson(events);
            }
            response.status(forbidden);
          } catch (NotFoundException exception) {
            response.status(notFound);
          }
          return "";
        } catch (NetworkException networkException) {
          String uri = networkException.getUri();
          this.twoPhaseCommit.deleteReplicateOnAllReplicates(uri);
        }
      }
    });
  }

  /**
   * Tranfers an amount of money from player to another player.
   * <code>post /banks/{gameId}/transfer/from/{from}/to/{to}/{amount}</code>
   */
  public void bindPostBankTransferFromTo() {
    post("/banks/:gameId/transfer/from/:from/to/:to/:amount", (request, response) -> {
      while (true) {
        try {
          String gameId = request.params(":gameId");
          String fromPlayerId = request.params(":from");
          String toPlayerId = request.params(":to");
          int amount = Integer.parseInt(request.params(":amount"));
          String reason = request.body();
          Transfer transfer = new Transfer(fromPlayerId, toPlayerId, amount, reason, "");
          try {
            if (this.twoPhaseCommit.applyTransferInGame(gameId, transfer)) {
              List<Event> events = this.bankLogic.getEventsOfPlayer(gameId, fromPlayerId);
              response.status(created);
              return this.jsonConverter.toJson(events);
            }
            response.status(forbidden);
          } catch (NotFoundException exception) {
            response.status(notFound);
          }
          return "";
        } catch (NetworkException networkException) {
          String uri = networkException.getUri();
          this.twoPhaseCommit.deleteReplicateOnAllReplicates(uri);
        }
      }
    });
  }

  /**
   * Transfers an amount of money from player to bank.
   * <code>post /banks/{gameId}/transfer/from/{from}/{amount}</code>
   */
  public void bindPostBankTransferFrom() {
    post("/banks/:gameId/transfer/from/:from/:amount", (request, response) -> {
      while (true) {
        try {
          String gameId = request.params(":gameId");
          String fromId = request.params(":from");
          int amount = Integer.parseInt(request.params(":amount"));
          String reason = request.body();
          Transfer transfer = Transfer.playerToBank(fromId, amount, reason, "");
          try {
            if (this.twoPhaseCommit.applyTransferInGame(gameId, transfer)) {
              List<Event> events = this.bankLogic.getEventsOfPlayer(gameId, fromId);
              response.status(created);
              return this.jsonConverter.toJson(events);
            }
            response.status(forbidden);
          } catch (NotFoundException exception) {
            response.status(notFound);
          }
          return "";
        } catch (NetworkException networkException) {
          String uri = networkException.getUri();
          this.twoPhaseCommit.deleteReplicateOnAllReplicates(uri);
        }
      }
    });
  }

  /**
   * Fetches all accounts in game.
   * <code>GET /banks/{gameId}/players</code>
   */
  public void bindGetBankPlayers() {
    get("/banks/:gameId/players", (request, response) -> {
      String gameId = request.params(":gameId");
      Set<Account> accounts = this.bankLogic.getAccounts(gameId);
      return this.jsonConverter.toJson(accounts);
    });
  }

  /**
   * Creates a new account for player.
   * <code>POST /banks/{gameId}/players.</code>
   */
  public void bindPostBankAccount() {
    post("/banks/:gameId/players", (request, response) -> {
      while (true) {
        try {
          String gameId = request.params(":gameId");
          Account account = jsonConverter.fromJson(request.body(), Account.class);
          if (twoPhaseCommit.registerPlayerForGame(gameId, account)) {
            response.status(created);
            return "";
          }
          response.status(conflict);
          return "";
        } catch (NetworkException networkException) {
          String uri = networkException.getUri();
          this.twoPhaseCommit.deleteReplicateOnAllReplicates(uri);
        }
      }
    });
  }

  /**
   * Returns the balance of player account to player.
   * <code>GET /banks/{gameId}/players/{playerId}</code>
   */
  public void bindGetBankPlayer() {
    get("/banks/:gameId/players/:playerId", (request, response) -> {
      String gameId = request.params(":gameId");
      String playerId = request.params(":playerId");
      Account account = bankLogic.getAccount(gameId, playerId);
      response.status(ok);
      return account.getSaldo();
    });
  }
}
