package vsp.decks;

import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.*;

/**
 * Please start gradle task run before running unit-tests.
 * Created by alex on 04.11.15.
 */
@Test
public class TestService {

  @Test
  public void testChanceCard() {
    expect().statusCode(200)
        .given().when()
        .get("http://localhost:8080/decks/test-game/chance");
  }

  @Test
  public void testCommunityCard() {
    expect().statusCode(200)
        .given().when()
        .get("http://localhost:8080/decks/test-game/community");
  }
}
