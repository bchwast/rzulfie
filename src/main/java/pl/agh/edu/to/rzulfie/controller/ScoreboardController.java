package pl.agh.edu.to.rzulfie.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.edu.to.rzulfie.model.Game;
import pl.agh.edu.to.rzulfie.model.GameResult;
import pl.agh.edu.to.rzulfie.model.game.Player;
import pl.agh.edu.to.rzulfie.model.service.GameResultService;
import pl.agh.edu.to.rzulfie.model.service.GameService;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
public class ScoreboardController {

    private final GameService gameService;
    private final GameResultService gameResultService;
    @FXML
    private TableView<Game> gameHistoryTable;
    @FXML
    private TableColumn<Game, Date> dateColumn;
    @FXML
    private TableColumn<Game, String> winnerColumn;
    @FXML
    private TableColumn<Game, String> detailsColumn;

    @Autowired
    public ScoreboardController(GameService gameService, GameResultService gameResultService) {
        this.gameService = gameService;
        this.gameResultService = gameResultService;
    }

    @FXML
    public void initialize() {
        initializeStartingState();
    }

    private void initializeStartingState() {
        ObservableList<Game> tableData = FXCollections.observableArrayList(gameService.getAllGames());
        dateColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getGameDate()));
        winnerColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().getWinner()));
        detailsColumn.setCellValueFactory(
                dataValue -> new SimpleStringProperty(getResultsAsString(dataValue.getValue())));
        gameHistoryTable.setItems(tableData);
    }

    public void updateScore(List<Player> players) {
        players.stream().max(Comparator.comparingInt(Player::getScore)).ifPresent(winner -> {
            Game newGame = gameService.addGame(new Game(Date.from(Instant.now()), winner.getName()));
            List<GameResult> gameResults =
                    players.stream()
                            .map(player -> new GameResult(player.getName(), player.getScore(), newGame))
                            .toList();
            gameResults.forEach(gameResultService::addResult);
        });
        initializeStartingState();
    }

    private String getResultsAsString(Game game) {
        StringBuilder resultBuilder = new StringBuilder();
        game.getGameResults().forEach(gameResult -> resultBuilder.append(gameResult.getPlayerName())
                .append(", ")
                .append(gameResult.getScore())
                .append("\n"));

        return resultBuilder.toString();
    }
}
