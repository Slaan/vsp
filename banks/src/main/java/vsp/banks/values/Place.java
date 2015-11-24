package vsp.banks.values;

import static vsp.banks.helper.ObjectHelper.*;
import static vsp.banks.helper.StringHelper.*;

/**
 * Created by alex on 11/20/15.
 */
public class Place {

  public String name;

  public Place(String name) {
    checkNotNull(name);
    checkNotEmpty(name);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Place)) {
      return false;
    }
    Place place = (Place) object;
    return !(name != null ? !name.equals(place.name) : place.name != null);
  }

  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}
