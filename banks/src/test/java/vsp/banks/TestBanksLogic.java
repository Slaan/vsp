package vsp.banks;

import org.testng.annotations.Test;
import vsp.banks.business.logic.bank.BanksLogic;
import vsp.banks.business.logic.bank.exceptions.BankNotFoundException;
import vsp.banks.business.logic.bank.exceptions.NotFoundException;
import vsp.banks.business.logic.bank.interfaces.IBanksLogic;
import vsp.banks.data.entities.Account;
import vsp.banks.data.entities.Player;
import vsp.banks.data.values.Game;
import vsp.banks.data.values.Place;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Created by alex on 11/20/15.
 */
@Test
public class TestBanksLogic {

  IBanksLogic bankLogic;

  Place place = new Place("Wacholderallee");
  Player player1 = new Player("player1", "bob", "localhost/player/");
  Player player2 = new Player("player2", "alice", "localhost/player/");
  Player player3 = new Player("player3", "hans", "localhost/player/");
  Player notInGame = new Player("notInGame", "hans", "localhost/player/");

  private IBanksLogic setUpLogic() {
    return new BanksLogic();
  }

  @Test
  public void test_bankLogic_setGame() {
    IBanksLogic logic = setUpLogic();
    Set<Player> players = new HashSet<>(Arrays.asList(player1, player2, player3));
    Game game = new Game("game1", "localhost/games/game1", players, null);
    logic.setGame(game);
    logic.setGame(game);
  }

  @Test
  public void test_bankLogic_registerPlayer() {
    IBanksLogic logic = setUpLogic();
    Set<Player> players = new HashSet<>(Arrays.asList(player1, player2, player3));
    Game game = new Game("game1", "localhost/games/game1", players, null);
    logic.setGame(game);
    Account playerAcc1 = new Account(player1, 5000);
    Account playerAcc2 = new Account(player2, 4000);
    try {
      if (!logic.registerPlayerForGame(game.getGameid(), playerAcc1)) {
        fail("Couldn't register account1.");
      }
      if (!logic.registerPlayerForGame(game.getGameid(), playerAcc2)) {
        fail("Couldn't register account2.");
      }
      if (logic.registerPlayerForGame(game.getGameid(), playerAcc2)) {
        fail("Could register account2 a second time.");
      }
    } catch (BankNotFoundException e) {
      fail("Did not found existing bank.");
    }
  }

  @Test
  public void test_bankLogic_lock_and_unlock() {
    IBanksLogic logic = setUpLogic();
    Set<Player> players = new HashSet<>(Arrays.asList(player1, player2, player3));
    Game game = new Game("game1", "localhost/games/game1", players, null);
    logic.setGame(game);
    game = new Game("game2", "localhost/games/game2", players, null);
    logic.setGame(game);
    Account playerAcc1 = new Account(player1, 5000);
    Account playerAcc2 = new Account(player2, 4000);
    try {
      logic.registerPlayerForGame(game.getGameid(), playerAcc1);
      logic.registerPlayerForGame(game.getGameid(), playerAcc2);
      assertTrue(!logic.isLocked("game1"), "A game should be unlocked at creation.");
      if (!logic.lock("game1")) {
        fail("Bank isn't locked after lock().");
      }
      if (logic.lock("game1")) {
        fail("Bank is already locked.");
      }
      assertTrue(logic.isLocked("game1"), "Bank isn't locked after lock().");
      if (!logic.unlock("game1")) {
        fail("Bank isn't unlocked after unlock().");
      }
      if (logic.unlock("game1")) {
        fail("Bank is already unlocked.");
      }
      assertTrue(!logic.isLocked("game1"), "Bank isn't unlocked after unlock().");
    } catch (NotFoundException e) {
      fail("Did not found existing bank.");
    }
  }

}
