package services;

import entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
@RestController public class RestApiController {

  private BoardsManager boardsManager = new BoardsManager();
  private static final Logger log = LoggerFactory.getLogger(RestApiController.class);

  public RestApiController() {
    log.info("RestApiController started");
  }

  @RequestMapping(method = RequestMethod.GET, value = "/boards")
  public ResponseEntity<List<Board>> getAllBoards() {
    List<Board> boards = boardsManager.getAllBoards();
    return new ResponseEntity<>(boards, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/boards")
  public ResponseEntity createBoardByGameId(@RequestBody Game game, UriComponentsBuilder b) {
    String gameId = game.getGameid();
    String uri = "/boards/" + gameId;
    boardsManager.createBoardForGame(gameId, game);
    log.info("new Board created for GameId: " + gameId);
    UriComponents uriComponents = b.path(uri).buildAndExpand(gameId);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(uriComponents.toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/boards/{id}")
  public ResponseEntity<Board> getBoardByGameId(@PathVariable("id") String gameId) {
    Board board = boardsManager.getBoardByGameId(gameId);
    return new ResponseEntity<Board>(board, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/boards/{id}")
  public ResponseEntity createBoardByGameId(@RequestBody Game game,
      @PathVariable("id") String gameId, UriComponentsBuilder b) {
    String uri = "/boards/" + gameId;
    boardsManager.createBoardForGame(gameId, game);
    log.info("new Board created for GameId: " + gameId);
    UriComponents uriComponents = b.path(uri).buildAndExpand(gameId);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(uriComponents.toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/boards/{id}")
  public void deleteGameById(@PathVariable("id") String gameId) {
    boardsManager.deleteGameById(gameId);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/boards/{id}/players")
  public ResponseEntity<List<Player>> getPlayersByGameId(@PathVariable("id") String gameId) {
    List<Player> players = boardsManager.getPlayersByGameId(gameId);
    return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/boards/{gameid}/players/")
  public ResponseEntity registerNewPlayer(@PathVariable("gameid") String gameId,
      @RequestBody Player player, UriComponentsBuilder b) {
    boardsManager.registerNewPlayerOnGame(gameId, player.getId(), player);
    String uri = "/boards/" + gameId + "/players/" + player.getId();
    UriComponents uriComponents = b.path(uri).build();
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(uriComponents.toUri());
    log.info("New Player joined Game: " + gameId + ", his Id is: " + player.getId());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/boards/{gameid}/players/{playerid}")
  public ResponseEntity registerNewPlayer(@PathVariable("gameid") String gameId,
      @PathVariable("playerid") String playerId, @RequestBody Player player,
      UriComponentsBuilder b) {
    boardsManager.registerNewPlayerOnGame(gameId, playerId, player);
    log.info("New Player joined Game: " + gameId + ", his Id is: " + playerId);
    String uri = "/boards/" + gameId + "/players/" + player.getId();
    UriComponents uriComponents = b.path(uri).build();
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(uriComponents.toUri());
    log.info("New Player joined Game: " + gameId + ", his Id is: " + player.getId());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/boards/{gameid}/players/{playerid}")
  public void deletePlayerFromBoard(@PathVariable("gameid") String gameId,
      @PathVariable("playerid") String playerId) {
    boardsManager.deletePlayerFromBoard(gameId, playerId);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/boards/{gameid}/players/{playerid}")
  public ResponseEntity<Player> getPlayerFromBoard(@PathVariable("gameid") String gameId,
      @PathVariable("playerid") String playerId) {
    Player result = boardsManager.getPlayerFromBoard(gameId, playerId);
    return new ResponseEntity<Player>(result, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/boards/{gameid}/players/{playerid}/move")
  public void movePlayer(@PathVariable("gameid") String gameId,
      @PathVariable("playerid") String playerId, @RequestBody Roll roll) {
    boardsManager.movePlayer(gameId, playerId, roll.getRoll());
  }

  @RequestMapping(method = RequestMethod.POST, value = "/boards/{gameid}/players/{playerid}/roll")
  public ResponseEntity<Board> movePlayerByThrow(@PathVariable("gameid") String gameId,
      @PathVariable("playerid") String playerId, @RequestBody Throw wurf) {
    Board result = boardsManager.movePlayer(gameId, playerId, (wurf.getRoll1() + wurf.getRoll2()));
    return new ResponseEntity<Board>(result, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/boards/{gameid}/places")
  public ResponseEntity<List<Place>> getPlacesByGameId(@PathVariable("gameid") String gameId) {
    List<Place> result = boardsManager.getPlacesByGameId(gameId);
    return new ResponseEntity<List<Place>>(result, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/boards/{gameid}/places/{place}")
  public ResponseEntity<Place> getPlaceByGameId(@PathVariable("gameid") String gameId,
      @PathVariable("place") int place) {
    Place result = boardsManager.getPlaceByGameId(gameId, place);
    return new ResponseEntity<Place>(result, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/boards/{gameid}/places")
  public ResponseEntity putPlace(@PathVariable("gameid") String gameId,
      @RequestBody Place placeBody, UriComponentsBuilder b) {
    String uri = boardsManager.putPlace(gameId, 0, placeBody);
    UriComponents uriComponents = b.path(uri).build();
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(uriComponents.toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/boards/{gameid}/places/{place}")
  public ResponseEntity putPlace(@PathVariable("gameid") String gameId,
      @PathVariable("place") int place, @RequestBody Place placeBody, UriComponentsBuilder b) {

    String uri = boardsManager.putPlace(gameId, place, placeBody);
    UriComponents uriComponents = b.path(uri).buildAndExpand(place);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(uriComponents.toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }
}
