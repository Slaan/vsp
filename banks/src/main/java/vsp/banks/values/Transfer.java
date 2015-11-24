package vsp.banks.values;

import static vsp.banks.helper.StringHelper.*;
import static vsp.banks.helper.ObjectHelper.*;
/**
 * Created by alex on 11/22/15.
 */
public class Transfer {

  private static final String bankName = "BANK";

  private String from;

  private String to;

  private int amount;

  private String reason;

  private String event;

  public static Transfer initTransferFromPlayer(String from, int amount, String reason, String ev) {
    return new Transfer(from, bankName, amount, reason, ev);
  }

  public static Transfer initTransferToPlayer(String to, int amount, String reason, String event) {
    return new Transfer(bankName, to, amount, reason, event);
  }

  /**
   * @param from     of the money.
   * @param to       of the money.
   * @param amount   of money to transfer.
   * @param reason   of transfer. Reason can't be null nor empty.
   */
  public Transfer(String from, String to, int amount, String reason, String event) {
    checkNotNull(from, to, reason);
    checkNotEmpty(from, to, reason);
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.reason = reason;
    this.event = event;
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

  public int hashCode() {
    int result = from != null ? from.hashCode() : 0;
    result = 31 * result + (to != null ? to.hashCode() : 0);
    result = 31 * result + amount;
    result = 31 * result + (reason != null ? reason.hashCode() : 0);
    result = 31 * result + (event != null ? event.hashCode() : 0);
    return result;
  }
}
