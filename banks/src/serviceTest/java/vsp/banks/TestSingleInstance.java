package vsp.banks;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import vsp.banks.access.CommitFacade;
import vsp.banks.access.Facade;
import vsp.banks.business.logic.bank.BanksLogic;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.business.logic.bank.exceptions.PlayerNotFoundException;
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
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Created by alex on 1/18/16.
 */
@Test
public class TestSingleInstance {

  IBanksLogicImmutable serviceLogic;

  CommitFacade commitFacade;

  ITwoPhaseCommit twoPhaseCommit;
  DebugTwoPhaseCommit twoPhaseCommitDebug;

  Facade banksController;

  Player player1 = new Player("player1", "bob", "localhost/player/1");
  Player player2 = new Player("player2", "alice", "localhost/player/2");
  Player player3 = new Player("player3", "hans", "localhost/player/3");

  @BeforeClass
  public void setUp() {
    BanksLogic serviceLogic = new BanksLogic();
    this.serviceLogic = serviceLogic;
    commitFacade = new CommitFacade(serviceLogic);
    TwoPhaseCommitLogic twoPhaseCommitLogic = new TwoPhaseCommitLogic(serviceLogic, "localhost:4567");
    twoPhaseCommit = twoPhaseCommitLogic;
    twoPhaseCommitDebug = twoPhaseCommitLogic;
    banksController = new Facade(serviceLogic, twoPhaseCommit);
  }


  @Test(groups = "setGameAndRegisterAccounts")
  public void test_singleInstance_setGameAndRegisterAccounts() {
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
  public void test_singleInstance_lockAndUnlock() throws BankNotFoundException {
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
  public void test_singleInstance_makeTransfers_playerToPlayer() throws NotFoundException {
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
}
