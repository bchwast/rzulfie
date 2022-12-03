package pl.agh.edu.to.rzulfie.controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.edu.to.rzulfie.model.*;
import pl.agh.edu.to.rzulfie.model.service.GameResultService;

@Component
public class MapController {

    private static final double CELL_SIZE = 50;

    @FXML
    private ComboBox<Turtle> turtleComboBox;
    @FXML
    private Label selectedTurtleLabel;
    @FXML
    private TextField numberOfPlayersField;
    @FXML
    private GridPane mapPane;
    @FXML
    private Text currentPlayer;

    private final GameResultService gameResultService;
    private GameState gameState;
    private GameHandler gameHandler;

    @Autowired
    public MapController(GameResultService gameResultService) {
        this.gameResultService = gameResultService;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void startButtonClicked() {
        int numberOfPlayers = Integer.parseInt(numberOfPlayersField.getText());
        gameState = new GameState(numberOfPlayers);

        // set combo box items to turtles
        // set selected turtle label to current turtle

        turtleComboBox.getItems().clear();
        turtleComboBox.getItems().addAll(gameState.getTurtles());
        turtleComboBox.selectionModelProperty().bindBidirectional(gameState.currentTurtleSelector());

        // bind current player label to current player
        currentPlayer.textProperty().bind(gameState.getCurrentPlayer().asString());

        // add turtles to the map
        gameHandler.spawnTurtlesOnMap(gameState.getTurtles());
    }

    public void moveRightButtonClicked() {
        gameHandler.makeMove(gameState.getCurrentTurtle(), Move.RIGHT);
        gameState.nextPlayer();
    }

    public void init() {
        mapPane.setGridLinesVisible(true);
        Vector mapSize = gameHandler.getMap().getMapSize();

        Label yx = new Label("y \\ x");
        mapPane.add(yx, 0, mapSize.getYCoordinate(), 1, 1);
        mapPane.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        mapPane.getRowConstraints().add(new RowConstraints(CELL_SIZE));
        GridPane.setHalignment(yx, HPos.CENTER);

        for (int x = 1; x <= mapSize.getXCoordinate(); x++) {
            mapPane.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
            Label label = new Label(String.format("%d", x));
            GridPane.setHalignment(label, HPos.CENTER);
            mapPane.add(label, x, mapSize.getYCoordinate(), 1, 1);
        }

        for (int y = 1; y <= mapSize.getYCoordinate(); y++) {
            mapPane.getRowConstraints().add(new RowConstraints(CELL_SIZE));
            Label label = new Label(String.format("%d", y));
            GridPane.setHalignment(label, HPos.CENTER);
            mapPane.add(label, 0, mapSize.getYCoordinate() - y, 1, 1);
        }

        // Bind turtle description for field to label representing map field
        for (int x = 1; x <= mapSize.getXCoordinate(); x++) {
            for (int y = 1; y <= mapSize.getYCoordinate(); y++) {
                var l = new Label();
                var field = gameHandler.getMap().getField(new Vector(x, y));
                if (field.isPresent()) {
                    l.textProperty().bind(field.get().getTurtleStringProperty());
                    mapPane.add(l, x, mapSize.getYCoordinate()-y);
                }
            }
        }
    }
}
