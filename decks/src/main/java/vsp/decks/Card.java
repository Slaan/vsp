package vsp.decks;

/**
 * Created by alex on 04.11.15.
 */
public abstract class Card {

  private String name;

  private String text;

  /**
   * Abstract constructor of card.
   * @param name of card.
   * @param text of card.
   */
  public Card(String name, String text) {
    this.name = name;
    this.text = text;
  }

  /**
   * Get name of card.
   * @return name of card as String.
   */
  public String getName() {
    return name;
  }

  /**
   * Get text of card.
   * @return text of card as String.
   */
  public String getText() {
    return text;
  }
}
