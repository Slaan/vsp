package vsp.banks;

import com.jayway.restassured.http.ContentType;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.testng.annotations.Test;

import javax.net.ssl.SSLEngineResult;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import static vsp.banks.values.StatusCodes.*;

/**
 * Created by alex on 04.11.15.
 */
@Test
public class TestService {

  int port = 4567;
  String host = "http://localhost" + ":" + port;


  @Test
  public void test_Session1() {
    String player1Id = "player1";
    String gameId = "game1";
    String gameJson =
        "{\n" + "  \"gameid\": \"" + gameId + "\",\n" + "  \"uri\": \"localost/games/game1\",\n"
            + "  \"players\": [\n" + "    {\n" + "      \"id\": \"" + player1Id + "\",\n"
            + "      \"name\": \"bob\",\n" + "      \"place\": {\n"
            + "        \"name\": \"Schlossallee\"\n" + "      },\n" + "      \"position\": 3,\n"
            + "      \"ready\": true\n" + "    },\n" + "    {\n" + "      \"id\": \"player2\",\n"
            + "      \"name\": \"nick\",\n" + "      \"place\": {\n"
            + "        \"name\": \"Weggehen\"\n" + "      },\n" + "      \"position\": 2,\n"
            + "      \"ready\": true\n" + "    }\n" + "  ],\n" + "  \"components\": {\n"
            + "    \"game\": \"localhost/games\",\n" + "    \"dice\": \"localhost/dice\",\n"
            + "    \"board\": \"localhost/board\",\n" + "    \"bank\": \"localhost/bank\",\n"
            + "    \"broker\": \"localhost/broker\",\n" + "    \"decks\": \"localhost/decks\",\n"
            + "    \"events\": \"localhost/events\"\n" + "  }\n" + "}";
    given().contentType(ContentType.JSON).body(gameJson).put(host + "/banks/" + gameId).then()
        .statusCode(ok);
    given().contentType(ContentType.JSON).body(gameJson).put(host + "/banks/" + gameId).then()
        .statusCode(ok);
    // register player
    String accountJson = "{\n" + "    \"player\": {\n" + "      \"id\": \"" + player1Id + "\",\n"
        + "      \"name\": \"bob\",\n" + "      \"place\": {\n"
        + "        \"name\": \"Schlossallee\"\n" + "      },\n" + "      \"position\": 3,\n"
        + "      \"ready\": true\n" + "    },\n" + "    \"saldo\": 5000\n" + "}";
    given().contentType(ContentType.JSON).body(accountJson)
        .post(host + "/banks/" + gameId + "/players").then().statusCode(created);
    given().contentType(ContentType.JSON).body(accountJson)
        .post(host + "/banks/" + gameId + "/players").then().statusCode(conflict);
    // check saldo of new account
    String startMoney = given().contentType(ContentType.JSON)
        .get(host + "/banks/" + gameId + "/players/" + player1Id).body().asString();
    assertEquals(startMoney, "5000");
    // transfer from bank to player
    given().contentType(ContentType.JSON).body("test ueberweisung")
        .post(host + "/banks/" + gameId + "/transfer/to/" + player1Id + "/" + 500).then()
        .statusCode(200);
    String afterBankTransfer = given().contentType(ContentType.JSON)
        .get(host + "/banks/" + gameId + "/players/" + player1Id).body().asString();
    assertEquals(afterBankTransfer, "5500");
  }

}
