package pl.agh.edu.to.rzulfie.model.game;

import manifold.ext.rt.api.Jailbreak;
import org.junit.jupiter.api.Test;
import pl.agh.edu.to.rzulfie.model.game.map.MapCreator;
import pl.agh.edu.to.rzulfie.model.game.map.MapField;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class MapCreatorTest {
    @Jailbreak MapCreator mapCreator = new MapCreator(new Vector(10,10));

    @Test
    void addFieldShouldWork() {
        int amountOfFieldsBefore = mapCreator.getFieldsByPosition().size();

        Vector position = new Vector(0, 0);
        mapCreator.addMapField(position);

        assertThat(mapCreator.getFieldsByPosition().size()).isEqualTo(amountOfFieldsBefore + 1);
        assertThat(mapCreator.getFieldsByPosition().get(position)).isNotNull();
    }

    @Test
    void markAsStartFieldShouldWork() {
        Vector oldStartPosition = mapCreator.getStartPosition();
        Vector newStartPosition = new Vector(0, 0);
        mapCreator.fieldsByPosition.put(
                newStartPosition,
                new MapField(Collections.emptyList(), newStartPosition)
        );

        mapCreator.markAsStartField(newStartPosition);

        assertThat(oldStartPosition).isNull();
        assertThat(mapCreator.getStartPosition()).isEqualTo(newStartPosition);
    }

    @Test
    void markAsStartFieldShouldFail() {
        Vector oldStartPosition = mapCreator.getStartPosition();
        Vector newStartPosition = new Vector(0, 0);

        mapCreator.markAsStartField(newStartPosition);

        assertThat(mapCreator.getStartPosition()).isEqualTo(oldStartPosition);
    }

    @Test
    void markAsFinishedFieldShouldWork() {
        Vector oldFinishPosition = mapCreator.getFinishPosition();
        Vector newFinishPosition = new Vector(0, 0);
        mapCreator.fieldsByPosition.put(
                newFinishPosition,
                new MapField(Collections.emptyList(), newFinishPosition)
        );

        mapCreator.markAsFinishField(newFinishPosition);

        assertThat(oldFinishPosition).isNull();
        assertThat(mapCreator.getFinishPosition()).isEqualTo(newFinishPosition);
    }

    @Test
    void markAsFinishedFieldShouldFail() {
        Vector oldFinishPosition = mapCreator.getStartPosition();
        Vector newFinishPosition = new Vector(0, 0);

        mapCreator.markAsFinishField(newFinishPosition);

        assertThat(mapCreator.getFinishPosition()).isEqualTo(oldFinishPosition);
    }



}
