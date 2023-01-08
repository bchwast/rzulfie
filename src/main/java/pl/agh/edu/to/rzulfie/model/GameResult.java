package pl.agh.edu.to.rzulfie.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String playerName;

    private int score;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public GameResult() {
    }

    public GameResult(String playerName, int score, Game game) {
        this.playerName = playerName;
        this.score = score;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String winnerName) {
        this.playerName = winnerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
