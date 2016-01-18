package vsp.banks.access;

import spark.Request;
import spark.Response;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;

import static spark.Spark.delete;
import static spark.Spark.post;
import static spark.Spark.put;
import static vsp.banks.data.values.StatusCodes.*;
/**
 * Created by alex on 1/17/16.
 */
public class CommitFacade extends AbstractFacade {

  IBanksLogic bankLogic;

  public CommitFacade(IBanksLogic logic) {
    super();
    this.bankLogic = logic;
    bindAllMethods();
  }

  @Override
  public void bindAllMethods() {
    bindPostBankLock();
    bindDeleteBankLock();
  }

  /**
   * POST /bank/lock.
   *  when successful     -> 201 (created).
   *  when already locked -> 409 (conflict).
   */
  public void bindPostBankLock() {
    put("/replicate/lock/:gameId", (request, response) -> {
      String gameId = request.params(":gameId");
      response.status(ok);
      if (!bankLogic.lock(gameId)) {
        response.status(conflict);
      }
      return "";
    });
  }

  /**
   * DELETE /bank/lock
   *  when successful -> 200
   */
  public void bindDeleteBankLock() {
    delete("/replicate/lock/:gameId", (request, response) -> {
      String gameId = request.params(":gameId");
      response.status(ok);
      if (!this.bankLogic.unlock(gameId)) {
        response.status(noContent);
      }
      return "";
    });
  }



}
