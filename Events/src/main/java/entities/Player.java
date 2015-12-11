package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
@JsonIgnoreProperties
public class Player {

    private String id;
    private String name;
    private String uri;
    private Place place;
    private int position;
    private boolean ready;

    public Player() {}

    public Player(String id, String name, String uri, Place place, int position, boolean ready) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.place = place;
        this.position = position;
        this.ready = ready;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isReady() {
        return ready;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (position != player.position) return false;
        if (ready != player.ready) return false;
        if (id != null ? !id.equals(player.id) : player.id != null) return false;
        if (name != null ? !name.equals(player.name) : player.name != null) return false;
        if (uri != null ? !uri.equals(player.uri) : player.uri != null) return false;
        return !(place != null ? !place.equals(player.place) : player.place != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + position;
        result = 31 * result + (ready ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", place=" + place +
                ", position=" + position +
                ", ready=" + ready +
                '}';
    }
}
