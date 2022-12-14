package pl.agh.edu.to.rzulfie.model.game.map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pl.agh.edu.to.rzulfie.model.game.turtle.Turtle;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static pl.agh.edu.to.rzulfie.controller.ApplicationController.CELL_SIZE;

public class MapField {

    private List<Turtle> turtles;
    private final ObjectProperty<FlowPane> fieldRepresentationProperty;
    private final Vector position;
    private boolean isStart = false;
    private boolean isFinish = false;

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

    private void markAsStart(FlowPane flowPane) {
        addBorder(flowPane, Color.GREEN);
    }

    public void setAsStart() {
        this.isStart = true;
    }

    private void markAsFinish(FlowPane flowPane) {
        addBorder(flowPane, Color.RED);
    }

    public void setAsFinish() {
        this.isFinish = true;
    }

    private void addBorder(FlowPane flowPane, Color color) {
        int borderThickness = 2;
        flowPane.setBorder(new Border(
                new BorderStroke(
                        color,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(
                                borderThickness,
                                borderThickness,
                                borderThickness,
                                borderThickness))));
    }

    private void recalculateFieldRepresentationProperty() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL, 0, 0);
        if (isStart) {
            markAsStart(flowPane);
        } else if (isFinish) {
            markAsFinish(flowPane);
        }

        List<Turtle> turtlesCopy = new ArrayList<>(turtles);
        Collections.reverse(turtlesCopy);
        turtlesCopy.forEach(turtle -> flowPane.getChildren().add(turtle.getGraphicalRepresentation()));
        flowPane.setPrefWrapLength(CELL_SIZE);

        fieldRepresentationProperty.set(flowPane);
    }
}
