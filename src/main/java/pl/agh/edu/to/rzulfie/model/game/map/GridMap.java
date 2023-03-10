package pl.agh.edu.to.rzulfie.model.game.map;

import pl.agh.edu.to.rzulfie.model.game.turtle.Move;
import pl.agh.edu.to.rzulfie.model.game.turtle.Turtle;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static pl.agh.edu.to.rzulfie.model.game.turtle.Move.vectorToMove;

public class GridMap {

    private final Map<Vector, MapField> fieldsByPosition;
    private final Map<Vector, List<Move>> possibleMovesByPosition;
    private final Vector mapSize;
    private final Vector startPosition;
    private final Vector finishPosition;

    public GridMap(Map<Vector, MapField> fieldsByPosition, Map<Vector, List<Move>> possibleMovesByPosition,
            Vector mapSize, Vector finishPosition, Vector startPosition) {
        this.fieldsByPosition = fieldsByPosition;
        this.possibleMovesByPosition = possibleMovesByPosition;
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

    public void makeMove(Turtle turtle, Vector desiredPosition) {
        Optional<MapField> sourceOptional = getFieldWithTurtle(turtle);
        sourceOptional.ifPresent(source -> {
            if (canMoveBetweenPositions(desiredPosition, source.getPosition())) {
                MapField destination = getField(desiredPosition)
                        .orElseThrow(() -> new IllegalStateException("No such field on map"));
                List<Turtle> popped = source.popTurtlesAboveTurtle(turtle);
                destination.addTurtlesOnTop(popped);
            }
        });
    }

    public void spawnTurtlesOnMap(List<Turtle> turtles) {
        turtles.forEach(turtle -> turtle.setPosition(startPosition));
        getField(startPosition).orElseThrow(() -> new IllegalStateException("Start position not initialized"))
                .addTurtlesOnTop(turtles);
    }

    public Optional<Turtle> getTopTurtleFromFinish() {
        MapField finishField = getField(finishPosition).orElseThrow(
                () -> new IllegalStateException("Finish field was not initialized"));

        return finishField.getTopTurtle();
    }

    public Optional<MapField> getFieldWithTurtle(Turtle turtle) {
        return Optional.ofNullable(fieldsByPosition.get(turtle.getPosition()));
    }

    public List<Move> getPossibleMovesForPosition(Vector position) {
        return possibleMovesByPosition.computeIfAbsent(position, k -> new ArrayList<>());
    }

    private boolean canMoveBetweenPositions(Vector desiredPosition, Vector sourcePosition) {
        return getPossibleMovesForPosition(sourcePosition).contains(
                vectorToMove(desiredPosition.subtract(sourcePosition)));
    }
}
