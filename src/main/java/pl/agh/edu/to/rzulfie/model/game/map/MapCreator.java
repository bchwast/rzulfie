package pl.agh.edu.to.rzulfie.model.game.map;

import pl.agh.edu.to.rzulfie.model.game.turtle.Fruit;
import pl.agh.edu.to.rzulfie.model.game.turtle.Move;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapCreator {

    private final Map<Vector, MapField> fieldsByPosition = new HashMap<>();
    private final Vector mapSize;
    private Vector startPosition = null;
    private Vector finishPosition = null;

    public MapCreator(Vector mapSize) {
        this.mapSize = mapSize;
    }

    public GridMap create() {
        return new GridMap(
                fieldsByPosition,
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
        MapField field1 = fieldsByPosition.get(position1);
        MapField field2 = fieldsByPosition.get(position2);
        field1.addMove(Move.vectorToMove(field2.getPosition().subtract(field1.getPosition())));
        field2.addMove(Move.vectorToMove(field1.getPosition().subtract(field2.getPosition())));
    }

    public Map<Vector, MapField> getFieldsByPosition() {
        return fieldsByPosition;
    }

    public Vector getStartPosition() {
        return startPosition;
    }

    public Vector getFinishPosition() {
        return finishPosition;
    }
}
