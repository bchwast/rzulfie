package pl.agh.edu.to.rzulfie.model.game.turtle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public record Fruit(int value) {

    public Shape getGraphicalRepresentation() {
        return new Circle(5, Color.BROWN);
    }
}
