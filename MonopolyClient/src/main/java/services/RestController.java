package services;

import entities.Game;
import entities.GameList;
import entities.Player;
import entities.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import services.Exception.ClientException;
import services.Exception.ServerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */

public class RestController  {

    private final String hostUrl="http://141.22.79.157:4567";
    private final String gamesUrl="/games";
    private final String playersUrl="/players";
    public static final Logger log = LoggerFactory.getLogger(RestController.class);

    private RestTemplate restTemplate;
    private Player ownPlayer;

    public RestController()  {
      this.restTemplate = new RestTemplate();
      restTemplate.setErrorHandler(new OurRestErrorHandler());
      callRestQuote();
    }

    public void callRestQuote() throws ClientException, ServerException{
      Quote quote = restTemplate
        .getForObject("http://gturnquist-quoters.cfapps.io/api/rand", Quote.class);
      log.info(quote.toString());
    }

    public List<Game> getAllGames() throws ClientException, ServerException {
      return restTemplate.getForObject(hostUrl+gamesUrl,GameList.class);
    }

    public Game createGame() throws ClientException, ServerException {
        Game game = restTemplate.postForObject(hostUrl+gamesUrl,null,Game.class);
        log.info("new Game created! Id: " + game.getGameid());
        return game;
    }

    public void registerPlayerForID(String id) throws ClientException, ServerException {
        UriComponentsBuilder build = UriComponentsBuilder.fromHttpUrl(hostUrl+gamesUrl+
            "/"+id+""+playersUrl+"/"+ownPlayer.getId())
                .queryParam("Playername",ownPlayer.getName())
                .queryParam("Playeruri",ownPlayer.getUri());
        log.info("url we tried to contact: " + build.toUriString());
        restTemplate.put(build.toUriString(),null);

    }

    public Player getOwnPlayer() {
        return ownPlayer;
    }

    public void setOwnPlayer(Player ownPlayer) {
        this.ownPlayer = ownPlayer;
    }
}
