package pl.agh.edu.to.rzulfie.model;

public enum Color {
    RED,
    GREEN,
    BLUE,
    YELLOW;

    @Override
    public String toString() {
        return switch (this) {
            case RED -> "R";
            case GREEN -> "G";
            case BLUE -> "B";
            case YELLOW -> "Y";
        };
    }
}
