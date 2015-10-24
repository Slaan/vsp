package vsp.dice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by alex on 10/24/15.
 */
@RestController
public class DiceRestController {

  @RequestMapping("/dice")
  public Roll roll() {
    return new Roll(new Random().nextInt(6) + 1);
  }
}
