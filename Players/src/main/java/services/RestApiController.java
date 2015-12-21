package services;


import entities.Event;
import entities.Player;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
@RestController public class RestApiController {

  private PlayerManager playerManager = new PlayerManager();
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(RestApiController.class);

  public RestApiController() {
  }

  @RequestMapping(method = RequestMethod.GET, value = "/player")
  public ResponseEntity<Player> getPlayerById() {
    Player result = playerManager.getOurPlayer();
    return new ResponseEntity<Player>(result, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST)
  public void informsOurTurn() {
    playerManager.informOurTurn();
  }

  @RequestMapping(method = RequestMethod.POST)
  public void informNewEvent(@RequestBody Event event) {
    playerManager.informNewEvent(event);
  }

  /* _________________________________________________________________________________________
  *
  * TODO !!!!From Client side!!!! Just set up Sync con from Client side?
  */

  @RequestMapping(method = RequestMethod.POST, value = "/player/register")
  public void registerPlayer(@RequestBody Player player) {
    if (playerManager.getOurPlayer()==null) {
      playerManager.setOurPlayer(player);
      //TODO setup sync connection
    } else {
      log.info("Player: \"" + player.getName() + "\" tried to register at this service but Player: "
          + "\"" + playerManager.getOurPlayer().getName() + "\" already owns it.");
    }
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/player/delete")
  public ResponseEntity removePlayer(@RequestParam("id") String id, @RequestParam("pw") String pw) {
    ResponseEntity result;
    if (playerManager.deletePlayer(id,pw)) {
      result = new ResponseEntity(HttpStatus.OK);
    } else {
      result = new ResponseEntity(HttpStatus.FORBIDDEN);
    }
    return result;
  }
}
