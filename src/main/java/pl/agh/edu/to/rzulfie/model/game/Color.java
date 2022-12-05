package pl.agh.edu.to.rzulfie.model.game;

import javafx.scene.shape.Rectangle;

public enum Color {
    RED(new Rectangle(25, 9, javafx.scene.paint.Color.RED)),
    GREEN(new Rectangle(25, 9, javafx.scene.paint.Color.GREEN)),
    BLUE(new Rectangle(25, 9, javafx.scene.paint.Color.BLUE)),
    YELLOW(new Rectangle(25, 9, javafx.scene.paint.Color.YELLOW)),
    ORANGE(new Rectangle(25, 9, javafx.scene.paint.Color.ORANGE)),
    BROWN(new Rectangle(25, 9, javafx.scene.paint.Color.BROWN));

    private final Rectangle image;

    Color(Rectangle image) {
        this.image = image;
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
