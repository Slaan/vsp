package services;

import entities.Game;
import entities.Player;
import entities.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */

public class RestController  {

    private final String hostUrl="http://localhost:4567";
    private final String gamesUrl="/games";
    private final String playersUrl="/players";
    public static final Logger log = LoggerFactory.getLogger(RestController.class);

    private Player ownPlayer;

    public RestController()  {}

    public void callRestQuote() {
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        log.info(quote.toString());
    }

    public void getAllGames() {

    }

    public int createGame() {
        RestTemplate restTemplate = new RestTemplate();
        //Game game = restTemplate.postForObject(hostUrl+gamesUrl,null,Game.class);
        //log.info("new Game created! Id: " + game.getGameid());
        //return game.getGameid();
        return 0;
    }

    public void registerPlayerForID(int id) {
        UriComponentsBuilder build = UriComponentsBuilder.fromHttpUrl(hostUrl+gamesUrl+"/"+id+""+playersUrl)
                .queryParam("Playername",ownPlayer.getName())
                .queryParam("Playeruri",ownPlayer.getUri());
        log.info("url we tried to contact: " + build.toUriString());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(build.toUriString(),null);
    }

    public Player getOwnPlayer() {
        return ownPlayer;
    }

    public void setOwnPlayer(Player ownPlayer) {
        this.ownPlayer = ownPlayer;
    }
}
