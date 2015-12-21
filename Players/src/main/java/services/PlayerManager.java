package services;

import entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
public class PlayerManager {

  private Player ourPlayer;
  private static final Logger log = LoggerFactory.getLogger(PlayerManager.class);
  private static String yellowPageUrl =
      "https://vs-docker.informatik.haw-hamburg.de/ports/8053/services";

  public PlayerManager() {
    registerYellowPages();
  }

  private void registerYellowPages() {
    RestTemplate template = new RestTemplate();
    Service playerService =
        new Service("PlayerService", "Service connecting the Client to our docker env",
            "playerService", "https://vs-docker/ports/10343/player");
    template.postForEntity(yellowPageUrl, null, null, playerService);
    log.info("Trying to register at Yellow Pages");
  }

  public void setOurPlayer(Player player) {
    this.ourPlayer = player;
  }

  public Player getOurPlayer() {
    return ourPlayer;
  }

  public void informOurTurn() {
    ourPlayer.setReady(true);
    //TODO inform client
  }

  public void informNewEvent(Event event) {
    log.info("An event happened! Type: " + event.getType() +", Reason: " + event.getReason());
    //TODO inform client about new event
  }

  public boolean deletePlayer(String id, String pw) {
    if (this.ourPlayer.getId().equals(id) && this.ourPlayer.getPassword().equals(pw)) {
      ourPlayer = null;
      return true;
    } else {
      return false;
    }
  }
}
