package pl.agh.edu.to.rzulfie.model.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MapField {

    private List<Turtle> turtles;

    private Optional<Fruit> fruit;
    private final ObjectProperty<FlowPane> fieldRepresentationProperty;
    private final Vector position;

    private boolean wasVisited;

    public MapField(List<Turtle> turtles, Vector position) {
        this.turtles = turtles;
        this.position = position;
        this.fieldRepresentationProperty = new SimpleObjectProperty<>();
        turtles.forEach(turtle -> turtle.setPosition(position));
        recalculateFieldRepresentationProperty();
        this.fruit = Optional.empty();
        this.wasVisited = false;
    }

    public MapField(List<Turtle> turtles, Vector position, Fruit fruit) {
        this.turtles = turtles;
        this.position = position;
        this.fieldRepresentationProperty = new SimpleObjectProperty<>();
        turtles.forEach(turtle -> turtle.setPosition(position));
        recalculateFieldRepresentationProperty();
        this.fruit = Optional.of(fruit);
        this.wasVisited = false;
    }

    public List<Turtle> popTurtlesAboveTurtle(Turtle turtle) {
        var movedTurtles = turtles.subList(turtles.indexOf(turtle), turtles.size());
        turtles = turtles.subList(0, turtles.indexOf(turtle));
        recalculateFieldRepresentationProperty();
        return movedTurtles;
    }

    private void handleTurtleEatingFruit(List<Turtle> turtles) {
        var firstTurtle = turtles.get(0);
        fruit.ifPresent(fruit -> firstTurtle.getOwner().eatFruit(fruit));
        fruit = Optional.empty();
        // TODO: hide fruit from map
    }

    public void addTurtlesOnTop(List<Turtle> newTurtles) {
        if (!wasVisited) {
            handleTurtleEatingFruit(newTurtles);
        }
        this.wasVisited = true;

        newTurtles.forEach(turtle -> turtle.setPosition(position));
        turtles = Stream.concat(turtles.stream(), newTurtles.stream()).toList();
        recalculateFieldRepresentationProperty();
    }

    public Vector getPosition() {
        return position;
    }

    public ObjectProperty<FlowPane> fieldRepresentationProperty() {
        return fieldRepresentationProperty;
    }

    public Optional<Turtle> getUpperMostTurtle() {
        if (turtles.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(turtles.get(turtles.size() - 1));
        }
    }

    public Optional<Fruit> getFruit() {
        return fruit;
    }

    private void recalculateFieldRepresentationProperty() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL, 0, 1);
        List<Turtle> turtlesCopy = new ArrayList<>(turtles);
        Collections.reverse(turtlesCopy);
        turtlesCopy.forEach(turtle -> flowPane.getChildren().add(turtle.getGraphicalRepresentation()));
        flowPane.setPrefWrapLength(60);
        fieldRepresentationProperty.set(flowPane);
    }
}
