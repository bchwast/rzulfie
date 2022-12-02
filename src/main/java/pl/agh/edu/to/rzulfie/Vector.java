package pl.agh.edu.to.rzulfie;

import java.util.Objects;

public class Vector {

    private final int xCoordinate;
    private final int yCoordinate;

    public Vector(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vector vector = (Vector) o;
        return xCoordinate == vector.xCoordinate && yCoordinate == vector.yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }
}
