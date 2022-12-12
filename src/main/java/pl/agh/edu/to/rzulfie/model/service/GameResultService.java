package pl.agh.edu.to.rzulfie.model.service;

import pl.agh.edu.to.rzulfie.model.GameResult;

import java.util.List;

public interface GameResultService {

    void addResult(GameResult result);

    List<GameResult> getAllResults();
}
