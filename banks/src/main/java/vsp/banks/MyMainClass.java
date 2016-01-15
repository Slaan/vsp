package vsp.banks;

import vsp.banks.business.BanksLogic;
import vsp.banks.business.discovery.YellowPages;
import vsp.banks.access.BanksRestApi;

public class MyMainClass {

  /**
   * Main function. Entry point of application.
   */
  public static void main(String[] args) {
    if (args.length == 1) {
      String host = args[0];
      YellowPages yp = new YellowPages(host);
      try {
        String name = "Banks";
        String description = "desc";
        String service = "/banks";
        String uri = "http://localhost:4567";
        yp.register(name, description, service, uri);
      } catch (Exception e) {
        System.err.println("Warning: 'Couldn't connect to yellow page server.");
      }
    }
    // Dependency Injections
    BanksLogic serviceLogic = new BanksLogic();
    BanksRestApi banksController = new BanksRestApi(serviceLogic);
  }

}
