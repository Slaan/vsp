package vsp.dice;

import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Please start gradle task 'run' before running unit-tests
 *
 * Created by alex on 04.11.15.
 */
@Test
public class TestService {

  @Test
  public void testDiceBetween() {
    expect().statusCode(200).
        given().when().
        get("http://localhost:8080/dice");
  }
}
