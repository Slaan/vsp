package services;

import services.Adapters.GamesServiceAdapter;

/**
 * Created by slaan on 25.11.15.
 */
public class MainController {

  private GamesServiceAdapter gamesServiceAdapter;

  public MainController() {
    gamesServiceAdapter = new GamesServiceAdapter();
  }


}
