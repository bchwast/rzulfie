package pl.agh.edu.to.rzulfie;

import java.util.Map;

public class GridMap {

    private final Map<Vector, MapField> fieldsByPosition;

    public GridMap(Map<Vector, MapField> fieldsByPosition) {
        this.fieldsByPosition = fieldsByPosition;
    }

    public MapField getFieldWithTurtle(Turtle turtle) {
        return fieldsByPosition.values().stream()
                .filter(mapField -> mapField.hasTurtle(turtle))
                .findFirst().orElseThrow(() -> new IllegalStateException("No such turtle on map"));
    }
}
