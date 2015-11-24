package vsp.banks.values;

import static vsp.banks.helper.ObjectHelper.*;
import static vsp.banks.helper.StringHelper.*;
/**
 * Created by alex on 11/24/15.
 */
public class Components {

  private String game;

  private String dice;

  private String board;

  private String bank;

  private String broker;

  private String decks;

  private String events;

  public Components(String game, String dice, String board, String bank, String broker,
      String decks, String events) {
    checkNotNull(game, dice, board, bank, broker, decks, events);
    checkNotEmpty(game, dice, board, bank, broker, decks, events);
    this.game = game;
    this.dice = dice;
    this.board = board;
    this.bank = bank;
    this.broker = broker;
    this.decks = decks;
    this.events = events;
  }

  public String getGame() {
    return game;
  }

  public String getDice() {
    return dice;
  }

  public String getBoard() {
    return board;
  }

  public String getBank() {
    return bank;
  }

  public String getBroker() {
    return broker;
  }

  public String getDecks() {
    return decks;
  }

  public String getEvents() {
    return events;
  }

  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Components)) {
      return false;
    }
    Components that = (Components) object;
    if (game != null ? !game.equals(that.game) : that.game != null) {
      return false;
    }
    if (dice != null ? !dice.equals(that.dice) : that.dice != null) {
      return false;
    }
    if (board != null ? !board.equals(that.board) : that.board != null) {
      return false;
    }
    if (bank != null ? !bank.equals(that.bank) : that.bank != null) {
      return false;
    }
    if (broker != null ? !broker.equals(that.broker) : that.broker != null) {
      return false;
    }
    if (decks != null ? !decks.equals(that.decks) : that.decks != null) {
      return false;
    }
    return !(events != null ? !events.equals(that.events) : that.events != null);

  }

  public int hashCode() {
    int result = game != null ? game.hashCode() : 0;
    result = 31 * result + (dice != null ? dice.hashCode() : 0);
    result = 31 * result + (board != null ? board.hashCode() : 0);
    result = 31 * result + (bank != null ? bank.hashCode() : 0);
    result = 31 * result + (broker != null ? broker.hashCode() : 0);
    result = 31 * result + (decks != null ? decks.hashCode() : 0);
    result = 31 * result + (events != null ? events.hashCode() : 0);
    return result;
  }
}
