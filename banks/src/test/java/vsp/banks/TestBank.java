package vsp.banks;

import org.testng.annotations.Test;
import vsp.banks.core.entities.Account;
import vsp.banks.core.entities.Bank;
import vsp.banks.core.exceptions.PlayerNotFoundException;
import vsp.banks.values.Game;
import vsp.banks.values.Place;
import vsp.banks.values.Player;
import vsp.banks.values.Transfer;

import java.util.Arrays;
import java.util.HashSet;

import static junit.framework.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by alex on 11/24/15.
 */
@Test
public class TestBank {

  Place place = new Place("Wacholderallee");
  Player player1 = new Player("player1", "bob", "localhost/player/", place, 3, true);
  Player player2 = new Player("player2", "alice", "localhost/player/", place, 3, true);
  Player player3 = new Player("player3", "hans", "localhost/player/", place, 3, true);
  Player notInGame = new Player("notInGame", "hans", "localhost/player/", place, 3, true);

  private Bank setUp() {
    Game game = new Game("TESTGAME", "localhost/GAMES/TESTGAME",
        new HashSet<>(Arrays.asList(player1, player2, player3)), null);
    return new Bank(game);
  }


  @Test
  public void test_bank_register() {
    Bank bank = setUp();
    Account account = new Account(player1, 2000);
    assertTrue(bank.registerAccount(account));
    Account unsuccessful = new Account(player1, 3000);
    assertFalse(bank.registerAccount(unsuccessful));
    Account successful = new Account(player2, 2000);
    assertTrue(bank.registerAccount(successful));
    Account unsuccessful2 = new Account(notInGame, 2000);
    assertFalse(bank.registerAccount(unsuccessful2));
  }

  @Test
  public void test_bank_Transfer_FromPlayerToBank_enoughMoney() throws PlayerNotFoundException {
    Bank bank = setUp();
    Account account = new Account(player1, 2000);
    assertTrue(bank.registerAccount(account));
    Transfer transfer = Transfer.initTransferFromPlayer("player1", 2000, "haus", "EVENT3");
    assertTrue(bank.applyTransfer(transfer));
    assertEquals(account.getSaldo(), 0);
  }

  @Test
  public void test_bank_Transfer_FromPlayerToBank_notEnoughMoney() throws PlayerNotFoundException {
    Bank bank = setUp();
    Account account = new Account(player1, 2000);
    assertTrue(bank.registerAccount(account));
    Transfer transfer = Transfer.initTransferFromPlayer("player1", 2001, "haus", "EVENT3");
    assertFalse(bank.applyTransfer(transfer));
    assertEquals(account.getSaldo(), 2000);
  }

  @Test
  public void test_bank_Transfer_ToPlayerFromBank() throws PlayerNotFoundException {
    Bank bank = setUp();
    Account account = new Account(player1, 2000);
    assertTrue(bank.registerAccount(account));
    Transfer transfer = Transfer.initTransferToPlayer("player1", 2001, "haus", "EVENT3");
    assertTrue(bank.applyTransfer(transfer));
    assertEquals(account.getSaldo(), 4001);
  }

  @Test(expectedExceptions = PlayerNotFoundException.class)
  public void test_bank_Transfer_PlayerNotFound() throws PlayerNotFoundException {
    Bank bank = setUp();
    Transfer transfer = Transfer.initTransferToPlayer("player1", 2001, "haus", "EVENT3");
    bank.applyTransfer(transfer);
  }

  @Test
  public void test_bank_getAccountByPlayerId() throws PlayerNotFoundException {
    Bank bank = setUp();
    Account account = new Account(player1, 2000);
    assertTrue(bank.registerAccount(account));
    Account received = bank.getAccountByPlayerId("player1");
    assertEquals(received, account);
  }

  @Test
  public void test_bank_Transfer_FromPlayerToPlayer_enoughMoney() throws PlayerNotFoundException {
    Bank bank = setUp();
    assertTrue(bank.registerAccount(new Account(player1, 2000)));
    assertTrue(bank.registerAccount(new Account(player2, 2000)));
    Transfer transfer = new Transfer("player1", "player2", 2000, "haus", "EVENT3");
    assertTrue(bank.applyTransfer(transfer));
    Account player1 = bank.getAccountByPlayerId("player1");
    assertEquals(player1.getSaldo(), 0);
    Account player2 = bank.getAccountByPlayerId("player2");
    assertEquals(player2.getSaldo(), 4000);
  }

}
