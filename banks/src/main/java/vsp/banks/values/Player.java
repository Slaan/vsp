package vsp.banks.values;

import static vsp.banks.helper.ObjectHelper.*;
import static vsp.banks.helper.StringHelper.*;

/**
 * Created by alex on 11/20/15.
 */
public class Player {

  private String id;

  private String name;

  private String uri;

  private Place place;

  private int position;

  private boolean ready;

  public Player(String id, String name, String uri, Place place, int position, boolean ready) {
    checkNotNull(id, place);
    checkNotEmpty(id);
    this.id = id;
    this.name = name;
    this.uri = uri;
    this.place = place;
    this.position = position;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUri() {
    return uri;
  }

  public Place getPlace() {
    return place;
  }

  public int getPosition() {
    return position;
  }

  public boolean isReady() {
    return ready;
  }

  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Player)) {
      return false;
    }
    Player player = (Player) object;
    if (position != player.position) {
      return false;
    }
    if (ready != player.ready) {
      return false;
    }
    if (id != null ? !id.equals(player.id) : player.id != null) {
      return false;
    }
    if (name != null ? !name.equals(player.name) : player.name != null) {
      return false;
    }
    if (uri != null ? !uri.equals(player.uri) : player.uri != null) {
      return false;
    }
    return !(place != null ? !place.equals(player.place) : player.place != null);
  }

  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (uri != null ? uri.hashCode() : 0);
    result = 31 * result + (place != null ? place.hashCode() : 0);
    result = 31 * result + position;
    result = 31 * result + (ready ? 1 : 0);
    return result;
  }
}
