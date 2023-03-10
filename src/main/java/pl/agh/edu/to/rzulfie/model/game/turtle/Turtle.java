package pl.agh.edu.to.rzulfie.model.game.turtle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Rectangle;
import pl.agh.edu.to.rzulfie.model.game.Player;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

public class Turtle {

    private final Color color;
    private final Player owner;
    private final ObjectProperty<Vector> positionProperty = new SimpleObjectProperty<>();

    public Turtle(Color color, Player owner) {
        this.color = color;
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public ObjectProperty<Vector> positionProperty() {
        return positionProperty;
    }

    public Vector getPosition() {
        return positionProperty.get();
    }

    public void setPosition(Vector vector) {
        positionProperty.set(vector);
    }

    public Rectangle getGraphicalRepresentation() {
        return color.toImage();
    }

    @Override
    public String toString() {
        return color.toString();
    }
}
