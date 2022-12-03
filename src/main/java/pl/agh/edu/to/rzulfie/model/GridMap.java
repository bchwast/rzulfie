package pl.agh.edu.to.rzulfie.model;

import java.util.Map;
import java.util.Optional;

public class GridMap {

    private final Map<Vector, MapField> fieldsByPosition;
    private final Vector mapSize;
    private final Vector startPosition = new Vector(1,1);
    private final Vector metaPosition;

    public GridMap(Map<Vector, MapField> fieldsByPosition, Vector mapSize,Vector metaPosition) {
        this.fieldsByPosition = fieldsByPosition;
        this.mapSize = mapSize;
        this.metaPosition = metaPosition;
    }

    public MapField getFieldWithTurtle(Turtle turtle) {
        return fieldsByPosition.values().stream()
                .filter(mapField -> mapField.hasTurtle(turtle))
                .findFirst().orElseThrow(() -> new IllegalStateException("No such turtle on map"));
    }

    public Vector getMapSize() {
        return mapSize;
    }

    public Optional<MapField> getField(Vector position) {
        return Optional.ofNullable(fieldsByPosition.get(position));
    }

    @Override
    public String toString() {
        return "GridMap{" +
                "fieldsByPosition=" + fieldsByPosition.keySet() +
                '}';
    }

    public Vector getStartPosition() {
        return startPosition;
    }

    public Vector getMetaPosition() {
        return metaPosition;
    }
}
