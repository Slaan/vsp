package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
@JsonIgnoreProperties public class Subscription {

  private String gameid;
  private String uri;
  private String callbackuri;
  private Event event;
  private int id;

  public Subscription() {
  }

  public void setGameid(String gameid) {
    this.gameid = gameid;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public void setCallbackuri(String callbackuri) {
    this.callbackuri = callbackuri;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public String getGameid() {
    return gameid;
  }

  public String getUri() {
    return uri;
  }

  public String getCallbackuri() {
    return callbackuri;
  }

  public Event getEvent() {
    return event;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Subscription that = (Subscription) o;

    if (id != that.id)
      return false;
    if (gameid != null ? !gameid.equals(that.gameid) : that.gameid != null)
      return false;
    if (uri != null ? !uri.equals(that.uri) : that.uri != null)
      return false;
    if (callbackuri != null ? !callbackuri.equals(that.callbackuri) : that.callbackuri != null)
      return false;
    return !(event != null ? !event.equals(that.event) : that.event != null);

  }

  @Override public int hashCode() {
    int result = gameid != null ? gameid.hashCode() : 0;
    result = 31 * result + (uri != null ? uri.hashCode() : 0);
    result = 31 * result + (callbackuri != null ? callbackuri.hashCode() : 0);
    result = 31 * result + (event != null ? event.hashCode() : 0);
    result = 31 * result + id;
    return result;
  }

  @Override public String toString() {
    return "Subscription{" +
        "gameid='" + gameid + '\'' +
        ", uri='" + uri + '\'' +
        ", callbackuri='" + callbackuri + '\'' +
        ", event=" + event +
        ", id=" + id +
        '}';
  }
}
