package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Daniel Hofmeister on 11.12.2015.
 */
@JsonIgnoreProperties
public class Event {

    @JsonIgnore
    private int id;
    private String type;
    private String name;
    private String reason;
    private String resource;
    private Player player;

    public Event() {}

    public Event(String type, String name, String reason, String resource, Player player) {
        this.type = type;
        this.name = name;
        this.reason = reason;
        this.resource = resource;
        this.player = player;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (id != event.id) return false;
        if (type != null ? !type.equals(event.type) : event.type != null) return false;
        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        if (reason != null ? !reason.equals(event.reason) : event.reason != null) return false;
        if (resource != null ? !resource.equals(event.resource) : event.resource != null) return false;
        return !(player != null ? !player.equals(event.player) : event.player != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", reason='" + reason + '\'' +
                ", resource='" + resource + '\'' +
                ", player=" + player +
                '}';
    }
}
