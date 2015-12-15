package services;


import entities.Event;
import entities.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
@RestController
public class RestApiController {


    private PlayerManager playerManager = new PlayerManager();

    public RestApiController() {}

    @RequestMapping(method = RequestMethod.GET, value = "/player")
    public ResponseEntity<Player> getPlayer() {
        Player result = playerManager.getOurPlayer();
        return new ResponseEntity<Player>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/player/turn")
    public void informsOurTurn() {
        playerManager.informOurTurn();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/player/event")
    public void informNewEvent(@RequestBody Event event) {
        playerManager.informNewEvent(event);
    }
}
