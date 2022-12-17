package pl.agh.edu.to.rzulfie.model.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GridMap {

    private final Map<Vector, MapField> fieldsByPosition;
    private final Vector mapSize;
    private final Vector startPosition;
    private final Vector finishPosition;

    public GridMap(Map<Vector, MapField> fieldsByPosition, Vector mapSize, Vector finishPosition,
            Vector startPosition) {
        this.fieldsByPosition = fieldsByPosition;
        this.mapSize = mapSize;
        this.finishPosition = finishPosition;
        this.startPosition = startPosition;
    }

    public static GridMap generateStraightLineGridMap() {
        Vector start = new Vector(0, 0);
        Vector finish = new Vector(15, 0);
        Vector size = new Vector(15, 0);

        Map<Vector, MapField> map = new HashMap<>();

        for (int x = start.getXCoordinate(); x <= size.getXCoordinate(); x++) {
            for (int y = start.getYCoordinate(); y <= size.getYCoordinate(); y++) {
                Vector position = new Vector(x, y);
                map.put(position, new MapField(Collections.emptyList(), position));
            }
        }

        map.values().forEach(mapField -> {
            if (!mapField.getPosition().equals(start)) {
                mapField.addMove(Move.LEFT);
            }
            if (!mapField.getPosition().equals(finish)) {
                mapField.addMove(Move.RIGHT);
            }
        });

        return new GridMap(map, size, finish, start);
    }

    public Vector getMapSize() {
        return mapSize;
    }

    public Optional<MapField> getField(Vector position) {
        return Optional.ofNullable(fieldsByPosition.get(position));
    }

    public void makeMove(Turtle turtle, Vector desiredPosition) {
        Optional<MapField> sourceOptional = getFieldWithTurtle(turtle);
        sourceOptional.ifPresent(source -> {
            MapField destination = getField(desiredPosition)
                    .orElseThrow(() -> new IllegalStateException("No such field on map"));
            List<Turtle> popped = source.popTurtlesAboveTurtle(turtle);
            destination.addTurtlesOnTop(popped);
        });
    }

    public void spawnTurtlesOnMap(List<Turtle> turtles) {
        turtles.forEach(turtle -> turtle.setPosition(startPosition));
        getField(startPosition).orElseThrow(() -> new IllegalStateException("Start position not initialized"))
                .addTurtlesOnTop(turtles);
    }

    public void addFruitToMap(Fruit fruit, Vector position) {
        getField(position).orElseThrow(() -> new IllegalStateException("No such field on map")).setFruit(fruit);
    }

    public Optional<Turtle> getWinner() {
        MapField metaMapField = getField(finishPosition).orElseThrow(
                () -> new IllegalStateException("Finish field was not initialized"));

        return metaMapField.getUpperMostTurtle();
    }

    public Optional<MapField> getFieldWithTurtle(Turtle turtle) {
        return Optional.ofNullable(fieldsByPosition.get(turtle.getPosition()));
    }
}
