package pl.agh.edu.to.rzulfie.model.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class MapFieldTest {

    @Test
    void shouldPopAllTurtles() {
        //given
        Player player = mock(Player.class);
        Turtle turtleA = new Turtle(Color.RED, player);
        Turtle turtleB = new Turtle(Color.BLUE, player);
        Turtle turtleC = new Turtle(Color.GREEN, player);
        List<Turtle> turtles = List.of(turtleA, turtleB, turtleC);
        MapField mapField = new MapField(turtles, mock(Vector.class));

        //when
        mapField.popTurtlesAboveTurtle(turtleA);

        //then
        assertThat(mapField.fieldRepresentationProperty().get()).isEqualTo("");
    }

    @Test
    void shouldPopPartOfTurtles() {
        //given
        Player player = mock(Player.class);
        Turtle turtleA = new Turtle(Color.RED, player);
        Turtle turtleB = new Turtle(Color.BLUE, player);
        Turtle turtleC = new Turtle(Color.GREEN, player);
        List<Turtle> turtles = List.of(turtleA, turtleB, turtleC);
        MapField mapField = new MapField(turtles, mock(Vector.class));

        //when
        mapField.popTurtlesAboveTurtle(turtleB);

        //then
        assertThat(mapField.fieldRepresentationProperty().get()).isEqualTo("R");
    }

    @Test
    void shouldAddTurtlesOnTop() {
        //given
        Player player = mock(Player.class);
        Turtle turtleA = new Turtle(Color.RED, player);
        Turtle turtleB = new Turtle(Color.BLUE, player);
        Turtle turtleC = new Turtle(Color.GREEN, player);
        Turtle turtleD = new Turtle(Color.YELLOW, player);
        List<Turtle> turtles = List.of(turtleA, turtleB, turtleC);
        MapField mapField = new MapField(turtles, mock(Vector.class));

        //when
        mapField.addTurtlesOnTop(List.of(turtleD));

        //then
        assertThat(mapField.fieldRepresentationProperty().get()).isEqualTo("RBGY");
    }
}