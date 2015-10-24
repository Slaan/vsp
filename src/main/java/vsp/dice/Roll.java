package vsp.dice;

import java.io.Serializable;

/**
 * Created by alex on 10/24/15.
 */
public class Roll implements Serializable {

  static final long serialVersionUID = 1337L;

  private final int number;

  public Roll(int number) {
    this.number = number;
  }

  /**
   * Get diced number.
   *
   * @return diced number as integer.
   */
  public int getNumber() {
    return this.number;
  }
}
