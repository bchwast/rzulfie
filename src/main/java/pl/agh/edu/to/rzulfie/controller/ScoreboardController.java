package pl.agh.edu.to.rzulfie.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.edu.to.rzulfie.model.GameResult;
import pl.agh.edu.to.rzulfie.model.service.GameResultService;

import java.time.Instant;
import java.util.Date;

@Component
public class ScoreboardController {

    private final GameResultService gameResultService;
    @FXML
    private TableView<GameResult> gameHistoryTable;
    @FXML
    private TableColumn<GameResult, Date> dateColumn;
    @FXML
    private TableColumn<GameResult, String> winnerColumn;
    @FXML
    private TableColumn<GameResult, Number> scoreColumn;

    @Autowired
    public ScoreboardController(GameResultService gameResultService) {
        this.gameResultService = gameResultService;
    }

    @FXML
    public void initialize() {
        initializeStartingState();
    }

    private void initializeStartingState() {
        ObservableList<GameResult> tableData = FXCollections.observableArrayList(gameResultService.getAllResults());
        dateColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getGameDate()));
        winnerColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().getWinnerName()));
        scoreColumn.setCellValueFactory(dataValue -> new SimpleIntegerProperty(dataValue.getValue().getScore()));
        gameHistoryTable.setItems(tableData);
    }

    public void updateScore(String name, int score) {
        gameResultService.addResult(
                new GameResult(
                        name,
                        Date.from(Instant.now()),
                        score
                )
        );
        initializeStartingState();
    }
}
