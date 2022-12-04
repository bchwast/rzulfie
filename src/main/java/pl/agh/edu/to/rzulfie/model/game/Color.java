package pl.agh.edu.to.rzulfie.model.game;

public enum Color {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    ORANGE,
    WHITE;

    @Override
    public String toString() {
        return switch (this) {
            case RED -> "R";
            case GREEN -> "G";
            case BLUE -> "B";
            case YELLOW -> "Y";
            case ORANGE -> "O";
            case WHITE -> "W";
        };
    }
}
