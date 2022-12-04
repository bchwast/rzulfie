package pl.agh.edu.to.rzulfie.model.game;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MapField {

    private List<Turtle> turtles;
    private final StringProperty turtleStringProperty;
    private final Vector position;

    public MapField(List<Turtle> turtles, Vector position) {
        this.turtles = turtles;
        this.position = position;
        this.turtleStringProperty = new SimpleStringProperty();
        recalculateTurtleStringProperty();
    }

    public List<Turtle> popTurtlesAboveTurtle(Turtle turtle) {
        var movedTurtles = new ArrayList<>(turtles.subList(turtles.indexOf(turtle), turtles.size()));
        turtles = turtles.subList(0, turtles.indexOf(turtle));
        recalculateTurtleStringProperty();
        return movedTurtles;
    }

    public void addTurtlesOnTop(List<Turtle> newTurtles) {
        turtles = Stream.concat(turtles.stream(), newTurtles.stream()).toList();
        recalculateTurtleStringProperty();
    }

    public Vector getPosition() {
        return position;
    }

    private void recalculateTurtleStringProperty() {
        turtleStringProperty.set(StringUtils.trimToEmpty(turtles.stream().map(Turtle::getColor)
                .map(Color::toString)
                .reduce("", (a, b) -> a + b)));
    }

    public StringProperty turtleStringProperty() {
        return turtleStringProperty;
    }

    public boolean hasTurtle(Turtle turtle) {
        return turtles.contains(turtle);
    }

    public Optional<Turtle> getUpperMostTurtle() {
        if (turtles.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(turtles.get(turtles.size() - 1));
        }
    }
}
