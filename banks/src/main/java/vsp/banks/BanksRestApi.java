package vsp.banks;

import vsp.banks.core.interfaces.IAccount;
import vsp.banks.core.interfaces.IBankLogic;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by alex on 11/18/15.
 */
public class BanksRestApi {

  private IBankLogic bankServiceLogic;

  private final static int ok = 200;
  private final static int created = 201;
  private final static int forbidden = 403;

  public BanksRestApi(IBankLogic logic) {
    this.bankServiceLogic = logic;
    bindAllMethods();
  }

  /**
   * Binds all REST api calls.
   */
  public void bindAllMethods() {
    bindPostBankPlayer();
    bindGetBankPlayer();
    bindPostBankTransferTo();
    bindPostBankTransferFrom();
    bindPostBankTransferFromTo();
  }

  /**
   * Creates a new account for player.
   * <code>POST /banks/{gameId}/players.</code>
   */
  public void bindPostBankPlayer() {
    post("/banks/:gameId/players", (request, response) -> {
      String gameId = request.params(":gameId");
      String playerId = request.body();
      if (bankServiceLogic.registerPlayerForGame(gameId, playerId)) {
        response.status(created);
        return "";
      }
      response.status(forbidden);
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
      IAccount account = bankServiceLogic.getAccount(gameId, playerId);
      response.status(ok);
      return "" + account.getBalance();
    });
  }

  /**
   * Transfers an amount of money from bank to player.
   * <code>post /banks/{gameid}/transfer/to/{to}/{amount}</code>
   */
  public void bindPostBankTransferTo() {
    post("/banks/:gameId/transfer/to/:to/:amount", (request, response) -> {
      response.status(404);
      return "No Impl";
    });
  }

  /**
   * Transfers an amount of money from player to bank.
   * <code>post /banks/{gameid}/transfer/from/{from}/{amount}</code>
   */
  public void bindPostBankTransferFrom() {
    post("/banks/:gameId/transfer/from/:from/:amount", (request, response) -> {
      response.status(404);
      return "No Impl";
    });
  }

  /**
   * Tranfers an amount of money from player to another player.
   * <code>post /banks/{gameid}/transfer/from/{from}/to/{to}/{amount}</code>
   */
  public void bindPostBankTransferFromTo() {
    post("/banks/:gameId/transfer/from/:from/to/:to/:amount", (request, response) -> {
      response.status(404);
      return "No Impl";
    });
  }
}
