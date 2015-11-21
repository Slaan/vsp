package vsp.banks.values;

import static vsp.banks.helper.ObjectHelper.*;
/**
 * Created by alex on 11/20/15.
 */
public class Place {

  public String name;

  public Place(String name) {
    checkNotNull(name);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Place)) {
      return false;
    }

    Place place = (Place) o;

    return !(name != null ? !name.equals(place.name) : place.name != null);

  }

  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}
