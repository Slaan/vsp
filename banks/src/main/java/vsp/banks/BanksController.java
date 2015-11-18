package vsp.banks;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by alex on 11/18/15.
 */
public class BanksController {

  public BanksController() {
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
      response.status(404);
      return "No Impl";
    });
  }

  /**
   * Returns the balance of player account to player.
   * <code>GET /banks/{gameId}/players/{playerId}</code>
   */
  public void bindGetBankPlayer() {
    get("/banks/:gameId/players/:playerId", (request, response) -> {
      response.status(404);
      return "No Impl";
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
