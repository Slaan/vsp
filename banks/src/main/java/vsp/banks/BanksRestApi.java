package vsp.banks;

import com.google.gson.Gson;
import vsp.banks.values.Account;
import vsp.banks.core.interfaces.IBankLogic;
import vsp.banks.values.Event;
import vsp.banks.values.Game;
import vsp.banks.values.Transfer;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 * Created by alex on 11/18/15.
 */
public class BanksRestApi {

  private IBankLogic bankServiceLogic;

  private Gson converter;

  private final static int ok = 200;
  private final static int created = 201;
  private final static int forbidden = 403;
  private final static int conflict = 409;

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
   * Fetch transfer with given transferId
   * <code>GET /banks/{gameId}/transfers/{transferId}</code>
   */
  public void bindGetTransfer() {
    get("banks/:gameId/transfers/:transferId", (request, response) -> {
      // no op
      return "";
    });
  }

  /**
   * Transfers an amount of money from bank to player.implements IAccount
   * <code>post /banks/{gameId}/transfer/to/{to}/{amount}</code>
   */
  public void bindPostBankTransferTo() {
    post("/banks/:gameId/transfer/to/:to/:amount", (request, response) -> {
      String gameId = request.params(":gameId");
      String fromPlayerId = request.params(":to");
      int amount = Integer.parseInt(request.params(":amount"));
      String reason = request.body();
      this.bankServiceLogic.giveMoneyToPlayer(gameId, null);
      List<Event> events = this.bankServiceLogic.getEventsOfPlayer(gameId, fromPlayerId);
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
      if (this.bankServiceLogic.transferFromPlayerToPlayer(gameId, null)) {
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
      Transfer transfer = Transfer.initTransferFromPlayer(fromId, amount, reason, "");
      if (this.bankServiceLogic.withdrawMoneyFromPlayer(gameId, transfer)) {
        List<Event> events = this.bankServiceLogic.getEventsOfPlayer(gameId, fromId);
        response.status(ok);
        return this.converter.toJson(events);
      }
      response.status(forbidden);
      return "";
    });
  }

  public void bindGetBankPlayers() {
    get("/banks/:gameId/players", (request, response) -> {
      String gameId = request.params(":gameId");
      List<Account> accounts = this.bankServiceLogic.getAccounts(gameId);
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
