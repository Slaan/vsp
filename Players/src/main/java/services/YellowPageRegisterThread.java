package services;

import entities.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Daniel Hofmeister on 15.12.2015.
 */
public class YellowPageRegisterThread implements Runnable {

  private static final Logger log = LoggerFactory.getLogger(MyMainClass.class);
  private static String yellowPageUrl =
      "http://vs-docker.informatik.haw-hamburg.de:8053/services";


  @Override public void run() {
    log.info("trying to register YellowPages, waiting 10s for spring to start up");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    RestTemplate template = new RestTemplate();
    Service playerService = new Service("PlayerService",
        "Service connecting the Client to our docker env",
        "playerService", "https://vs-docker/ports/13421/player");
    String result = template.postForObject(yellowPageUrl, playerService, String.class);
    log.info("Trying to register at Yellow Pages");

  }
}
