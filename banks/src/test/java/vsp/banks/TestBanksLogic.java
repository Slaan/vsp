package vsp.banks;

import org.testng.annotations.Test;
import vsp.banks.core.BanksLogic;
import vsp.banks.core.entities.Account;
import vsp.banks.core.interfaces.IBankLogic;
import vsp.banks.values.Money;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by alex on 11/20/15.
 */
@Test
public class TestBanksLogic {

  @Test
  public void testRegisterUser() {
    String gameId   = "bestGame";
    String playerId = "bestPlayer";
    IBankLogic bankService = new BanksLogic();
    assertTrue(bankService.registerPlayerForGame(gameId, playerId));
    assertFalse(bankService.registerPlayerForGame(gameId, playerId));
    assertFalse(bankService.registerPlayerForGame(gameId, playerId));
  }

  @Test
  public void testGetAccount() {
    String gameId   = "bestGame";
    String playerId = "bestPlayer";
    IBankLogic bankService = new BanksLogic();
    String exceptionMsg = "Expected true in register Player";
    assertTrue(bankService.registerPlayerForGame(gameId, playerId), exceptionMsg);
    Account expectedAccount = new Account(playerId, BanksLogic.startMoney);
    assertEquals(bankService.getAccount(gameId, playerId), expectedAccount);
  }

  @Test
  public void testTransferFromPlayerToPlayer() {
    String gameId   = "bestGame";
    String playerId1 = "bestPlayer1";
    String playerId2 = "bestPlayer2";
    IBankLogic bankService = new BanksLogic();
    String exceptionMsg = "Expected true in register Player";
    assertTrue(bankService.registerPlayerForGame(gameId, playerId1), exceptionMsg);
    assertTrue(bankService.registerPlayerForGame(gameId, playerId2), exceptionMsg);
    Money transferMoney = new Money(500);
    assertTrue(bankService.transferFromPlayerToPlayer(gameId, playerId1, playerId2, transferMoney,
        ""));
    Account account1 = new Account(playerId1, BanksLogic.startMoney.sub(transferMoney));
    Account account2 = new Account(playerId2, BanksLogic.startMoney.add(transferMoney));
    assertEquals(bankService.getAccount(gameId, playerId1), account1);
    assertEquals(bankService.getAccount(gameId, playerId2), account2);
  }

  @Test
  public void testGiveMoneyToPlayer() {
    String gameId   = "bestGame";
    String playerId = "bestPlayer";
    IBankLogic bankService = new BanksLogic();
    String exceptionMsg = "Expected true in register Player";
    assertTrue(bankService.registerPlayerForGame(gameId, playerId), exceptionMsg);
    Money m = BanksLogic.startMoney.add(new Money(400));
    bankService.giveMoneyToPlayer(gameId, playerId, new Money(400), "");
    Account expectedAccount = new Account(playerId, m);
    assertEquals(bankService.getAccount(gameId, playerId), expectedAccount);
  }

  @Test
  public void testWithdrawMoneyFromPlayer() {
    String gameId   = "bestGame";
    String playerId = "bestPlayer";
    IBankLogic bankService = new BanksLogic();
    String exceptionMsg = "Expected true in register Player";
    assertTrue(bankService.registerPlayerForGame(gameId, playerId), exceptionMsg);
    Money m = BanksLogic.startMoney.sub(new Money(400));
    bankService.withdrawMoneyFromPlayer(gameId, playerId, new Money(400), "");
    Account expectedAccount = new Account(playerId, m);
    assertEquals(bankService.getAccount(gameId, playerId), expectedAccount);
  }

  @Test
  public void testWithdrawMoneyFromPlayer_NotEnoughMoney() {
    String gameId   = "bestGame";
    String playerId = "bestPlayer";
    IBankLogic bankService = new BanksLogic();
    String exceptionMsg = "Expected true in register Player";
    assertTrue(bankService.registerPlayerForGame(gameId, playerId), exceptionMsg);
    Money m = BanksLogic.startMoney.sub(new Money(1500));
    assertFalse(bankService.withdrawMoneyFromPlayer(gameId, playerId, new Money(1500), ""));
    Account expectedAccount = new Account(playerId, m);
    assertEquals(bankService.getAccount(gameId, playerId), expectedAccount);
  }
}
