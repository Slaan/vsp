package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Hofmeister on 13.12.2015.
 */
@JsonIgnoreProperties
public class Field {

    private Place place;
    private List<Player> players = new ArrayList<>();

    public Field() {}

    public Field(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Field{" +
                "place=" + place +
                ", players=" + players +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field)) return false;

        Field field = (Field) o;

        if (place != null ? !place.equals(field.place) : field.place != null) return false;
        return !(players != null ? !players.equals(field.players) : field.players != null);

    }

    @Override
    public int hashCode() {
        int result = place != null ? place.hashCode() : 0;
        result = 31 * result + (players != null ? players.hashCode() : 0);
        return result;
    }
}
