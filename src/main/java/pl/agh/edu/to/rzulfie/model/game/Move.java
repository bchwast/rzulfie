package pl.agh.edu.to.rzulfie.model.game;

public enum Move {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public Vector toVector() {
        return switch (this) {
            case LEFT -> new Vector(-1, 0);
            case RIGHT -> new Vector(1, 0);
            case UP -> new Vector(0, -1);
            case DOWN -> new Vector(0, 1);
        };
    }
}
