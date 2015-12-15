package vsp.dice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Daniel Hofmeister on 15.12.2015.
 */
public class YellowPageRegisterThread implements Runnable {

  private static final Logger log = LoggerFactory.getLogger(MyMainClass.class);
  private static String yellowPageUrl =
      "http://vs-docker.informatik.haw-hamburg.de/ports/8053/services";


  @Override public void run() {
    log.info("trying to register YellowPages, waiting 10s for spring to start up");
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    RestTemplate template = new RestTemplate();
    Service diceService = new Service("DiceService", "Roll a dice! 100% random", "DiceService",
        "https://vs-docker/ports/13422/dice");
    template.postForEntity(yellowPageUrl, null, null, diceService);
    log.info("Docker YP Env Var = " + System.getenv("DIRECTORY_SERVICE_URL"));
    log.info("Trying to register at Yellow Pages");

  }
}
