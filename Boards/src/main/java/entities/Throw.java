package entities;

/**
 * Created by Daniel Hofmeister on 13.12.2015.
 */
public class Throw {

    private int roll1;
    private int roll2;

    public Throw() {}

    public int getRoll1() {
        return roll1;
    }

    public void setRoll1(int roll1) {
        this.roll1 = roll1;
    }

    public int getRoll2() {
        return roll2;
    }

    public void setRoll2(int roll2) {
        this.roll2 = roll2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Throw)) return false;

        Throw aThrow = (Throw) o;

        if (roll1 != aThrow.roll1) return false;
        return roll2 == aThrow.roll2;

    }

    @Override
    public int hashCode() {
        int result = roll1;
        result = 31 * result + roll2;
        return result;
    }

    @Override
    public String toString() {
        return "Throw{" +
                "roll1=" + roll1 +
                ", roll2=" + roll2 +
                '}';
    }
}
