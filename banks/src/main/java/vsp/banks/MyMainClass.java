package vsp.banks;

import com.mashape.unirest.http.Unirest;
import vsp.banks.access.CommitFacade;
import vsp.banks.business.adapter.CloneService;
import vsp.banks.business.logic.bank.BanksLogic;
import vsp.banks.business.discovery.YellowPages;
import vsp.banks.access.Facade;
import vsp.banks.business.logic.twophasecommit.TwoPhaseCommitLogic;

import static spark.Spark.port;

public class MyMainClass {

  private static String yellowPageUrl = "http://vs-docker.informatik.haw-hamburg.de:8053/services";

  public static String getIp() {
    return "localhost";
  }

  /**
   * Main function. Entry point of application.
   */
  public static void main(String[] args) {
    // default mapping of spark-rest
    int port = 4567;
    port(port);

    String ownUri = getIp();
    //    if (args.length == 1) {
    //      YellowPages yp = new YellowPages(yellowPageUrl);
    //      try {
    //        String name = "Banks";
    //        String description = "desc";
    //        String service = "/banks";
    //        String uri = "http://localhost:4567";
    //        yp.register(name, description, service, uri);
    //      } catch (Exception e) {
    //        System.err.println("Warning: 'Couldn't connect to yellow page server.");
    //      }
    //    }
    // Dependency Injections
    BanksLogic serviceLogic = new BanksLogic();

    new CommitFacade(serviceLogic);
    TwoPhaseCommitLogic twoPhaseCommit =
            new TwoPhaseCommitLogic(serviceLogic, ownUri + ":" + port);
    new Facade(serviceLogic, twoPhaseCommit);
  }

}
