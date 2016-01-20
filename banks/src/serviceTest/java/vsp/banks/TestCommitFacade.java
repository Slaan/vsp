package vsp.banks;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import vsp.banks.access.CommitFacade;
import vsp.banks.business.adapter.CloneService;
import vsp.banks.business.adapter.exceptions.NetworkException;
import vsp.banks.business.adapter.interfaces.ICloneService;
import vsp.banks.business.logic.bank.BanksLogic;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.data.entities.Account;
import vsp.banks.data.entities.Player;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Place;
import vsp.banks.data.values.Transfer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.*;
import static spark.Spark.halt;
import static spark.Spark.stop;
import static vsp.banks.data.values.StatusCodes.*;

/**
 * Created by alex on 1/18/16.
 */
@Test(groups = "TestCommitFacade")
public class TestCommitFacade {

  IBanksLogic banksLogic;

  CommitFacade facade;

  ICloneService cloneService;

  /**
   * Setup test entites.
   */
  @BeforeClass
  public void setUp() throws BankNotFoundException, NetworkException {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.port = 4567;

    cloneService = new CloneService("localhost:4567");

    Place place = new Place("Wacholderallee");
    Player player1 = new Player("player1", "bob", "localhost/player/");
    Player player2 = new Player("player2", "alice", "localhost/player/");
    Player player3 = new Player("player3", "hans", "localhost/player/");
    Player notInGame = new Player("notInGame", "hans", "localhost/player/");
    this.banksLogic = new BanksLogic();
    this.facade = new CommitFacade(banksLogic);
    Set<Player> players = new HashSet<>(Arrays.asList(player1, player2, player3));
    Game game = new Game("game1", "localhost/games/game1", players, null);
    banksLogic.setGame(game);
    Account account1 = new Account(player1, 5000);
    Account account2 = new Account(player2, 5000);
    banksLogic.registerPlayerForGame("game1", account1);
    banksLogic.registerPlayerForGame("game1", account2);
  }

  @AfterClass
  public void tearDown() {
    stop();
  }

  @Test
  public void test_commitFacade_lock_and_unlock() {
    given().put("/replicate/banks/" + "game1" + "/lock").then().statusCode(ok);
    given().put("/replicate/banks/" + "game1" + "/lock").then().statusCode(conflict);
    given().put("/replicate/banks/" + "game1" + "/lock").then().statusCode(conflict);
    given().delete("/replicate/banks/" + "game1" + "/lock").then().statusCode(ok);
    given().delete("/replicate/banks/" + "game1" + "/lock").then().statusCode(noContent);
    given().put("/replicate/banks/" + "game1" + "/lock").then().statusCode(ok);
  }

  @Test
  public void test_commitFacadeAndCloneService_lockUnlockAndIsLocked() {
    try {
      assertTrue(cloneService.lock("game1"));
      assertTrue(cloneService.isLocked("game1"));
      assertTrue(cloneService.isLocked("game1"));
      assertFalse(cloneService.lock("game1"));
      assertTrue(cloneService.isLocked("game1"));
      assertFalse(cloneService.lock("game1"));
      assertTrue(cloneService.unlock("game1"));
      assertFalse(cloneService.isLocked("game1"));
      assertFalse(cloneService.unlock("game1"));
      assertTrue(cloneService.lock("game1"));
      assertTrue(cloneService.unlock("game1"));
    } catch (BankNotFoundException e) {
      e.printStackTrace();
    } catch (NetworkException e) {
      e.printStackTrace();
    }
  }

  @Test(expectedExceptions = BankNotFoundException.class)
  public void test_commitFacadeAndCloneService_lock_and_unlock_expectBanksNotFoundException()
          throws BankNotFoundException, NetworkException {
    cloneService.lock("thisBankDoesNotExist");
  }

  @Test
  public void test_commitFacadeAndCloneService_applyTransferInGame_PlayerToPlayer()
          throws NotFoundException, InterruptedException, NetworkException {
    Transfer transfer = new Transfer("player1", "player2", 3000, "no reason", "no event");
    if (!cloneService.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player1'.");
    }
    assertEquals(banksLogic.getAccount("game1", "player1").getSaldo(), 2000);
    assertEquals(banksLogic.getAccount("game1", "player2").getSaldo(), 8000);

    transfer = new Transfer("player2", "player1", 3000, "no reason", "no event");
    if (!cloneService.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player2'.");
    }
    assertEquals(banksLogic.getAccount("game1", "player1").getSaldo(), 5000);
    assertEquals(banksLogic.getAccount("game1", "player2").getSaldo(), 5000);

    transfer = new Transfer("player2", "player1", 5000, "no reason", "no event");
    if (!cloneService.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player2'.");
    }
    assertEquals(banksLogic.getAccount("game1", "player1").getSaldo(), 10000);
    assertEquals(banksLogic.getAccount("game1", "player2").getSaldo(), 0);

    transfer = new Transfer("player2", "player1", 5001, "no reason", "no event");
    if (cloneService.applyTransferInGame("game1", transfer)) {
      fail("There is NOT ENOUGH money on account of 'player2'.");
    }
    assertEquals(banksLogic.getAccount("game1", "player1").getSaldo(), 10000);
    assertEquals(banksLogic.getAccount("game1", "player2").getSaldo(), 0);

    transfer = new Transfer("player1", "player2", 5000, "no reason", "no event");
    if (!cloneService.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player1'.");
    }
    assertEquals(banksLogic.getAccount("game1", "player1").getSaldo(), 5000);
    assertEquals(banksLogic.getAccount("game1", "player2").getSaldo(), 5000);
  }

  @Test
  public void test_commitFacadeAndCloneService_applyTransferInGame_BankToPlayer_and_PlayerToBank()
          throws NotFoundException, NetworkException {
    assertEquals(banksLogic.getAccount("game1", "player1").getSaldo(), 5000);

    Transfer transfer = Transfer.bankToPlayer("player1", 5000, "no reason", "event");
    if (!cloneService.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player1'.");
    }
    assertEquals(banksLogic.getAccount("game1", "player1").getSaldo(), 10000);

    transfer = Transfer.playerToBank("player1", 10000, "no reason", "event");
    if (!cloneService.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player1'.");
    }
    assertEquals(banksLogic.getAccount("game1", "player1").getSaldo(), 0);

    transfer = Transfer.playerToBank("player1", 1, "no reason", "event");
    if (cloneService.applyTransferInGame("game1", transfer)) {
      fail("There is NOT ENOUGH money on account of 'player1'.");
    }
    assertEquals(banksLogic.getAccount("game1", "player1").getSaldo(), 0);

    transfer = Transfer.bankToPlayer("player1", 5000, "no reason", "event");
    if (!cloneService.applyTransferInGame("game1", transfer)) {
      fail("There is actually enough money on account of 'player1'.");
    }
    assertEquals(banksLogic.getAccount("game1", "player1").getSaldo(), 5000);
  }

  @Test
  public void test_commitFacadeAndCloneService_setGame() throws BankNotFoundException,
          NetworkException {
    Player player1 = new Player("player1", "bob", "localhost/player/");
    Player player2 = new Player("player2", "alice", "localhost/player/");
    Player player3 = new Player("player3", "hans", "localhost/player/");
    Player notInGame = new Player("notInGame", "hans", "localhost/player/");
    Set<Player> players = new HashSet<>(Arrays.asList(player1, player2, player3));
    Game game = new Game("game2", "http://localhost/games/game2", players, null);
    this.cloneService.setGame(game);
    Account account1 = new Account(player1, 5000);
    Account account2 = new Account(player2, 5000);
    if (!this.cloneService.registerPlayerForGame(game.getGameid(), account1)) {
      fail("Couldn't register account1.");
    }
    if (!this.cloneService.registerPlayerForGame(game.getGameid(), account2)) {
      fail("Couldn't register account2.");
    }
  }
}
