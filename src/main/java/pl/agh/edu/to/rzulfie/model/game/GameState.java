package pl.agh.edu.to.rzulfie.model.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.agh.edu.to.rzulfie.model.game.turtle.Color;
import pl.agh.edu.to.rzulfie.model.game.turtle.Turtle;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class GameState {

    private final ObjectProperty<Player> currentPlayer;
    private final ObjectProperty<Turtle> currentTurtle;
    private final ObjectProperty<Player> winner;
    private final List<Player> players;
    private final List<Turtle> turtles;

    public GameState(int numberOfPlayers) {
        this.players = IntStream.range(0, numberOfPlayers)
                .mapToObj(i -> new Player("Player " + i))
                .toList();
        this.turtles = IntStream.range(0, numberOfPlayers)
                .mapToObj(i -> new Turtle(Color.values()[i], players.get(i)))
                .toList();
        this.currentPlayer = new SimpleObjectProperty<>(players.get(0));
        this.winner = new SimpleObjectProperty<>();
        this.currentTurtle = new SimpleObjectProperty<>();
    }

    public ObjectProperty<Player> currentPlayerProperty() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner.get();
    }

    public ObjectProperty<Player> winnerProperty() {
        return winner;
    }

    public void nextPlayer() {
        var index = players.indexOf(currentPlayer.get());
        currentPlayer.set(players.get((index + 1) % players.size()));
    }

    public ObjectProperty<Turtle> currentTurtleProperty() {
        return currentTurtle;
    }

    public Turtle getCurrentTurtle() {
        return currentTurtle.get();
    }

    public List<Turtle> getTurtles() {
        return turtles;
    }

    public boolean handleWinner(Optional<Turtle> winningTurtleOptional) {
        if (winningTurtleOptional.isPresent()) {
            winner.set(winningTurtleOptional.get().getOwner());
            return true;
        }
        return false;
    }
}
