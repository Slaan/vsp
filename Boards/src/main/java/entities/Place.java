package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
@JsonIgnoreProperties
public class Place {

  private String name;
  private String broker;
  private String uri;

  public Place() {}

  public Place(String name, String broker, String uri) {
    this.name = name;
    this.broker = broker;
    this.uri = uri;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBroker() {
    return broker;
  }

  public void setBroker(String broker) {
    this.broker = broker;
  }

  @Override public String toString() {
    return "Place{" +
        "name='" + name + '\'' +
        ", broker='" + broker + '\'' +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Place))
      return false;

    Place place = (Place) o;

    if (name != null ? !name.equals(place.name) : place.name != null)
      return false;
    return !(broker != null ? !broker.equals(place.broker) : place.broker != null);

  }

  @Override public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (broker != null ? broker.hashCode() : 0);
    return result;
  }
}
