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
    private static String yellowPageUrl = "https://vs-docker.informatik.haw-hamburg.de/ports/8053/services";

    public PlayerManager() {
        registerYellowPages();
    }

    private void registerYellowPages() {
        RestTemplate template = new RestTemplate();
        Service playerService = new Service("PlayerService", "Service connecting the Client to our docker env",
                "playerService", "https://vs-docker/ports/10343/player");
        template.postForEntity(yellowPageUrl, null, null, playerService);
        log.info("Docker YP Env Var = " + System.getenv("DIRECTORY_SERVICE_URL"));
        log.info("Trying to register at Yellow Pages");
    }


    public Player getOurPlayer() {
        return ourPlayer;
    }

    public void informOurTurn() {
        ourPlayer.setReady(true);
        //TODO inform client
    }

    public void informNewEvent(Event event) {
        //TODO inform client about new event
    }
}
