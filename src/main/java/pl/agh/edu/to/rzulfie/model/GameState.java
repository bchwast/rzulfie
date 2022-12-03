package pl.agh.edu.to.rzulfie.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.SingleSelectionModel;

import java.util.List;
import java.util.stream.IntStream;

public class GameState {

    private final IntegerProperty numberOfPlayers;
    private final ObjectProperty<Player> currentPlayer;
    private final ObjectProperty<SingleSelectionModel<Turtle>> currentTurtleSelector;
    private final List<Player> players;

    private final List<Turtle> turtles;

    public GameState(int numberOfPlayers) {
        this.players = IntStream.range(0, numberOfPlayers)
                .mapToObj(i -> new Player(Integer.toString(i)))
                .toList();
        // TODO fix this (it will break for more than 4 players)
        this.turtles = IntStream.range(0, numberOfPlayers)
                .mapToObj(i -> new Turtle(Color.values()[i], players.get(i)))
                .toList();
        this.numberOfPlayers = new SimpleIntegerProperty(numberOfPlayers);
        this.currentPlayer = new SimpleObjectProperty<>(players.get(0));
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
    
    public IntegerProperty getNumberOfPlayers() {
        return numberOfPlayers;
    }
    
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers.set(numberOfPlayers);
    }
    
    public ObjectProperty<Player> getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void nextPlayer() {
        var index = players.indexOf(currentPlayer.get());
        currentPlayer.set(players.get((index + 1) % players.size()));
    }

    public ObjectProperty<SingleSelectionModel<Turtle>> currentTurtleSelector() {
        return currentTurtleSelector;
    }

    public Turtle getCurrentTurtle(){
        return currentTurtleSelector.get().getSelectedItem();
    }

    public List<Turtle> getTurtles() {
        return turtles;
    }
}
