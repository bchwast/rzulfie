package pl.agh.edu.to.rzulfie.model.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GridMap {

    private final Map<Vector, MapField> fieldsByPosition;
    private final Vector mapSize;
    private final Vector startPosition = new Vector(0, 0);
    private final Vector finishPosition;

    public GridMap(Map<Vector, MapField> fieldsByPosition, Vector mapSize, Vector finishPosition) {
        this.fieldsByPosition = fieldsByPosition;
        this.mapSize = mapSize;
        this.finishPosition = finishPosition;
    }

    public static GridMap generateStraightLineGridMap() {
        Vector start = new Vector(0,0);
        Vector finish = new Vector(15,0);
        Vector size = new Vector(15, 0);

        Map<Vector, MapField> map = new HashMap<>();

        for (int x = start.getXCoordinate(); x <= size.getXCoordinate(); x++) {
            for (int y = start.getYCoordinate(); y <= size.getYCoordinate(); y++) {
                Vector position = new Vector(x, y);
                map.put(position, new MapField(Collections.emptyList(), position));
            }
        }

        return new GridMap(map, size, finish);
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

    public void makeMove(Turtle turtle, Move move) {
        MapField source = getFieldWithTurtle(turtle);
        Vector sourcePosition = source.getPosition();
        Vector destinationPosition = sourcePosition.add(move.toVector());
        MapField destination = getField(destinationPosition)
                .orElseThrow(() -> new IllegalStateException("No such field on map"));
        List<Turtle> popped = source.popTurtlesAboveTurtle(turtle);
        destination.addTurtlesOnTop(popped);
    }

    public void spawnTurtlesOnMap(List<Turtle> turtles) {
        getField(getStartPosition()).orElseThrow(() -> new IllegalStateException("Start position not initialized"))
                .addTurtlesOnTop(turtles);
    }

    public Optional<Turtle> getWinner() {
        MapField metaMapField = getField(getFinishPosition()).orElseThrow(
                () -> new IllegalStateException("MetaField was not initialized"));

        return metaMapField.getUpperMostTurtle();
    }
}
