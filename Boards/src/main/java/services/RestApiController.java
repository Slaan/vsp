package services;

import entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
@RestController
public class RestApiController {

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

    @RequestMapping(method = RequestMethod.GET, value = "/boards/{id}")
    public ResponseEntity<Board> getBoardByGameId(@PathVariable("id") String gameId) {
        Board board = boardsManager.getBoardByGameId(gameId);
        return new ResponseEntity<Board>(board,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/boards/{id}")
    public void createBoardByGameId(@RequestBody Game game, @PathVariable("id") String gameId) {
        boardsManager.createBoardForGame(gameId, game);
        log.info("new Board created for GameId: " + gameId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/boards/{id}")
    public void deleteGameById(@PathVariable("id") String gameId) {
        boardsManager.deleteGameById(gameId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/boards/{id}/players")
    public ResponseEntity<List<Player>> getPlayersByGameId(@PathVariable ("id") String gameId) {
        List<Player> players = boardsManager.getPlayersByGameId(gameId);
        return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/boards/{gameid}/players/{playerid}")
    public void registerNewPlayer(@PathVariable("gameid") String gameId, @PathVariable("playerid") String playerId,
                                  @RequestBody Player player) {
        boardsManager.registerNewPlayerOnGame(gameId, playerId, player);
        log.info("New Player joined Game: " + gameId + ", his Id is: " + playerId);
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
        return new ResponseEntity<Player>(result,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/boards/{gameid}/players/{playerid}/move")
    public void movePlayer(@PathVariable ("gameid") String gameId, @PathVariable("playerid") String playerId,
                           @RequestBody Roll roll){
        boardsManager.movePlayer(gameId, playerId, roll.getRoll());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/boards/{gameid}/players/{playerid}/roll")
    public ResponseEntity<Board> movePlayerByThrow(@PathVariable("gameid") String gameId,
                                                   @PathVariable("playerid") String playerId,
                                                   @RequestBody Throw wurf) {
        Board result = boardsManager.movePlayer(gameId, playerId, (wurf.getRoll1()+wurf.getRoll2()));
        return new ResponseEntity<Board>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/boards/{gameid}/places")
    public ResponseEntity<List<Place>> getPlacesByGameId(@PathVariable("gameid") String gameId) {
        List<Place> result = boardsManager.getPlacesByGameId(gameId);
        return new ResponseEntity<List<Place>>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/boards/{gameid}/places/{place}")
    public ResponseEntity<Place> getPlaceByGameId(@PathVariable("gameid") String gameId,
                                                  @PathVariable("place") String place) {
        Place result = boardsManager.getPlaceByGameId(gameId, place);
        return new ResponseEntity<Place>(result,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/boards/{gameid}/places/{place}")
    public void getPlaceByGameId(@PathVariable("gameid") String gameId, @PathVariable("place") String place,
                                 @RequestBody Place placeBody) {
        boardsManager.putPlace(gameId, place, placeBody);
    }
}
