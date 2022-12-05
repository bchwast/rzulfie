package pl.agh.edu.to.rzulfie.model.game;

import javafx.scene.shape.Rectangle;

public class Turtle {

    private final Color color;
    private final Player owner;

    public Turtle(Color color, Player owner) {
        this.color = color;
        this.owner = owner;
    }

    public Color getColor() {
        return color;
    }

    public Player getOwner() {
        return owner;
    }

    public Rectangle getGraphicalRepresentation() {
        return color.toImage();
    }

    @Override
    public String toString() {
        return color + " turtle";
    }
}
