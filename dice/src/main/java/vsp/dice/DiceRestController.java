package vsp.dice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * Created by alex on 10/24/15.
 */
@RestController
public class DiceRestController {

  @RequestMapping("/dice")
  public Roll roll() {
    System.out.println("dice rolled!");
    return new Roll(new Random().nextInt(6) + 1);
  }

}
