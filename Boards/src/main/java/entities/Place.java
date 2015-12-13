package entities;

/**
 * Created by Daniel Hofmeister on 24.11.2015.
 */
public class Place {

    private String name;

    public Place() {}

    public Place(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                '}';
    }
}
