package vsp.banks;

import org.testng.annotations.Test;
import vsp.banks.core.BanksLogic;
import vsp.banks.core.interfaces.IBankLogic;
import vsp.banks.core.values.Game;
import vsp.banks.core.values.Place;
import vsp.banks.core.entities.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by alex on 11/20/15.
 */
@Test
public class TestBanksLogic {

  IBankLogic bankLogic;


  Place place = new Place("Wacholderallee");
  Player player1 = new Player("player1", "bob", "localhost/player/");
  Player player2 = new Player("player2", "alice", "localhost/player/");
  Player player3 = new Player("player3", "hans", "localhost/player/");
  Player notInGame = new Player("notInGame", "hans", "localhost/player/");

  private IBankLogic setUpLogic() {
    return new BanksLogic();
  }

  @Test
  public void test_bankLogic_setGame() {
    IBankLogic logic = setUpLogic();
    Set<Player> players = new HashSet<>(Arrays.asList(player1, player2, player3));
    Game game = new Game("game1", "localhost/games/game1", players, null);
    logic.setGame(game);
    logic.setGame(game);
  }

  @Test
  public void test_bankLogic_registerPlayer() {
    IBankLogic logic = setUpLogic();
    Set<Player> players = new HashSet<>(Arrays.asList(player1, player2, player3));
    Game game = new Game("game1", "localhost/games/game1", players, null);
    logic.setGame(game);
  }

}
