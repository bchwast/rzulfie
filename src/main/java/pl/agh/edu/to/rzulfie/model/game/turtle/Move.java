package pl.agh.edu.to.rzulfie.model.game.turtle;

import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

public enum Move {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static Move vectorToMove(Vector vector) {
        if (vector.equals(new Vector(-1, 0))) {
            return LEFT;
        }
        if (vector.equals(new Vector(1, 0))) {
            return RIGHT;
        }
        if (vector.equals(new Vector(0, 1))) {
            return UP;
        }
        if (vector.equals(new Vector(0, -1))) {
            return DOWN;
        }
        return null;
    }

    public Vector toVector() {
        return switch (this) {
            case LEFT -> new Vector(-1, 0);
            case RIGHT -> new Vector(1, 0);
            case UP -> new Vector(0, 1);
            case DOWN -> new Vector(0, -1);
        };
    }
}
