package vsp.banks.values;

import java.util.List;

import static vsp.banks.helper.ObjectHelper.*;
import static vsp.banks.helper.StringHelper.*;
/**
 * Created by alex on 11/22/15.
 */
public class Game {

  private String gameId;

  private String uri;

  private List<Player> players;

  private Components components;

  public Game(String gameId, String uri, List<Player> players, Components components) {
    checkNotNull(gameId);
    checkNotEmpty(gameId);
    this.gameId = gameId;
    this.uri = uri;
    this.players = players;
    this.components = components;
  }

  public String getGameId() {
    return gameId;
  }

  public String getUri() {
    return uri;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public Components getComponents() {
    return components;
  }

  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Game)) {
      return false;
    }
    Game game = (Game) object;
    if (gameId != null ? !gameId.equals(game.gameId) : game.gameId != null) {
      return false;
    }
    if (uri != null ? !uri.equals(game.uri) : game.uri != null) {
      return false;
    }
    if (players != null ? !players.equals(game.players) : game.players != null) {
      return false;
    }
    return !(components != null ? !components.equals(game.components) : game.components != null);

  }

  public int hashCode() {
    int result = gameId != null ? gameId.hashCode() : 0;
    result = 31 * result + (uri != null ? uri.hashCode() : 0);
    result = 31 * result + (players != null ? players.hashCode() : 0);
    result = 31 * result + (components != null ? components.hashCode() : 0);
    return result;
  }
}
