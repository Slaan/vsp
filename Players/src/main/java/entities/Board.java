package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel Hofmeister on 13.12.2015.
 */
@JsonIgnoreProperties
public class Board {

    private List<Field> fields = new ArrayList<>();
    private Map<String,Integer> positions = new HashMap<>();

    public Board() {}

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Map<String, Integer> getPositions() {
        return positions;
    }

    public void setPositions(Map<String, Integer> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "Board{" +
                "fields=" + fields +
                ", positions=" + positions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board = (Board) o;

        if (fields != null ? !fields.equals(board.fields) : board.fields != null) return false;
        return !(positions != null ? !positions.equals(board.positions) : board.positions != null);

    }

    @Override
    public int hashCode() {
        int result = fields != null ? fields.hashCode() : 0;
        result = 31 * result + (positions != null ? positions.hashCode() : 0);
        return result;
    }
}


