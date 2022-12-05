package pl.agh.edu.to.rzulfie.model.game;

import javafx.scene.shape.Rectangle;

public enum Color {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    ORANGE,
    BROWN;

    @Override
    public String toString() {
        return switch (this) {
            case RED -> "Red";
            case GREEN -> "Green";
            case BLUE -> "Blue";
            case YELLOW -> "Yellow";
            case ORANGE -> "Orange";
            case BROWN -> "Brown";
        };
    }

    public Rectangle toImage() {
        return switch (this) {
            case RED -> new Rectangle(25, 9, javafx.scene.paint.Color.RED);
            case GREEN -> new Rectangle(25, 9, javafx.scene.paint.Color.GREEN);
            case BLUE -> new Rectangle(25, 9, javafx.scene.paint.Color.BLUE);
            case YELLOW -> new Rectangle(25, 9, javafx.scene.paint.Color.YELLOW);
            case ORANGE -> new Rectangle(25, 9, javafx.scene.paint.Color.ORANGE);
            case BROWN -> new Rectangle(25, 9, javafx.scene.paint.Color.BROWN);
        };
    }
}
