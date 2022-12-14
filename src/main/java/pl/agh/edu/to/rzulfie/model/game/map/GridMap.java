package pl.agh.edu.to.rzulfie.model.game.map;

import pl.agh.edu.to.rzulfie.model.game.turtle.Move;
import pl.agh.edu.to.rzulfie.model.game.turtle.Turtle;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

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

    public Vector getMapSize() {
        return mapSize;
    }

    public Optional<MapField> getField(Vector position) {
        return Optional.ofNullable(fieldsByPosition.get(position));
    }

    public Vector getStartPosition() {
        return startPosition;
    }

    public void makeMove(Turtle turtle, Move move) {
        Optional<MapField> sourceOptional = getFieldWithTurtle(turtle);
        sourceOptional.ifPresent(source -> {
            Vector sourcePosition = source.getPosition();
            Vector destinationPosition = sourcePosition.add(move.toVector());
            MapField destination = getField(destinationPosition)
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

    public Optional<Turtle> getWinner() {
        MapField metaMapField = getField(finishPosition).orElseThrow(
                () -> new IllegalStateException("Finish field was not initialized"));

        return metaMapField.getUpperMostTurtle();
    }

    private Optional<MapField> getFieldWithTurtle(Turtle turtle) {
        return Optional.ofNullable(fieldsByPosition.get(turtle.getPosition()));
    }

    public static GridMap SampleSimpleMap(){
        int mapLength = 10;
        MapCreator mapCreator = new MapCreator(new Vector(mapLength,0));

        for (int x = 0; x < mapLength; x++) {
            mapCreator.addMapField(new Vector(x,0));
        }
        mapCreator.markAsStartField(new Vector(0,0));
        mapCreator.markAsFinishField(new Vector(mapLength-1,0));

        return mapCreator.create();
    }

    public static GridMap SampleComplexMap(){
        MapCreator mapCreator = new MapCreator(new Vector(10,10));

        mapCreator.addMapField(new Vector(2,5));
        mapCreator.markAsStartField(new Vector(2,5));

//      'main' way
        mapCreator.addMapField(new Vector(3,5));
        mapCreator.addMapField(new Vector(4,5));
        mapCreator.addMapField(new Vector(5,5));
        mapCreator.addMapField(new Vector(6,5));
        mapCreator.addMapField(new Vector(7,5));
        mapCreator.addMapField(new Vector(7,4));
        mapCreator.addMapField(new Vector(7,3));
        mapCreator.addMapField(new Vector(7,2));

//        shortcut
        mapCreator.addMapField(new Vector(5,4));
        mapCreator.addMapField(new Vector(5,3));
        mapCreator.addMapField(new Vector(5,2));

        mapCreator.addMapField(new Vector(6,2));
        mapCreator.markAsFinishField(new Vector(6,2));


        return mapCreator.create();
    }
}
