package pl.agh.edu.to.rzulfie.model.game;

import org.junit.jupiter.api.Test;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

import static org.assertj.core.api.Assertions.assertThat;

class VectorTest {

    @Test
    void shouldAddTwoVectors() {
        //given
        Vector vectorA = new Vector(10, 5);
        Vector vectorB = new Vector(2, 9);
        Vector expectedResult = new Vector(12, 14);

        //when
        Vector result = vectorA.add(vectorB);

        //then
        assertThat(result).isEqualTo(expectedResult);
    }
}