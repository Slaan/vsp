package vsp.banks.access;

import com.google.gson.Gson;
import vsp.banks.access.entities.Account;
import vsp.banks.access.interfaces.IBankLogic;
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
public class BanksRestApi {

  private IBankLogic bankServiceLogic;

  private Gson converter;


  /**
   * Creates the RESTful API for banks.
   * @param logic is the business logic of this application.
   */
  public BanksRestApi(IBankLogic logic) {
    this.bankServiceLogic = logic;
    this.converter = new Gson();
    bindAllMethods();
  }

  /**
   * Binds all REST api calls.
   */
  public void bindAllMethods() {
    bindGetBanks();
    bindPutBank();
    bindGetTransfer();
    bindPostBankTransferTo();
    bindPostBankTransferFromTo();
    bindPostBankTransferFrom();
    bindGetBankPlayers();
    bindPostBankPlayer();
    bindGetBankPlayer();
  }

  /**
   * Fetches all banks.
   * <code>GET /banks</code>
   */
  public void bindGetBanks() {
    get("/banks/", (request, response) -> {
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
      Game game = this.converter.fromJson(request.body(), Game.class);
      this.bankServiceLogic.setGame(game);
      response.status(ok);
      return "";
    });
  }

  /**
   * Fetch transfer with given transferId.
   * <code>GET /banks/{gameId}/transfers/{transferId}</code>
   */
  public void bindGetTransfer() {
    get("banks/:gameId/transfers", (request, response) -> {
      String gameId = request.params(":gameId");
      List<Transfer> transfers = this.bankServiceLogic.getTransfersOfBank(gameId);
      return this.converter.toJson(transfers);
    });
  }

  /**
   * Transfers an amount of money from bank to player.implements IAccount
   * <code>post /banks/{gameId}/transfer/to/{to}/{amount}</code>
   */
  public void bindPostBankTransferTo() {
    post("/banks/:gameId/transfer/to/:to/:amount", (request, response) -> {
      String gameId = request.params(":gameId");
      String toPlayer = request.params(":to");
      int amount = Integer.parseInt(request.params(":amount"));
      String reason = request.body();
      Transfer transfer = Transfer.bankToPlayer(toPlayer, amount, reason, null);
      this.bankServiceLogic.applyTransferInGame(gameId, transfer);
      List<Event> events = this.bankServiceLogic.getEventsOfPlayer(gameId, toPlayer);
      return this.converter.toJson(events);
    });
  }

  /**
   * Tranfers an amount of money from player to another player.
   * <code>post /banks/{gameId}/transfer/from/{from}/to/{to}/{amount}</code>
   */
  public void bindPostBankTransferFromTo() {
    post("/banks/:gameId/transfer/from/:from/to/:to/:amount", (request, response) -> {
      String gameId = request.params(":gameId");
      String fromPlayerId = request.params(":from");
      String toPlayerId = request.params(":to");
      int amount = Integer.parseInt(request.params(":amount"));
      String reason = request.body();
      Transfer transfer = new Transfer(fromPlayerId, toPlayerId, amount, reason, "");
      if (this.bankServiceLogic.applyTransferInGame(gameId, transfer)) {
        List<Event> events = this.bankServiceLogic.getEventsOfPlayer(gameId, fromPlayerId);
        response.status(ok);
        return this.converter.toJson(events);
      }
      response.status(forbidden);
      return "";
    });
  }

  /**
   * Transfers an amount of money from player to bank.
   * <code>post /banks/{gameId}/transfer/from/{from}/{amount}</code>
   */
  public void bindPostBankTransferFrom() {
    post("/banks/:gameId/transfer/from/:from/:amount", (request, response) -> {
      String gameId = request.params(":gameId");
      String fromId = request.params(":from");
      int amount = Integer.parseInt(request.params(":amount"));
      String reason = request.body();
      Transfer transfer = Transfer.playerToBank(fromId, amount, reason, "");
      if (this.bankServiceLogic.applyTransferInGame(gameId, transfer)) {
        List<Event> events = this.bankServiceLogic.getEventsOfPlayer(gameId, fromId);
        response.status(ok);
        return this.converter.toJson(events);
      }
      response.status(forbidden);
      return "";
    });
  }

  /**
   * Fetches all accounts in game.
   * <code>GET /banks/{gameId}/players</code>
   */
  public void bindGetBankPlayers() {
    get("/banks/:gameId/players", (request, response) -> {
      String gameId = request.params(":gameId");
      Set<Account> accounts = this.bankServiceLogic.getAccounts(gameId);
      String accountsAsJson = this.converter.toJson(accounts);
      return accountsAsJson;
    });
  }

  /**
   * Creates a new account for player.
   * <code>POST /banks/{gameId}/players.</code>
   */
  public void bindPostBankPlayer() {
    post("/banks/:gameId/players", (request, response) -> {
      String gameId = request.params(":gameId");
      Account account = converter.fromJson(request.body(), Account.class);
      if (bankServiceLogic.registerPlayerForGame(gameId, account)) {
        response.status(created);
        return "";
      }
      response.status(conflict);
      return "";
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
      Account account = bankServiceLogic.getAccount(gameId, playerId);
      response.status(ok);
      return account.getSaldo();
    });
  }
}
