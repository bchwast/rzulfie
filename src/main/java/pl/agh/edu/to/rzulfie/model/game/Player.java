package pl.agh.edu.to.rzulfie.model.game;

import pl.agh.edu.to.rzulfie.model.game.turtle.Fruit;

import java.util.LinkedList;
import java.util.List;

public class Player {

    private final String name;

    private final List<Fruit> eatenFruits;

    public Player(String name) {
        this.name = name;
        this.eatenFruits = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void eatFruit(Fruit fruit) {
        eatenFruits.add(fruit);
    }
    public int getScore() {
        return eatenFruits.stream()
                .mapToInt(Fruit::value)
                .sum();
    }
}
