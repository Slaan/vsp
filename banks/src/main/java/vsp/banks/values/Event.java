package vsp.banks.values;

import static vsp.banks.helper.ObjectHelper.*;

/**
 * Created by alex on 11/20/15.
 */
public class Event {

  private String type;

  private String name;

  private String reason;

  private String resource;

  private Player player;

  public Event(String type, String name, String reason, String resource, Player player) {
    checkNotNull(type, name, reason);
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

  public Player getPlayer() {
    return player;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Event)) {
      return false;
    }
    Event event = (Event) o;
    if (type != null ? !type.equals(event.type) : event.type != null) {
      return false;
    }
    if (name != null ? !name.equals(event.name) : event.name != null) {
      return false;
    }
    if (reason != null ? !reason.equals(event.reason) : event.reason != null) {
      return false;
    }
    if (resource != null ? !resource.equals(event.resource) : event.resource != null) {
      return false;
    }
    return !(player != null ? !player.equals(event.player) : event.player != null);
  }

  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (reason != null ? reason.hashCode() : 0);
    result = 31 * result + (resource != null ? resource.hashCode() : 0);
    result = 31 * result + (player != null ? player.hashCode() : 0);
    return result;
  }
}
