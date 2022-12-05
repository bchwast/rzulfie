package pl.agh.edu.to.rzulfie.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.edu.to.rzulfie.model.GameResult;
import pl.agh.edu.to.rzulfie.model.game.GameState;
import pl.agh.edu.to.rzulfie.model.game.GridMap;
import pl.agh.edu.to.rzulfie.model.game.Move;
import pl.agh.edu.to.rzulfie.model.game.Turtle;
import pl.agh.edu.to.rzulfie.model.game.Vector;
import pl.agh.edu.to.rzulfie.model.service.GameResultService;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class ApplicationController {

    private static final double CELL_SIZE = 60;

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
    private Button moveRightButton;
    @FXML
    private Button moveLeftButton;
    @FXML
    private TableView<GameResult> gameHistoryTable;
    @FXML
    private TableColumn<GameResult, Date> dateColumn;
    @FXML
    private TableColumn<GameResult, String> winnerColumn;
    @FXML
    private Text playerTurtle;

    private final GameResultService gameResultService;
    private GameState gameState;
    private GridMap gridMap;
    private int playersAmount;

    @Autowired
    public ApplicationController(GameResultService gameResultService) {
        this.gameResultService = gameResultService;
    }

    public void startButtonClicked() {
        playersAmount = numberOfPlayersComboBox.getValue();
        gameState = new GameState(playersAmount);
        gridMap = GridMap.generateStraightLineGridMap();
        init();
        printPlayers();

        // set combo box items to turtles
        turtleComboBox.getItems().clear();
        turtleComboBox.getItems().addAll(gameState.getTurtles());
        turtleComboBox.selectionModelProperty().bindBidirectional(gameState.currentTurtleSelector());

        // bind current player label to current player
        // bind winner text to winning player
        currentPlayer.textProperty().bind(gameState.currentPlayerProperty().asString());
        winner.textProperty().bind(gameState.winnerProperty().asString());
        winner.visibleProperty().set(false);

        // add turtles to the map
        gridMap.spawnTurtlesOnMap(gameState.getTurtles());

        moveLeftButton.disableProperty().set(false);
        moveRightButton.disableProperty().set(false);
        winner.visibleProperty().set(false);
    }

    public void moveRightButtonClicked() {
        gridMap.makeMove(gameState.getCurrentTurtle(), Move.RIGHT);
        checkGameOver();
        gameState.nextPlayer();
    }

    public void moveLeftButtonClicked() {
        gridMap.makeMove(gameState.getCurrentTurtle(), Move.LEFT);
        gameState.nextPlayer();
    }

    public void init() {
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
                var label = new Label();
                var field = gridMap.getField(new Vector(x, y));
                GridPane.setHalignment(label, HPos.CENTER);
                if (field.isPresent()) {
                    label.labelForProperty().bind(field.get().fieldRepresentationProperty());
                    label.graphicProperty().bind(field.get().fieldRepresentationProperty());
                    mapPane.add(label, x, mapSize.getYCoordinate() - y);
                }
            }
        }
    }

    private void checkGameOver() {
        Optional<Turtle> winningTurtle = gridMap.getWinner();
        boolean isFinished = gameState.handleWinner(winningTurtle);
        if (isFinished) {
            moveLeftButton.disableProperty().set(true);
            moveRightButton.disableProperty().set(true);
            winner.visibleProperty().set(true);
            gameResultService.addResult(new GameResult(gameState.getWinner().getName(), Date.from(Instant.now())));
            initializeStartingState();
        }
    }

    public void initializeStartingState() {
        ObservableList<GameResult> tableData = FXCollections.observableArrayList(gameResultService.getAllResults());
        dateColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getGameDate()));
        winnerColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().getWinnerName()));
        gameHistoryTable.setItems(tableData);

        numberOfPlayersComboBox.getItems().clear();
        numberOfPlayersComboBox.getItems().addAll(IntStream.rangeClosed(1, 10).boxed().toList());
    }

    private void printPlayers() {
        playerTurtle.setText(gameState.getTurtles().toString());
    }
}
