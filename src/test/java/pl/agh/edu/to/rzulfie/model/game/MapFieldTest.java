package pl.agh.edu.to.rzulfie.model.game;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;
import pl.agh.edu.to.rzulfie.model.game.map.MapField;
import pl.agh.edu.to.rzulfie.model.game.turtle.Color;
import pl.agh.edu.to.rzulfie.model.game.turtle.Turtle;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

import java.util.List;

import static java.util.Collections.emptyList;
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
        List<Node> resultRepresentation = mapField.fieldRepresentationProperty().get().getChildren();

        //then
        assertThat(resultRepresentation).isEqualTo(emptyList());
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
        List<Rectangle> expectedFieldRepresentationList = List.of(turtleA.getGraphicalRepresentation());

        //when
        mapField.popTurtlesAboveTurtle(turtleB);
        List<Node> resultRepresentation = mapField.fieldRepresentationProperty().get().getChildren();

        //then
        assertThat(resultRepresentation).isEqualTo(expectedFieldRepresentationList);
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
        List<Node> expectedFieldRepresentationList =
                List.of(turtleD.getGraphicalRepresentation(), turtleC.getGraphicalRepresentation(),
                        turtleB.getGraphicalRepresentation(), turtleA.getGraphicalRepresentation());

        //when
        mapField.addTurtlesOnTop(List.of(turtleD));
        List<Node> resultRepresentation = mapField.fieldRepresentationProperty().get().getChildren();

        //then
        assertThat(resultRepresentation).isEqualTo(expectedFieldRepresentationList);
    }
}