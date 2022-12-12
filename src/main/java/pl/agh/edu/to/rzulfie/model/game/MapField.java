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
    private final ObjectProperty<FlowPane> fieldRepresentationProperty;
    private final Vector position;

    public MapField(List<Turtle> turtles, Vector position) {
        this.turtles = turtles;
        this.position = position;
        this.fieldRepresentationProperty = new SimpleObjectProperty<>();
        turtles.forEach(turtle -> turtle.setPosition(position));
        recalculateFieldRepresentationProperty();
    }

    public List<Turtle> popTurtlesAboveTurtle(Turtle turtle) {
        var movedTurtles = turtles.subList(turtles.indexOf(turtle), turtles.size());
        turtles = turtles.subList(0, turtles.indexOf(turtle));
        recalculateFieldRepresentationProperty();
        return movedTurtles;
    }

    public void addTurtlesOnTop(List<Turtle> newTurtles) {
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

    private void recalculateFieldRepresentationProperty() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL, 0, 1);
        List<Turtle> turtlesCopy = new ArrayList<>(turtles);
        Collections.reverse(turtlesCopy);
        turtlesCopy.forEach(turtle -> flowPane.getChildren().add(turtle.getGraphicalRepresentation()));
        flowPane.setPrefWrapLength(60);
        fieldRepresentationProperty.set(flowPane);
    }
}
