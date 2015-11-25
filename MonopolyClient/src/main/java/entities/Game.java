package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
@JsonIgnoreProperties
public class Game {

    private String gameid;
    private List<Player> players;
    private Components components;

    public Game() {}

    public void setPlayer(Player player) {
        players.add(player);
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Components getComponents() {
        return components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameid=" + gameid +
                ", players=" + players +
                ", components=" + components +
                '}';
    }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Game game = (Game) o;

    if (gameid != null ? !gameid.equals(game.gameid) : game.gameid != null)
      return false;
    if (players != null ? !players.equals(game.players) : game.players != null)
      return false;
    return !(components != null ? !components.equals(game.components) : game.components != null);

  }

  @Override public int hashCode() {
    int result = gameid != null ? gameid.hashCode() : 0;
    result = 31 * result + (players != null ? players.hashCode() : 0);
    result = 31 * result + (components != null ? components.hashCode() : 0);
    return result;
  }
}
