package pl.agh.edu.to.rzulfie.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.agh.edu.to.rzulfie.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

}
