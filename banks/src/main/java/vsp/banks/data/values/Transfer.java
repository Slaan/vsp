package vsp.banks.data.values;

import static vsp.banks.helper.StringHelper.*;
import static vsp.banks.helper.ObjectHelper.*;
/**
 * Created by alex on 11/22/15.
 */
public class Transfer {

  enum TransferType {
    playerToPlayer,
    playerToBank,
    bankToPlayer
  }

  private transient TransferType type;

  private String from;

  private String to;

  private int amount;

  private String reason;

  private String event;

  /**
   * Checks invariant of this
   */
  private void checkInvariant() {
    if (type.equals(TransferType.bankToPlayer) && from.isEmpty() && !to.isEmpty()) {
      throw new RuntimeException();
    }
    if (type.equals(TransferType.playerToBank) && !from.isEmpty() && to.isEmpty()) {
      throw new RuntimeException();
    }
  }

  /**
   * Creates a bank to player transfer.
   */
  public static Transfer playerToBank(String from, int amount, String reason, String ev) {
    return new Transfer(TransferType.playerToBank, from, "", amount, reason, ev);
  }

  /**
   * Creates a bank to player transfer.
   */
  public static Transfer bankToPlayer(String to, int amount, String reason, String event) {
    return new Transfer(TransferType.bankToPlayer, "", to, amount, reason, event);
  }

  /**
   * Creates a player to player transfer.
   */
  public Transfer(String from, String to, int amount, String reason, String event) {
    this(TransferType.playerToPlayer, from, to, amount, reason, event);
  }

  /**
   * Private constructor.
   */
  private Transfer(TransferType type, String from, String to, int amount, String reason,
      String event) {
    checkNotNull(from, to, reason);
    checkNotEmpty(reason);
    this.type = type;
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.reason = reason;
    this.event = event;
    checkInvariant();
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public int getAmount() {
    return amount;
  }

  public String getReason() {
    return reason;
  }

  public String getEvent() {
    return event;
  }

  public boolean isPlayerToPlayer() {
    return this.type.equals(TransferType.playerToPlayer);
  }

  public boolean isBankToPlayer() {
    return this.type.equals(TransferType.bankToPlayer);
  }

  public boolean isPlayerToBank() {
    return this.type.equals(TransferType.playerToBank);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Transfer)) {
      return false;
    }
    Transfer transfer = (Transfer) object;
    if (amount != transfer.amount) {
      return false;
    }
    if (from != null ? !from.equals(transfer.from) : transfer.from != null) {
      return false;
    }
    if (to != null ? !to.equals(transfer.to) : transfer.to != null) {
      return false;
    }
    if (reason != null ? !reason.equals(transfer.reason) : transfer.reason != null) {
      return false;
    }
    return !(event != null ? !event.equals(transfer.event) : transfer.event != null);

  }

  @Override
  public int hashCode() {
    int result = from != null ? from.hashCode() : 0;
    result = 31 * result + (to != null ? to.hashCode() : 0);
    result = 31 * result + amount;
    result = 31 * result + (reason != null ? reason.hashCode() : 0);
    result = 31 * result + (event != null ? event.hashCode() : 0);
    return result;
  }
}
