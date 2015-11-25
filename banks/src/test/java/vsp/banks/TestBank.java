package vsp.banks;

import org.testng.annotations.Test;
import vsp.banks.core.entities.Bank;
import vsp.banks.values.Game;
import vsp.banks.values.Place;
import vsp.banks.values.Player;
import vsp.banks.values.Transfer;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by alex on 11/24/15.
 */
@Test
public class TestBank {


  private Bank setUp() {
    Place place = new Place("Wacholderallee");
    Player player1 = new Player("player1", "bob", "localhost/player/", place, 3, true);
    Player player2 = new Player("player2", "alice", "localhost/player/", place, 3, true);
    Player player3 = new Player("player3", "hans", "localhost/player/", place, 3, true);
    Game game = new Game("TESTGAME", "localhost/GAMES/TESTGAME",
        new HashSet<>(Arrays.asList(player1, player2, player3)), null);
    return new Bank(game);
  }



  @Test
  public void test_bank_Transfer_FromPlayerToBank() {
    Bank bank = setUp();
    Transfer transfer = Transfer.initTransferFromPlayer("player1", 50, "haus", "nono");
    if (bank.applyTransfer(transfer)) {

    }
  }
}
