package vsp.banks.data.values;

import static vsp.banks.helper.ObjectHelper.checkNotNull;
/**
 * Created by alex on 11/24/15.
 */
public class Event {

  private String type;

  private String name;

  private String reason;

  private String resource;

  private String player;

  /**
   * TODO: add java-doc :).
   */
  public Event(String type, String name, String reason, String resource, String player) {
    checkNotNull(type, name, reason, resource);
    this.type = type;
    this.name = name;
    this.reason = reason;
    this.resource = resource;
    this.player = player;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getReason() {
    return reason;
  }

  public String getResource() {
    return resource;
  }

  public String getPlayer() {
    return player;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }
}