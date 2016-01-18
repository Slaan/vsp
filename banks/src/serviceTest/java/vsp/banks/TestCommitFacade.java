package vsp.banks;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import vsp.banks.access.CommitFacade;
import vsp.banks.business.logic.bank.BanksLogic;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.data.entities.Player;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Place;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.jayway.restassured.RestAssured.given;
import static vsp.banks.data.values.StatusCodes.*;
/**
 * Created by alex on 1/18/16.
 */
@Test
public class TestCommitFacade {

  IBanksLogic banksLogic;

  CommitFacade facade;

  @BeforeTest
  public void setUp() throws InterruptedException {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.port = 4567;

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
  }

  @Test
  public void test_commitFacade_lock_and_unlock() {
    given().put("/replicate/lock/" + "game1").then().statusCode(ok);
    given().put("/replicate/lock/" + "game1").then().statusCode(conflict);
    given().put("/replicate/lock/" + "game1").then().statusCode(conflict);
    given().delete("/replicate/lock/" + "game1").then().statusCode(ok);
    given().delete("/replicate/lock/" + "game1").then().statusCode(noContent);
    given().put("/replicate/lock/" + "game1").then().statusCode(ok);
  }

}
