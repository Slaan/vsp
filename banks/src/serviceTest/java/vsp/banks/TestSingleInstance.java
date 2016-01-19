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

  BanksLogic logic;



  @BeforeClass
  public void setUp() {
    BanksLogic serviceLogic = new BanksLogic();

    CommitFacade commitFacade = new CommitFacade(serviceLogic);
    TwoPhaseCommitLogic twoPhaseCommit = new TwoPhaseCommitLogic(serviceLogic, "localhost:4567");
    Facade banksController = new Facade(serviceLogic, twoPhaseCommit);
  }


}
