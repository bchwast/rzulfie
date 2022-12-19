package pl.agh.edu.to.rzulfie.model.game.map;

import pl.agh.edu.to.rzulfie.model.game.turtle.Fruit;
import pl.agh.edu.to.rzulfie.model.game.turtle.Turtle;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

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
        MapField finishField = getField(finishPosition).orElseThrow(
                () -> new IllegalStateException("Finish field was not initialized"));

        return finishField.getWinningTurtle();
    }

    public Optional<MapField> getFieldWithTurtle(Turtle turtle) {
        return Optional.ofNullable(fieldsByPosition.get(turtle.getPosition()));
    }

    public static GridMap getSampleSimpleMap() {
        int mapLength = 10;
        MapCreator mapCreator = new MapCreator(new Vector(mapLength, 0));

        for (int x = 0; x < mapLength; x++) {
            mapCreator.addMapField(new Vector(x, 0));
        }
        mapCreator.markAsStartField(new Vector(0, 0));
        mapCreator.markAsFinishField(new Vector(mapLength - 1, 0));

        return mapCreator.create();
    }

    public static GridMap getSampleComplexMap() {
        MapCreator mapCreator = new MapCreator(new Vector(10, 10));

        mapCreator.addMapField(new Vector(2, 5));
        mapCreator.markAsStartField(new Vector(2, 5));

        //      'main' way
        mapCreator.addMapField(new Vector(3, 5));
        mapCreator.addLinkBetweenFields(new Vector(2, 5), new Vector(3, 5));

        mapCreator.addMapField(new Vector(4, 5));
        mapCreator.addLinkBetweenFields(new Vector(3, 5), new Vector(4, 5));

        mapCreator.addMapField(new Vector(5, 5));
        mapCreator.addLinkBetweenFields(new Vector(4, 5), new Vector(5, 5));

        mapCreator.addMapField(new Vector(6, 5));
        mapCreator.addLinkBetweenFields(new Vector(5, 5), new Vector(6, 5));

        mapCreator.addMapField(new Vector(7, 5));
        mapCreator.addLinkBetweenFields(new Vector(6, 5), new Vector(7, 5));

        mapCreator.addMapField(new Vector(7, 4));
        mapCreator.addLinkBetweenFields(new Vector(7, 5), new Vector(7, 4));

        mapCreator.addMapField(new Vector(7, 3));
        mapCreator.addLinkBetweenFields(new Vector(7, 4), new Vector(7, 3));

        mapCreator.addMapField(new Vector(7, 2));
        mapCreator.addLinkBetweenFields(new Vector(7, 3), new Vector(7, 2));

        //      shortcut
        mapCreator.addMapField(new Vector(5, 4));
        mapCreator.addLinkBetweenFields(new Vector(5, 5), new Vector(5, 4));

        mapCreator.addMapField(new Vector(5, 3));
        mapCreator.addLinkBetweenFields(new Vector(5, 4), new Vector(5, 3));

        mapCreator.addMapField(new Vector(5, 2));
        mapCreator.addLinkBetweenFields(new Vector(5, 3), new Vector(5, 2));

        mapCreator.addMapField(new Vector(6, 2));
        mapCreator.addLinkBetweenFields(new Vector(5, 2), new Vector(6, 2));
        mapCreator.addLinkBetweenFields(new Vector(7, 2), new Vector(6, 2));

        mapCreator.addMapField(new Vector(6, 1));
        mapCreator.addLinkBetweenFields(new Vector(6, 2), new Vector(6, 1));

        mapCreator.markAsFinishField(new Vector(6, 1));

        mapCreator.addFruit(new Vector(6, 5), 10);
        mapCreator.addFruit(new Vector(7, 4), 10);
        mapCreator.addFruit(new Vector(7, 2), 10);
        mapCreator.addFruit(new Vector(5, 3), 10);

        return mapCreator.create();
    }
}
