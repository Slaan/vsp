package vsp.banks.access.entities;

import static vsp.banks.helper.ObjectHelper.*;
import static vsp.banks.helper.StringHelper.*;

/**
 * Created by alex on 11/20/15.
 */
public class Player {

  private String id;

  private String name;

  private String uri;

  /**
   * This immutable object stores information about the player.
   */
  public Player(String id, String name, String uri) {
    checkNotNull(id);
    checkNotEmpty(id);
    this.id = id;
    this.name = name;
    this.uri = uri;
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
