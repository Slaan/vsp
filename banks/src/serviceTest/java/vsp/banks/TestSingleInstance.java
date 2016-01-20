package vsp.banks;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import vsp.banks.business.adapter.exceptions.NetworkException;
import vsp.banks.business.logic.bank.BanksLogic;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogicImmutable;
import vsp.banks.business.logic.twophasecommit.TwoPhaseCommitLogic;
import vsp.banks.business.logic.twophasecommit.interfaces.DebugTwoPhaseCommit;
import vsp.banks.business.logic.twophasecommit.interfaces.ITwoPhaseCommit;
import vsp.banks.data.entities.Account;
import vsp.banks.data.entities.Player;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Transfer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static spark.Spark.stop;

/**
 * Created by alex on 1/18/16.
 */
@Test
public class TestSingleInstance {

  IBanksLogicImmutable serviceLogic;

  ITwoPhaseCommit twoPhaseCommit;
  DebugTwoPhaseCommit twoPhaseCommitDebug;

  Player player1 = new Player("player1", "bob", "localhost/player/1");
  Player player2 = new Player("player2", "alice", "localhost/player/2");
  Player player3 = new Player("player3", "hans", "localhost/player/3");

  /**
   * Setup test entites.
   */
  @BeforeClass
  public void setUp() {
    BanksLogic serviceLogic = new BanksLogic();
    this.serviceLogic = serviceLogic;
    TwoPhaseCommitLogic twoPhaseCommitLogic =
        new TwoPhaseCommitLogic(serviceLogic, "localhost:4567");
    twoPhaseCommit = twoPhaseCommitLogic;
    twoPhaseCommitDebug = twoPhaseCommitLogic;
  }

  /**
   * Tear down running application.
   */
  @AfterClass
  public void tearDown() {
    stop();
  }

  @Test(groups = "setGameAndRegisterAccounts")
  public void test_singleInstance_setGameAndRegisterAccounts() throws NetworkException {
    Set<Player> players = new HashSet<>(Arrays.asList(player1, player2, player3));
    Game game = new Game("game1", "localhost/games/game1", players, null);
    twoPhaseCommit.setGame(game);
    Account playerAcc1 = new Account(player1, 5000);
    Account playerAcc2 = new Account(player2, 5000);
    assertEquals(twoPhaseCommit.getUris().size(), 1);
    assertEquals(twoPhaseCommitDebug.getAllServices().size(), 1);
    try {
      if (!twoPhaseCommit.registerPlayerForGame(game.getGameid(), playerAcc1)) {
        fail("Couldn't register account1.");
      }
      if (!twoPhaseCommit.registerPlayerForGame(game.getGameid(), playerAcc2)) {
        fail("Couldn't register account2.");
      }
      if (twoPhaseCommit.registerPlayerForGame(game.getGameid(), playerAcc2)) {
        fail("Could register account2 a second time.");
      }
      Account playerAccCopy1 = this.serviceLogic.getAccount("game1", "player1");
      assertEquals(playerAcc1, playerAccCopy1);
      if (twoPhaseCommit.isLocked("game1")) {
        fail("Unexpected lock!");
      }
    } catch (NotFoundException e) {
      fail("Did not found existing bank/account.");
    }
  }

  @Test(dependsOnGroups = "setGameAndRegisterAccounts", groups = "lockAndUnlock")
  public void test_singleInstance_lockAndUnlock() throws BankNotFoundException, NetworkException {
    assertEquals(twoPhaseCommit.getUris().size(), 1);
    assertEquals(twoPhaseCommitDebug.getAllServices().size(), 1);
    String gameName = "game1";
    if (!twoPhaseCommit.lockBankOnAllServices(gameName)) {
      fail("Couldn't lock 'game1' on all banks.");
    }
    if (!twoPhaseCommit.isLocked(gameName)) {
      fail("Lock expected!");
    }
    if (!twoPhaseCommit.unlockBankOnAllServices(gameName)) {
      fail("Couldn't unlock 'game1' on all banks.");
    }
    if (twoPhaseCommit.isLocked(gameName)) {
      fail("No lock expected!");
    }
  }

  @Test(dependsOnGroups = {"setGameAndRegisterAccounts", "lockAndUnlock"})
  public void test_singleInstance_makeTransfers_playerToPlayer() throws NotFoundException,
          NetworkException {
    Transfer transfer = new Transfer("player1", "player2", 3000, "no reason", "no event");
    if (!twoPhaseCommit.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player1'.");
    }
    assertEquals(serviceLogic.getAccount("game1", "player1").getSaldo(), 2000);
    assertEquals(serviceLogic.getAccount("game1", "player2").getSaldo(), 8000);

    transfer = new Transfer("player2", "player1", 3000, "no reason", "no event");
    if (!twoPhaseCommit.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player2'.");
    }
    assertEquals(serviceLogic.getAccount("game1", "player1").getSaldo(), 5000);
    assertEquals(serviceLogic.getAccount("game1", "player2").getSaldo(), 5000);

    transfer = new Transfer("player2", "player1", 5000, "no reason", "no event");
    if (!twoPhaseCommit.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player2'.");
    }
    assertEquals(serviceLogic.getAccount("game1", "player1").getSaldo(), 10000);
    assertEquals(serviceLogic.getAccount("game1", "player2").getSaldo(), 0);

    transfer = new Transfer("player2", "player1", 5001, "no reason", "no event");
    if (twoPhaseCommit.applyTransferInGame("game1", transfer)) {
      fail("There is NOT ENOUGH money on account of 'player2'.");
    }
    assertEquals(serviceLogic.getAccount("game1", "player1").getSaldo(), 10000);
    assertEquals(serviceLogic.getAccount("game1", "player2").getSaldo(), 0);

    transfer = new Transfer("player1", "player2", 5000, "no reason", "no event");
    if (!twoPhaseCommit.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player1'.");
    }
    assertEquals(serviceLogic.getAccount("game1", "player1").getSaldo(), 5000);
    assertEquals(serviceLogic.getAccount("game1", "player2").getSaldo(), 5000);
  }

  @Test(dependsOnGroups = {"setGameAndRegisterAccounts", "lockAndUnlock"})
  public void test_commitFacadeAndCloneService_applyTransferInGame_BankToPlayer_and_PlayerToBank()
          throws NotFoundException, NetworkException {
    assertEquals(serviceLogic.getAccount("game1", "player1").getSaldo(), 5000);

    Transfer transfer = Transfer.bankToPlayer("player1", 5000, "no reason", "event");
    if (!twoPhaseCommit.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player1'.");
    }
    assertEquals(serviceLogic.getAccount("game1", "player1").getSaldo(), 10000);

    transfer = Transfer.playerToBank("player1", 10000, "no reason", "event");
    if (!twoPhaseCommit.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player1'.");
    }
    assertEquals(serviceLogic.getAccount("game1", "player1").getSaldo(), 0);

    transfer = Transfer.playerToBank("player1", 1, "no reason", "event");
    if (twoPhaseCommit.applyTransferInGame("game1", transfer)) {
      fail("There is NOT ENOUGH money on account of 'player1'.");
    }
    assertEquals(serviceLogic.getAccount("game1", "player1").getSaldo(), 0);

    transfer = Transfer.bankToPlayer("player1", 5000, "no reason", "event");
    if (!twoPhaseCommit.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player1'.");
    }
    assertEquals(serviceLogic.getAccount("game1", "player1").getSaldo(), 5000);
  }
}
