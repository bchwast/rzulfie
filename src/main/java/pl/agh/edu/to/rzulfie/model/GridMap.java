package pl.agh.edu.to.rzulfie.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GridMap {

    private final Map<Vector, MapField> fieldsByPosition;
    private final Vector mapSize;
    private final Vector startPosition = new Vector(1,1);
    private final Vector finishPosition;

    public GridMap(Map<Vector, MapField> fieldsByPosition, Vector mapSize,Vector finishPosition) {
        this.fieldsByPosition = fieldsByPosition;
        this.mapSize = mapSize;
        this.finishPosition = finishPosition;
    }

    public static GridMap generateStraightLineGridMap(){
        int size = 10;
        int startIndex = 1; // 0 axis are taken by the map key

        Map<Vector,MapField> map = new HashMap<>();

        for (int x = startIndex; x <= size; x++) {
            Vector position = new Vector(x,startIndex);
            map.put(position,new MapField(Collections.emptyList(),position));
        }

        return new GridMap(map,new Vector(size,size),new Vector(size,1));
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

    public Vector getStartPosition() {
        return startPosition;
    }

    public Vector getFinishPosition() {
        return finishPosition;
    }

}
