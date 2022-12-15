package pl.agh.edu.to.rzulfie.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class GridMapTest {

    private Map<Vector, MapField> mapFieldByVector;
    private Turtle turtle;

    @BeforeEach
    void setUp() {
        Player player = mock(Player.class);
        this.turtle = new Turtle(Color.RED, player);
        MapField mapFieldA = new MapField(List.of(this.turtle), new Vector(5, 5));
        MapField mapFieldB = new MapField(emptyList(), new Vector(4, 5));
        MapField mapFieldC = new MapField(emptyList(), new Vector(6, 5));
        MapField mapFieldD = new MapField(emptyList(), new Vector(5, 4));
        MapField mapFieldE = new MapField(emptyList(), new Vector(5, 6));
        this.mapFieldByVector = Map.of(new Vector(5, 5), mapFieldA,
                new Vector(4, 5), mapFieldB,
                new Vector(6, 5), mapFieldC,
                new Vector(5, 4), mapFieldD,
                new Vector(5, 6), mapFieldE);
    }

    @Test
    void shouldMoveToGivenField() {
        //given
        GridMap gridMap = new GridMap(mapFieldByVector, mock(Vector.class), mock(Vector.class), mock(Vector.class));

        //when
        gridMap.makeMove(turtle, new Vector(5, 6));

        //then
        assertThat(gridMap.getField(new Vector(5, 5)).get()
                .fieldRepresentationProperty().get().getChildren())
                .isEqualTo(emptyList());
        assertThat(gridMap.getField(new Vector(4, 5)).get()
                .fieldRepresentationProperty().get().getChildren())
                .isEqualTo(emptyList());
        assertThat(gridMap.getField(new Vector(6, 5)).get()
                .fieldRepresentationProperty().get().getChildren())
                .isEqualTo(emptyList());
        assertThat(gridMap.getField(new Vector(5, 4)).get()
                .fieldRepresentationProperty().get().getChildren())
                .isEqualTo(emptyList());
        assertThat(gridMap.getField(new Vector(5, 6)).get()
                .fieldRepresentationProperty().get().getChildren())
                .isEqualTo(List.of(turtle.getGraphicalRepresentation()));
        assertThat(turtle.getPosition()).isEqualTo(new Vector(5, 6));
    }
}