package entities;

/**
 * Created by Daniel Hofmeister on 13.12.2015.
 */
public class Roll {
    private int roll;

    public Roll() {}

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    @Override
    public String toString() {
        return "Roll{" +
                "roll=" + roll +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Roll)) return false;

        Roll roll1 = (Roll) o;

        return roll == roll1.roll;

    }

    @Override
    public int hashCode() {
        return roll;
    }
}
