package services;

import entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
public class BoardsManager {

  private Map<String, Board> boardsMap;
  private List<Game> games;

  public BoardsManager() {
    boardsMap = new HashMap<>();
    games = new ArrayList<>();
  }

  /**
   * Gets all Boards hosted on this service.
   *
   * @return List of all Boards hosted on this serivce
   */
  public List<Board> getAllBoards() {
    List<Board> result = new ArrayList<>();
    for (Board b : boardsMap.values()) {
      result.add(b);
    }
    return result;
  }

  /**
   * Returns Board belonging to the Game.
   *
   * @param gameId gameId we want the Board from
   * @return Board beloning to the Game
   */
  public Board getBoardByGameId(String gameId) {
    return boardsMap.get(gameId);
  }

  /**
   * Creats a new Board.
   *
   * @param gameId gameId the board belongs to
   * @param game   Game the Board was created for
   */
  public void createBoardForGame(String gameId, Game game) {
    Board board = new Board();
    board.getFields().add(new Field(new Place("Los")));
    boardsMap.put(gameId, board);
    games.add(game);
  }

  /**
   * Deletes Board on this service.
   *
   * @param gameId gameId of the Board that shall be removed
   */
  public void deleteGameById(String gameId) {
    boardsMap.remove(gameId);
  }

  /**
   * Returns a List of players currently registered on the given game.
   *
   * @param gameId gameId of the game we want players from
   * @return List of players currently registered on this game
   */
  public List<Player> getPlayersByGameId(String gameId) {
    List<Field> fields = boardsMap.get(gameId).getFields();
    List<Player> result = new ArrayList<>();
    for (Field f : fields) {
      result.addAll(f.getPlayers());
    }
    return result;
  }

  /**
   * Registers a new player with our game, will be set to position 0.
   *
   * @param gameId   gameId the player will be registered to.
   * @param playerId playerId of the player being registered.
   * @param player   the actual player that will be put on the board.
   */
  public void registerNewPlayerOnGame(String gameId, String playerId, Player player) {
    Board board = boardsMap.get(gameId);
    board.getPositions().put(playerId, 0);
    player.setPlace(board.getFields().get(0).getPlace());
    player.setPosition(0);
    board.getFields().get(0).getPlayers().add(player);
  }


  /**
   * Removes a player from the board.
   *
   * @param gameId   gameId the player will be removed from.
   * @param playerId playerId of the player that will be removed.
   */
  public void deletePlayerFromBoard(String gameId, String playerId) {
    Board board = boardsMap.get(gameId);
    board.getPositions().remove(playerId);
    for (Field f : board.getFields()) {
      for (Player p : f.getPlayers()) {
        if (p.getId().equals(playerId)) {
          f.getPlayers().remove(p);
          break;
        }
      }
    }
  }

  /**
   * Returns a specific player from the game.
   *
   * @param gameId   gameId of the game the player will be returned
   * @param playerId playerId of the player that will be returned
   * @return Player
   */
  public Player getPlayerFromBoard(String gameId, String playerId) {
    Player result = null;
    Board board = boardsMap.get(gameId);
    int positionOfPlayer = board.getPositions().get(playerId);
    for (Player p : board.getFields().get(positionOfPlayer).getPlayers()) {
      if (p.getId().equals(playerId)) {
        result = p;
      }
    }
    return result;
  }

  /**
   * Moves a player a number of fields.
   *
   * @param gameId   gameId of the game the player will be moved
   * @param playerId id of the player that will be moved
   * @param move     number of steps the player will take
   * @return New status of Board
   */
  public Board movePlayer(String gameId, String playerId, int move) {
    Board board = boardsMap.get(gameId);
    int currentPosition = board.getPositions().get(playerId);
    int nextPosition = ((currentPosition + move) % board.getFields().size());
    board.getPositions().put(playerId, nextPosition);
    Player player = null;
    for (Player p : board.getFields().get(currentPosition).getPlayers()) {
      if (p.getId().equals(playerId)) {
        player = p;
        board.getFields().get(nextPosition).getPlayers().add(p);
        board.getFields().get(currentPosition).getPlayers().remove(p);
        break;
      }
    }
    player.setPlace(board.getFields().get(nextPosition).getPlace());
    player.setPosition(nextPosition);
    return board;
  }

  /**
   * Returns all places from a given Game.
   *
   * @param gameId gameId the places will be returned from.
   * @return All places of this game.
   */
  public List<Place> getPlacesByGameId(String gameId) {
    Board board = boardsMap.get(gameId);
    List<Place> result = new ArrayList<>();
    for (Field f : board.getFields()) {
      result.add(f.getPlace());
    }
    return result;
  }

  /**
   * Gets place from a game
   *
   * @param gameId gameId the place will be looked for
   * @param place  place that will be grabbed
   * @return Place
   */
  public Place getPlaceByGameId(String gameId, String place) {
    //TODO throw Exception if place isn't found in game
    Board board = boardsMap.get(gameId);
    Place result = null;
    for (Field f : board.getFields()) {
      if (f.getPlace().getName().equals(place)) {
        result = f.getPlace();
      }
    }
    return result;
  }

  /**
   * Adds a new Place to the Board belonging to a certain Game.
   *
   * @param gameId    gameId of the Game the place will be added to
   * @param place     not used currently
   * @param placeBody Place that will be added
   */
  public void putPlace(String gameId, String place, Place placeBody) {
    Board board = boardsMap.get(gameId);
    board.getFields().add(new Field(placeBody));
  }

}
