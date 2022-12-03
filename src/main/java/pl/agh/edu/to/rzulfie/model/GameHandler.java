package pl.agh.edu.to.rzulfie.model;

import java.util.List;
import java.util.Optional;

public class GameHandler {

    private final GridMap map;


    public GameHandler(GridMap map) {
        this.map = map;
    }

    public void makeMove(Turtle turtle, Move move) {
        MapField source = map.getFieldWithTurtle(turtle);
        Vector sourcePosition = source.getPosition();
        Vector destinationPosition = sourcePosition.add(move.toVector());
        MapField destination = map.getField(destinationPosition)
                .orElseThrow(() -> new IllegalStateException("No such field on map"));
        List<Turtle> popped = source.popTurtlesAboveTurtle(turtle);
        destination.addTurtlesOnTop(popped);
        if(gameOver(destinationPosition)){
            // todo needs better structure
            System.out.println(getWinner().toString());
        }
    }

    public GridMap getMap() {
        return map;
    }

    public void spawnTurtlesOnMap(List<Turtle> turtles) {
        map.getField(map.getStartPosition()).get().addTurtlesOnTop(turtles);
    }

    private Boolean gameOver(Vector destination) {
        return destination.equals(map.getMetaPosition());
    }

    private Turtle getWinner() {
        MapField metaMapField = map.getField(map.getMetaPosition()).orElseThrow(
                () -> new IllegalStateException("MetaField was not initialized"));

        return metaMapField.getUpperMostTurtle();
    }
}
