package pl.agh.edu.to.rzulfie.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.to.rzulfie.model.GameResult;
import pl.agh.edu.to.rzulfie.model.repository.GameResultRepository;

import java.util.List;

@Service
public class GameResultServiceImpl implements GameResultService {

    private final GameResultRepository repository;

    @Autowired
    public GameResultServiceImpl(GameResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addResult(GameResult result) {
        repository.save(result);
    }

    @Override
    public List<GameResult> getAllResults() {
        return repository.findAll();
    }
}
