package vsp.banks.core.entities;

import vsp.banks.values.Place;

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

  /**
   * This immutable object stores information about the player.
   */
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

  public void setPlace(Place place) {
    this.place = place;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Player)) {
      return false;
    }
    Player player = (Player) object;
    if (id != null ? !id.equals(player.id) : player.id != null) {
      return false;
    }
    return !(name != null ? !name.equals(player.name) : player.name != null);

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
