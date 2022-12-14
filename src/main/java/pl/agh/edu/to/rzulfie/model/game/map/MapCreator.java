package pl.agh.edu.to.rzulfie.model.game.map;

import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapCreator {
    private Map<Vector, MapField> fieldsByPosition = new HashMap<>();
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
        MapField mapField = new MapField(Collections.emptyList(),position);
        fieldsByPosition.put(position,mapField);
    }

    public void connectMapFields(Vector positionA, Vector positionB) {

    }

    public void markAsStartField(Vector position) {
        if(fieldsByPosition.containsKey(position)){
            this.startPosition = position;
        }
    }

    public void markAsFinishField(Vector position) {
        if(fieldsByPosition.containsKey(position)){
            this.finishPosition = position;
        }
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
