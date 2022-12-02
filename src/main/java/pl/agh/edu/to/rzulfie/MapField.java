package pl.agh.edu.to.rzulfie;

import java.util.List;
import java.util.stream.Stream;

public class MapField {

    private List<Turtle> turtles;

    public MapField(List<Turtle> turtles) {
        this.turtles = turtles;
    }

    public List<Turtle> popTurtlesAboveTurtle(Turtle turtle) {
        return turtles.subList(turtles.indexOf(turtle), turtles.size());
    }

    public void addTurtlesOnTop(List<Turtle> newTurtles) {
        turtles = Stream.concat(turtles.stream(), newTurtles.stream()).toList();
    }

    public boolean hasTurtle(Turtle turtle) {
        return turtles.contains(turtle);
    }
}
