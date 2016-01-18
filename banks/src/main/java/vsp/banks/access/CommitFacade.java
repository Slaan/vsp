package vsp.banks.access;

import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.business.logic.twophasecommit.interfaces.ITwoPhaseCommit;

import static spark.Spark.*;
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
    put("/lock/:gameId", (request, response) -> {
      String gameId = request.params(":gameId");
      if (!bankLogic.lock(gameId)) {
        response.status(conflict);
        return "";
      }
      response.status(created);
      return "";
    });
  }

  /**
   * DELETE /bank/lock
   *  when successful -> 200
   */
  public void bindDeleteBankLock() {
    delete("/lock/:gameId", (request, response) -> {
      String gameId = request.params(":gameId");
      this.bankLogic.unlock(gameId);
      response.status(ok);
      return "";
    });
  }



}
