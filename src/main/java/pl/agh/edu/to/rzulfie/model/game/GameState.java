package pl.agh.edu.to.rzulfie.model.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.SingleSelectionModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class GameState {

    private final ObjectProperty<Player> currentPlayer;
    private final ObjectProperty<SingleSelectionModel<Turtle>> currentTurtleSelector;
    private final ObjectProperty<Player> winner;
    private final List<Player> players;
    private final List<Turtle> turtles;

    public GameState(int numberOfPlayers) {
        this.players = IntStream.range(0, numberOfPlayers)
                .mapToObj(i -> new Player(Integer.toString(i)))
                .toList();
        this.turtles = IntStream.range(0, numberOfPlayers)
                .mapToObj(i -> new Turtle(Color.values()[i], players.get(i)))
                .toList();
        this.currentPlayer = new SimpleObjectProperty<>(players.get(0));
        this.winner = new SimpleObjectProperty<>();
        this.currentTurtleSelector = new SimpleObjectProperty<>(new SingleSelectionModel<>() {
            @Override
            protected Turtle getModelItem(int index) {
                return turtles.get(index);
            }

            @Override
            protected int getItemCount() {
                return turtles.size();
            }
        });
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

    public ObjectProperty<SingleSelectionModel<Turtle>> currentTurtleSelector() {
        return currentTurtleSelector;
    }

    public Turtle getCurrentTurtle() {
        return currentTurtleSelector.get().getSelectedItem();
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
