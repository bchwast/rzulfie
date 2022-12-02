package pl.agh.edu.to.rzulfie;

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
}
