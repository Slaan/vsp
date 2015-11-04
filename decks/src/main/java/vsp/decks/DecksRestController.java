package vsp.decks;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by alex on 10/24/15.
 */
@RestController
@RequestMapping("/decks")
public class DecksRestController {

  private Chance    chance    = new Chance("Go to jail!", "Go now!");
  private Community community = new Community("Go to jail!", "Go now!");

  @RequestMapping(value = "/{gameid}/chance", method = RequestMethod.GET)
  public Chance chance(@PathVariable String gameid) {
    System.out.println(gameid);
    return chance;
  }

  @RequestMapping(value = "/{gameid}/community", method = RequestMethod.GET)
  public Community community(@PathVariable String gameid) {
    System.out.println(gameid);
    return community;
  }
}
