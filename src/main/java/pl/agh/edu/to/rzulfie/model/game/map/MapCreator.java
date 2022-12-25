package pl.agh.edu.to.rzulfie.model.game.map;

import pl.agh.edu.to.rzulfie.model.game.turtle.Fruit;
import pl.agh.edu.to.rzulfie.model.game.turtle.Move;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pl.agh.edu.to.rzulfie.model.game.turtle.Move.vectorToMove;

public class MapCreator {

    private final Map<Vector, MapField> fieldsByPosition = new HashMap<>();
    private final Map<Vector, List<Move>> possibleMovesByPosition = new HashMap<>();
    private final Vector mapSize;
    private Vector startPosition = null;
    private Vector finishPosition = null;

    public MapCreator(Vector mapSize) {
        this.mapSize = mapSize;
    }

    public GridMap create() {
        return new GridMap(
                fieldsByPosition,
                possibleMovesByPosition,
                mapSize,
                finishPosition,
                startPosition
        );
    }

    public void addMapField(Vector position) {
        MapField mapField = new MapField(Collections.emptyList(), position);
        fieldsByPosition.put(position, mapField);
    }

    public void addFruit(Vector position, int value) {
        MapField field = fieldsByPosition.get(position);
        field.setFruit(new Fruit(value));
    }

    public void markAsStartField(Vector position) {
        if (fieldsByPosition.containsKey(position)) {
            this.startPosition = position;
            fieldsByPosition.get(position).setAsStart();
        }
    }

    public void markAsFinishField(Vector position) {
        if (fieldsByPosition.containsKey(position)) {
            this.finishPosition = position;
            fieldsByPosition.get(position).setAsFinish();
        }
    }

    public void addLinkBetweenFields(Vector position1, Vector position2) {
        List<Move> position1Moves = possibleMovesByPosition.computeIfAbsent(position1, k -> new ArrayList<>());
        position1Moves.add(vectorToMove(position2.subtract(position1)));
        possibleMovesByPosition.put(position1, position1Moves);
    }

    public Map<Vector, MapField> getFieldsByPosition() {
        return fieldsByPosition;
    }

    public Vector getFinishPosition() {
        return finishPosition;
    }

    public Vector getStartPosition() {
        return startPosition;
    }
}
