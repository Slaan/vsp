package vsp.banks.values;

/**
 * Created by alex on 11/18/15.
 *
 * <p>Representation of Monopoly money.</p>
 */
public class Money {

  private int amount;

  public Money(int amount) {
    this.amount = amount;
  }

  public Money(Money money) {

  }

  public int getAmount() {
    return amount;
  }

  /**
   * Multiplies given money with current.
   * @param money to multiply with.
   * @return product of this and and given money.
   */
  public Money mul(Money money) {
    return new Money(this.getAmount() * money.getAmount());
  }

  /**
   * Adds given money with current.
   * @param money to add.
   * @return sum of this and given money.
   */
  public Money add(Money money) {
    return new Money(this.getAmount() + money.getAmount());
  }

  /**
   * Subtracts given money from current.
   * @param money to subtract.
   * @return difference of mthis and given money.
   */
  public Money sub(Money money) {
    return new Money(this.getAmount() - money.getAmount());
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Money)) {
      return false;
    }
    Money money = (Money) object;
    return amount == money.amount;
  }

  @Override
  public int hashCode() {
    return amount;
  }
}
