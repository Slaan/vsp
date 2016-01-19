package vsp.banks.access;

import spark.Request;
import spark.Response;
import vsp.banks.business.converter.TransferConverter;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.data.dtos.TransferCommitDto;
import vsp.banks.data.entities.Account;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

import static spark.Spark.*;
import static vsp.banks.data.values.StatusCodes.*;
/**
 * Created by alex on 1/17/16.
 */
public class CommitFacade extends AbstractFacade {

  IBanksLogic bankLogic;

  TransferConverter transferConverter;

  /**
   * Creates a new instance of a commit facade.
   */
  public CommitFacade(IBanksLogic logic) {
    super();
    this.bankLogic = logic;
    this.transferConverter = new TransferConverter();
    bindAllMethods();
  }

  @Override
  public void bindAllMethods() {
    bindPostBankLock();
    bindGetBankLock();
    bindDeleteBankLock();
    bindPutGame();
    bindPostTransfer();
    bindPostBankAccount();
  }

  /**
   * POST /bank/lock.
   *  when successful     -> 201 (created).
   *  when already locked -> 409 (conflict).
   */
  public void bindPostBankLock() {
    put("/replicate/banks/:gameId/lock", (request, response) -> {
      String gameId = request.params(":gameId");
      response.status(ok);
      try {
        if (!bankLogic.lock(gameId)) {
          response.status(conflict);
        }
      } catch (BankNotFoundException exception) {
        response.status(notFound);
      }
      return "";
    });
  }

  /**
   * GET /replicate/banks/{gameId}/lock.
   *  when locked     -> ok.
   *  when not locked -> conflict.
   *  when not bank with gameId -> notFound.
   */
  public void bindGetBankLock() {
    get("/replicate/banks/:gameId/lock", (request, response) -> {
      String gameId = request.params(":gameId");
      response.status(ok);
      try {
        if (!bankLogic.isLocked(gameId)) {
          response.status(conflict);
        }
      } catch (BankNotFoundException exception) {
        response.status(notFound);
      }
      return "";
    });
  }

  /**
   * DELETE /replicate/banks/{gameId}/lock.
   *  when successful -> 200.
   */
  public void bindDeleteBankLock() {
    delete("/replicate/banks/:gameId/lock", (request, response) -> {
      String gameId = request.params(":gameId");
      response.status(ok);
      try {
        if (!this.bankLogic.unlock(gameId)) {
          response.status(noContent);
        }
      } catch (BankNotFoundException exception) {
        response.status(notFound);
      }
      return "";
    });
  }

  /**
   * PUT /replicate/banks/{gameId}.
   *  when created -> ok.
   */
  public void bindPutGame() {
    put("/replicate/banks/:gameId", (request, response) -> {
      Game game = this.jsonConverter.fromJson(request.body(), Game.class);
      this.bankLogic.setGame(game);
      return "";
    });
  }

  /**
   * POST /replicate/banks/{gameId}/transfer.
   *  when couldn't apply -> forbidden.
   *  when successfully -> created.
   *  when bank with gameId not found -> notFound.
   */
  public void bindPostTransfer() {
    post("/replicate/banks/:gameId/transfer", (request, response) -> {
      String gameId = request.params(":gameId");
      TransferCommitDto dto = jsonConverter.fromJson(request.body(), TransferCommitDto.class);
      Transfer transfer = transferConverter.dtoToEntity(dto);
      try {
        if (!this.bankLogic.applyTransferInGame(gameId, transfer)) {
          response.status(forbidden);
          return "";
        }
      } catch (NotFoundException exception) {
        response.status(notFound);
      }
      response.status(created);
      return "";
    });
  }

  /**
   * POST /banks/{gameId}/players.
   *  when player already exists -> conflict.
   *  when player account created -> created.
   */
  public void bindPostBankAccount() {
    post("/banks/:gameId/players", (request, response) -> {
      String gameId = request.params(":gameId");
      Account account = jsonConverter.fromJson(request.body(), Account.class);
      if (bankLogic.registerPlayerForGame(gameId, account)) {
        response.status(created);
        return "";
      }
      response.status(conflict);
      return "";
    });
  }

}
