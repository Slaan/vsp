package vsp.banks;

import org.testng.annotations.Test;
import vsp.banks.values.Money;

import static org.testng.Assert.assertEquals;

/**
 * Created by alex on 11/18/15.
 */
@Test
public class TestMoney {

  @Test
  public void testMoney_add() {
    Money money1 = new Money(10);
    Money money2 = new Money(15);
    assertEquals(money1.add(money2), new Money(25));
  }

  @Test
  public void testMoney_sub() {
    Money money1 = new Money(10);
    Money money2 = new Money(15);
    assertEquals(money1.sub(money2), new Money(-5));
  }

  @Test
  public void testMoney_multiply() {
    Money money1 = new Money(10);
    Money money2 = new Money(15);
    assertEquals(money1.mul(money2), new Money(150));
  }
}
