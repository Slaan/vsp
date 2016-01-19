package vsp.banks;

import com.google.gson.Gson;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import vsp.banks.access.CommitFacade;
import vsp.banks.access.Facade;
import vsp.banks.business.logic.bank.BanksLogic;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogicImmutable;
import vsp.banks.business.logic.twophasecommit.TwoPhaseCommitLogic;
import vsp.banks.business.logic.twophasecommit.interfaces.ITwoPhaseCommit;
import vsp.banks.data.entities.Account;
import vsp.banks.data.entities.Player;
import vsp.banks.data.values.Components;
import vsp.banks.data.values.Game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static spark.Spark.stop;
import static vsp.banks.data.values.StatusCodes.*;

/**
 * You need to start the services by hand!
 * Created by alex on 1/19/16.
 */
@Test
public class TestServiceReplicates {
  Gson jsonConverter;

  String uri = "localhost:4567";

  Player player1 = new Player("player1", "bob", "localhost/player/1");
  Player player2 = new Player("player2", "alice", "localhost/player/2");
  Player player3 = new Player("player3", "hans", "localhost/player/3");

  /**
   * Setup test entities.
   */
  @BeforeClass
  public void setUp() {
    jsonConverter = new Gson();
    this.uri = "http://" + uri;
  }

  @AfterClass
  public void tearDown() {
    stop();
  }


  @Test(groups = "setGameAndRegisterAccounts")
  public void test_singleInstance_setGameAndRegisterAccounts() {
    Set<Player> players = new HashSet<>(Arrays.asList(player1, player2, player3));
    Components components = new Components("asdf", "asdf", "asdf", "asdf", "asdf", "asdf", "asdf");
    Game game = new Game("game1", "localhost/games/game1", players, components);
    String gameAsJson = jsonConverter.toJson(game);
    System.out.println(gameAsJson);
    given().body(gameAsJson)
        .put(uri + "/banks/" + game.getGameid()).then().statusCode(ok);

    Account playerAcc1 = new Account(player1, 5000);
    String playerAccAsJson1 = jsonConverter.toJson(playerAcc1);
    given().body(playerAccAsJson1).post(uri + "/banks/" + game.getGameid() + "/players").then()
        .statusCode(created);
    Account playerAcc2 = new Account(player2, 5000);
    String playerAccAsJson2 = jsonConverter.toJson(playerAcc2);
    given().body(playerAccAsJson2).post(uri + "/banks/" + game.getGameid() + "/players").then()
        .statusCode(created);
    given().body(playerAccAsJson2).post(uri + "/banks/" + game.getGameid() + "/players").then()
        .statusCode(conflict);

  }


  @Test(dependsOnGroups = {"setGameAndRegisterAccounts"})
  public void test_singleInstance_makeTransfers_playerToPlayer() throws NotFoundException {
    String gameId = "game1";

    String requestUri = uri + "/banks/" + gameId + "/transfer/from/player1/to/player2/3000";
    given().body("no reason").post(requestUri).then().statusCode(created);
    String saldo = given().get(uri + "/banks/" + gameId + "/players/player1")
        .then().statusCode(ok).extract().body().asString();
    assertEquals(Integer.parseInt(saldo), 2000);
    saldo = given().get(uri + "/banks/" + gameId + "/players/player2")
        .then().statusCode(ok).extract().body().asString();
    assertEquals(Integer.parseInt(saldo), 8000);

    requestUri = uri + "/banks/" + gameId + "/transfer/from/player1/to/player2/3000";
    given().body("no reason").post(requestUri).then().statusCode(forbidden);
    saldo = given().get(uri + "/banks/" + gameId + "/players/player1")
        .then().statusCode(ok).extract().body().asString();
    assertEquals(Integer.parseInt(saldo), 2000);
    saldo = given().get(uri + "/banks/" + gameId + "/players/player2")
        .then().statusCode(ok).extract().body().asString();
    assertEquals(Integer.parseInt(saldo), 8000);

    requestUri = uri + "/banks/" + gameId + "/transfer/from/player2/to/player1/3000";
    given().body("no reason").post(requestUri).then().statusCode(created);
    saldo = given().get(uri + "/banks/" + gameId + "/players/player1")
        .then().statusCode(ok).extract().body().asString();
    assertEquals(Integer.parseInt(saldo), 5000);
    saldo = given().get(uri + "/banks/" + gameId + "/players/player2")
        .then().statusCode(ok).extract().body().asString();
    assertEquals(Integer.parseInt(saldo), 5000);
  }

  @Test(dependsOnGroups = {"setGameAndRegisterAccounts"})
  public void test_commitFacadeAndCloneService_applyTransferInGame_BankToPlayer_and_PlayerToBank()
      throws NotFoundException {
    String gameId = "game1";

    String requestUri = uri + "/banks/" + gameId + "/transfer/from/player1/3000";
    given().body("no reason").post(requestUri).then().statusCode(created);
    String saldo = given().get(uri + "/banks/" + gameId + "/players/player1")
        .then().statusCode(ok).extract().body().asString();
    assertEquals(Integer.parseInt(saldo), 2000);

    requestUri = uri + "/banks/" + gameId + "/transfer/from/player1/3000";
    given().body("no reason").post(requestUri).then().statusCode(forbidden);
    saldo = given().get(uri + "/banks/" + gameId + "/players/player1")
        .then().statusCode(ok).extract().body().asString();
    assertEquals(Integer.parseInt(saldo), 2000);

    requestUri = uri + "/banks/" + gameId + "/transfer/to/player1/3000";
    given().body("no reason").post(requestUri).then().statusCode(created);
    saldo = given().get(uri + "/banks/" + gameId + "/players/player1")
        .then().statusCode(ok).extract().body().asString();
    assertEquals(Integer.parseInt(saldo), 5000);
  }
}
