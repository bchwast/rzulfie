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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.edu.to.rzulfie.model.GameResult;
import pl.agh.edu.to.rzulfie.model.game.GameState;
import pl.agh.edu.to.rzulfie.model.game.map.EmptyCell;
import pl.agh.edu.to.rzulfie.model.game.map.GridMap;
import pl.agh.edu.to.rzulfie.model.game.turtle.Move;
import pl.agh.edu.to.rzulfie.model.game.turtle.Turtle;
import pl.agh.edu.to.rzulfie.model.game.utils.Vector;
import pl.agh.edu.to.rzulfie.model.service.GameResultService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class ApplicationController {

    public static final double CELL_SIZE = 60;

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
    private TableView<GameResult> gameHistoryTable;
    @FXML
    private TableColumn<GameResult, Date> dateColumn;
    @FXML
    private TableColumn<GameResult, String> winnerColumn;
    @FXML
    private Text playerTurtle;
    @FXML
    private Button startButton;

    private final List<StackPane> activeFields = new ArrayList<>();
    private final Map<Vector, StackPane> paneByPosition = new HashMap<>();

    private final GameResultService gameResultService;
    private GameState gameState;
    private GridMap gridMap;

    @Autowired
    public ApplicationController(GameResultService gameResultService) {
        this.gameResultService = gameResultService;
    }

    @FXML
    public void initialize() {
        initializeStartingState();
        initializeBindings();
    }

    public void startButtonClicked() {
        int playersAmount = numberOfPlayersComboBox.getValue();
        gameState = new GameState(playersAmount);
        gridMap = GridMap.SampleComplexMap();
        initializeMap();
        printPlayers();
        gridMap.spawnTurtlesOnMap(gameState.getTurtles());


//        gridMap.addFruitToMap(new Fruit(123), new Vector(0, 0)); # adding fruit to map



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
        ObservableList<GameResult> tableData = FXCollections.observableArrayList(gameResultService.getAllResults());
        dateColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getGameDate()));
        winnerColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().getWinnerName()));
        gameHistoryTable.setItems(tableData);

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
            gameResultService.addResult(
                    new GameResult(
                            gameState.getWinner().getName(),
                            Date.from(Instant.now()),
                            gameState.getWinner().getScore()
                    )
            );
            initializeStartingState();
        }
    }

    private void setFieldsEnabledForMove(MapField field) {
        field.getPossibleMoves().forEach(move -> {
            StackPane pane = paneByPosition.get(field.getPosition().add(move.toVector()));
            pane.setMouseTransparent(false);
            pane.setStyle("-fx-background-color: lightblue");
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

    private void initializeMap() {
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
                    label.labelForProperty().bind(field.get().fieldRepresentationProperty());
                    label.graphicProperty().bind(field.get().fieldRepresentationProperty());
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
                }else{
                    label.graphicProperty().bind(new EmptyCell().fieldRepresentationProperty());
                    mapPane.add(label, x, mapSize.getYCoordinate() - y);
                }
            }
        }
    }

    private void printPlayers() {
        playerTurtle.setText(gameState.getTurtles().toString());
    }
}
