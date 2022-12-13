package pl.agh.edu.to.rzulfie.model.game.utils;

import java.util.Objects;

public class Vector {

    private final int xCoordinate;
    private final int yCoordinate;

    public Vector(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public Vector add(Vector vector) {
        return new Vector(xCoordinate + vector.getXCoordinate(), yCoordinate + vector.getYCoordinate());
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

    @Override
    public String toString() {
        return "Vector{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }
}
