package services.Adapters;

import entities.Game;
import entities.GameList;
import entities.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import services.Exception.ClientException;
import services.Exception.ServerException;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */

public class GamesServiceAdapter {

  private final String hostUrl = "http://141.22.79.157:4567";
  private final String gamesUrl = "/games";
  private final String playersUrl = "/players";
  public static final Logger log = LoggerFactory.getLogger(GamesServiceAdapter.class);

  private RestTemplate restTemplate;
  private Player ownPlayer;

  public GamesServiceAdapter() {
    this.restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new OurRestErrorHandler());
  }

  public List<Game> getAllGames() throws ClientException, ServerException {
    return restTemplate.getForObject(hostUrl + gamesUrl, GameList.class);
  }

  public Game createGame() throws ClientException, ServerException {
    Game game = restTemplate.postForObject(hostUrl + gamesUrl, null, Game.class);
    log.info("new Game created! Id: " + game.getGameid());
    return game;
  }

  public void registerPlayerForID(String id) throws ClientException, ServerException {
    UriComponentsBuilder build = UriComponentsBuilder.fromHttpUrl(hostUrl + gamesUrl +
        "/" + id + "" + playersUrl + "/" + ownPlayer.getId())
        .queryParam("Playername", ownPlayer.getName()).queryParam("Playeruri", ownPlayer.getUri());
    log.info("url we tried to contact: " + build.toUriString());
    restTemplate.put(build.toUriString(), null);
  }

  public Player getOwnPlayer() {
    return ownPlayer;
  }

  public void setOwnPlayer(Player ownPlayer) {
    this.ownPlayer = ownPlayer;
  }
}
