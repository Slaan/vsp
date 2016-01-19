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


  public static String getIp() {
    return "192.168.178.61:4567";
  }

  /**
   * Main function. Entry point of application.
   */
  public static void main(String[] args) {
    // default mapping of spark-rest
    int port = 4569;
    port(port);

    String ownUri = getIp();
    //    if (args.length == 1) {
    //      String host = args[0];
    //      YellowPages yp = new YellowPages(host);
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

    CommitFacade commitFacade = new CommitFacade(serviceLogic);
    TwoPhaseCommitLogic twoPhaseCommit = new TwoPhaseCommitLogic(serviceLogic, ownUri);
    Facade banksController = new Facade(serviceLogic, twoPhaseCommit);

    // Register replicates
    if (port == 4567) {
      twoPhaseCommit.registerCloneServices("localhost:4568");
      twoPhaseCommit.registerCloneServices("localhost:4569");
    } else if (port == 4568) {
      twoPhaseCommit.registerCloneServices("localhost:4567");
      twoPhaseCommit.registerCloneServices("localhost:4569");
    } else if (port == 4569) {
      twoPhaseCommit.registerCloneServices("localhost:4567");
      twoPhaseCommit.registerCloneServices("localhost:4568");
    }
  }

}
