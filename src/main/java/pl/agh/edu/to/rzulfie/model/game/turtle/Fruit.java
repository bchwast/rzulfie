package pl.agh.edu.to.rzulfie.model.game.turtle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public record Fruit(int value) {

    private static final int RADIUS = 5;

    public static Shape getGraphicalRepresentation() {
        return new Circle(RADIUS, Color.BROWN);
    }
}
