package pl.agh.edu.to.rzulfie.model.service;

import pl.agh.edu.to.rzulfie.model.Game;

import java.util.List;

public interface GameService {

    Game addGame(Game game);

    List<Game> getAllGames();
}
