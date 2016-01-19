package vsp.banks;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import vsp.banks.access.CommitFacade;
import vsp.banks.access.Facade;
import vsp.banks.business.logic.bank.BanksLogic;
import vsp.banks.business.logic.twophasecommit.TwoPhaseCommitLogic;

/**
 * Created by alex on 1/18/16.
 */
@Test
public class TestSingleInstance {

  BanksLogic serviceLogic;

  CommitFacade commitFacade;

  TwoPhaseCommitLogic twoPhaseCommit;

  Facade banksController;

  @BeforeClass
  public void setUp() {
    this.serviceLogic = new BanksLogic();

    commitFacade = new CommitFacade(serviceLogic);
    twoPhaseCommit = new TwoPhaseCommitLogic(serviceLogic, "localhost:4567");
    banksController = new Facade(serviceLogic, twoPhaseCommit);
  }


}
