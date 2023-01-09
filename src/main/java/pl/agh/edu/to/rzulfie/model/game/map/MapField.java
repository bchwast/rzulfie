package pl.agh.edu.to.rzulfie.model.game.map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.agh.edu.to.rzulfie.model.game.turtle.Fruit;
import pl.agh.edu.to.rzulfie.model.game.turtle.Turtle;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MapField {

    private final ObjectProperty<List<Turtle>> turtles;
    private final Vector position;
    private Optional<Fruit> fruit = Optional.empty();
    private boolean isStart = false;
    private boolean isFinish = false;

    public MapField(List<Turtle> turtles, Vector position) {
        this.turtles = new SimpleObjectProperty<>(turtles);
        this.position = position;
        turtles.forEach(turtle -> turtle.setPosition(position));
    }

    public void setFruit(Fruit fruit) {
        this.fruit = Optional.of(fruit);
    }

    public boolean hasFruit() {
        return fruit.isPresent();
    }

    public List<Turtle> popTurtlesAboveTurtle(Turtle turtle) {
        List<Turtle> currentTurtles = turtles.get();
        var movedTurtles = currentTurtles.subList(currentTurtles.indexOf(turtle), currentTurtles.size());
        turtles.set(currentTurtles.subList(0, currentTurtles.indexOf(turtle)));
        return movedTurtles;
    }

    public void addTurtlesOnTop(List<Turtle> newTurtles) {
        List<Turtle> currentTurtles = turtles.get();
        newTurtles.forEach(turtle -> turtle.setPosition(position));
        turtles.set(Stream.concat(currentTurtles.stream(), newTurtles.stream()).toList());
        handleTurtleEatingFruit();
    }

    public Vector getPosition() {
        return position;
    }

    public ObjectProperty<List<Turtle>> turtlesProperty() {
        return turtles;
    }

    public Optional<Turtle> getTopTurtle() {
        if (turtles.get().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(turtles.get().get(turtles.get().size() - 1));
        }
    }

    public void setAsStart() {
        this.isStart = true;
    }

    public void setAsFinish() {
        this.isFinish = true;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isFinish() {
        return isFinish;
    }

    private void handleTurtleEatingFruit() {
        var firstTurtle = turtles.get().get(0);
        fruit.ifPresent(fruit -> firstTurtle.getOwner().eatFruit(fruit));
        fruit = Optional.empty();
    }
}
