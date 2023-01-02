package pl.agh.edu.to.rzulfie.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.edu.to.rzulfie.model.game.GameState;
import pl.agh.edu.to.rzulfie.model.game.map.GridMap;
import pl.agh.edu.to.rzulfie.model.game.map.MapFactory;
import pl.agh.edu.to.rzulfie.model.game.map.MapField;
import pl.agh.edu.to.rzulfie.model.game.turtle.Turtle;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class ApplicationController {

    public static final double CELL_SIZE = 66;
    public static final String BACKGROUND_COLOR_LIGHTBLUE = "-fx-background-color: lightblue";

    @FXML
    private ComboBox<Turtle> turtleComboBox;
    @FXML
    private ComboBox<Integer> numberOfPlayersComboBox;
    @FXML
    private GridPane mapPane;
    @FXML
    private Text currentPlayer;
    @FXML
    private Text winner;
    @FXML
    private Text playerTurtle;
    @FXML
    private Button startButton;
    @FXML
    private BorderPane scoreboard;

    private final ScoreboardController scoreboardController;
    private final List<StackPane> activeFields = new ArrayList<>();
    private final Map<Vector, StackPane> paneByPosition = new HashMap<>();
    private GameState gameState;
    private GridMap gridMap;

    @Autowired
    public ApplicationController(ScoreboardController scoreboardController) {
        this.scoreboardController = scoreboardController;
    }

    @FXML
    public void initialize() {
        scoreboard.setVisible(false);
        scoreboard.managedProperty().bind(scoreboard.visibleProperty());
        initializeStartingState();
        initializeBindings();
    }

    public void startButtonClicked() throws IOException {
        int playersAmount = numberOfPlayersComboBox.getValue();
        gameState = new GameState(playersAmount);
        gridMap = MapFactory.sampleComplexMap();
        initializeMap();
        printPlayers();
        gridMap.spawnTurtlesOnMap(gameState.getTurtles());

        turtleComboBox.setItems(FXCollections.observableList(gameState.getTurtles()));
        turtleComboBox.getSelectionModel().select(0);
        gameState.currentTurtleProperty().bind(turtleComboBox.valueProperty());

        currentPlayer.textProperty().bind(gameState.currentPlayerProperty().asString());
        winner.textProperty().bind(gameState.winnerProperty().asString());
        winner.visibleProperty().set(false);

        gameState.getTurtles().forEach(turtle ->
                turtle.positionProperty().addListener(((observable, oldValue, newValue) -> {
                    clearEnabledFields();
                    Optional<MapField> fieldOptional = gridMap.getField(newValue);
                    fieldOptional.ifPresent(this::setFieldsEnabledForMove);
                })));

        winner.visibleProperty().set(false);
    }

    private void initializeStartingState() {
        numberOfPlayersComboBox.getItems().clear();
        numberOfPlayersComboBox.getItems().addAll(IntStream.rangeClosed(1, 6).boxed().toList());
        turtleComboBox.setItems(FXCollections.emptyObservableList());
    }

    private void initializeBindings() {
        startButton.disableProperty().bind(numberOfPlayersComboBox.valueProperty().isNull());

        turtleComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Optional<MapField> fieldWithTurtle = gridMap.getFieldWithTurtle(newValue);
                clearEnabledFields();
                fieldWithTurtle.ifPresent(this::setFieldsEnabledForMove);
            }
        }));
    }

    private void checkGameOver() {
        Optional<Turtle> winningTurtle = gridMap.getWinner();
        boolean isFinished = gameState.handleWinner(winningTurtle);
        if (isFinished) {
            clearEnabledFields();
            winner.visibleProperty().set(true);
            scoreboardController.updateScore(gameState.getWinner().getName(), gameState.getWinner().getScore());
            initializeStartingState();
        }
    }

    public void toggleScoreboardButtonClicked() {
        scoreboard.setVisible(!scoreboard.isVisible());
    }

    private void setFieldsEnabledForMove(MapField field) {
        gridMap.getPossibleMovesForPosition(field.getPosition()).forEach(move -> {
            StackPane pane = paneByPosition.get(field.getPosition().add(move.toVector()));
            pane.setMouseTransparent(false);
            pane.setMaxWidth(CELL_SIZE - 3);
            pane.setMaxHeight(CELL_SIZE - 3);
            pane.setStyle(BACKGROUND_COLOR_LIGHTBLUE);
            activeFields.add(pane);
        });
    }

    private void clearEnabledFields() {
        activeFields.forEach(field -> {
            field.setStyle("");
            field.setMouseTransparent(true);
        });
        activeFields.clear();
    }

    private FlowPane createUnavailableCell() {
        var image = new Rectangle(CELL_SIZE - 1, CELL_SIZE - 1, Color.LIGHTGRAY);
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL, 0, 0);
        flowPane.getChildren().add(image);
        flowPane.setPrefWrapLength(CELL_SIZE);
        return flowPane;
    }

    private void initializeMap() throws IOException {
        mapPane.getChildren().clear();
        mapPane.setGridLinesVisible(false);
        mapPane.getColumnConstraints().clear();
        mapPane.getRowConstraints().clear();
        mapPane.setGridLinesVisible(true);
        Vector mapSize = gridMap.getMapSize();

        for (int x = 0; x <= mapSize.getXCoordinate(); x++) {
            mapPane.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        }

        for (int y = 0; y <= mapSize.getYCoordinate(); y++) {
            mapPane.getRowConstraints().add(new RowConstraints(CELL_SIZE));
        }

        // Bind turtle description for field to label representing map field
        for (int x = 0; x <= mapSize.getXCoordinate(); x++) {
            for (int y = 0; y <= mapSize.getYCoordinate(); y++) {
                var stackPane = new StackPane();
                var label = new Label();
                var field = gridMap.getField(new Vector(x, y));
                GridPane.setHalignment(label, HPos.CENTER);
                if (field.isPresent()) {
                    FXMLLoader fieldLoader = new FXMLLoader(getClass().getResource("/view/MapCellPane.fxml"));
                    fieldLoader.setControllerFactory(arg -> new MapCellController(field.get()));
                    label.graphicProperty().set(fieldLoader.load());
                    stackPane.setOnMouseClicked(event -> {
                        gridMap.makeMove(gameState.getCurrentTurtle(), field.get().getPosition());
                        checkGameOver();
                        gameState.nextPlayer();
                    });
                    stackPane.setPrefSize(CELL_SIZE, CELL_SIZE);
                    stackPane.setMouseTransparent(true);
                    paneByPosition.put(new Vector(x, y), stackPane);
                    stackPane.getChildren().addAll(label);
                    mapPane.add(stackPane, x, mapSize.getYCoordinate() - y);
                } else {
                    label.graphicProperty().set(createUnavailableCell());
                    mapPane.add(label, x, mapSize.getYCoordinate() - y);
                }
            }
        }
    }

    private void printPlayers() {
        playerTurtle.setText(gameState.getTurtles().toString());
    }
}
