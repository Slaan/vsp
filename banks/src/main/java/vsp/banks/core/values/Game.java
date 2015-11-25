package vsp.banks.core.values;

import vsp.banks.core.entities.Player;

import java.util.HashSet;
import java.util.Set;

import static vsp.banks.helper.ObjectHelper.*;
import static vsp.banks.helper.StringHelper.*;
/**
 * Created by alex on 11/22/15.
 */
public class Game {

  private String gameid;

  private String uri;

  private Set<Player> players;

  private Components components;

  /**
   * Game is a immutable object.
   */
  public Game(String gameid, String uri, Set<Player> players, Components components) {
    checkNotNull(gameid);
    checkNotEmpty(gameid);
    this.gameid = gameid;
    this.uri = uri;
    this.players = players;
    this.components = components;
  }

  public String getGameid() {
    return gameid;
  }

  public String getUri() {
    return uri;
  }

  public Set<Player> getPlayers() {
    return new HashSet<>(players);
  }

  public Components getComponents() {
    return components;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Game)) {
      return false;
    }
    Game game = (Game) object;
    if (gameid != null ? !gameid.equals(game.gameid) : game.gameid != null) {
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

  @Override
  public int hashCode() {
    int result = gameid != null ? gameid.hashCode() : 0;
    result = 31 * result + (uri != null ? uri.hashCode() : 0);
    result = 31 * result + (players != null ? players.hashCode() : 0);
    result = 31 * result + (components != null ? components.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Game{"
        + "gameid='" + gameid + '\''
        + ", uri='" + uri + '\''
        + ", players=" + players
        + ", components=" + components
        + '}';
  }
}
