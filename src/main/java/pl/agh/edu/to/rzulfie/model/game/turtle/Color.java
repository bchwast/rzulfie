package pl.agh.edu.to.rzulfie.model.game.turtle;

import javafx.scene.shape.Rectangle;

public enum Color {
    RED(javafx.scene.paint.Color.RED),
    GREEN(javafx.scene.paint.Color.GREEN),
    BLUE(javafx.scene.paint.Color.BLUE),
    YELLOW(javafx.scene.paint.Color.YELLOW),
    ORANGE(javafx.scene.paint.Color.ORANGE),
    BROWN(javafx.scene.paint.Color.BROWN);

    private static final int IMAGE_WIDTH = 25;
    public static final int IMAGE_HEIGHT = 9;

    private final Rectangle image;

    Color(javafx.scene.paint.Color color) {
        this.image = new Rectangle(IMAGE_WIDTH, IMAGE_HEIGHT, color);
    }

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
        return image;
    }
}
