package pl.agh.edu.to.rzulfie.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import pl.agh.edu.to.rzulfie.model.game.map.MapField;
import pl.agh.edu.to.rzulfie.model.game.turtle.Fruit;
import pl.agh.edu.to.rzulfie.model.game.turtle.Turtle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapCellController {

    public static final int BORDER_THICKNESS = 2;
    @FXML
    private FlowPane contentPane;

    private final MapField mapField;

    public MapCellController(MapField mapField) {
        this.mapField = mapField;
    }

    @FXML
    public void initialize() {
        if (mapField.isStart() || mapField.isFinish()) {
            addBorder();
        }

        addFruit();
        mapField.turtlesProperty().addListener(((observable, oldValue, newValue) -> addTurtles(newValue)));
    }

    private void addBorder() {
        Color color;
        if (mapField.isStart()) {
            color = Color.GREEN;
        } else {
            color = Color.RED;
        }

        BorderWidths borderWidths =
                new BorderWidths(BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS);
        BorderStroke borderStroke = new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, borderWidths);
        contentPane.setBorder(new Border(borderStroke));
    }

    private void addFruit() {
        if (mapField.hasFruit()) {
            contentPane.getChildren().add(Fruit.getGraphicalRepresentation());
        }
    }

    private void addTurtles(List<Turtle> turtles) {
        contentPane.getChildren().clear();
        ArrayList<Turtle> turtlesCopy = new ArrayList<>(turtles);
        Collections.reverse(turtlesCopy);
        turtlesCopy.forEach(turtle -> contentPane.getChildren().add(turtle.getGraphicalRepresentation()));
    }
}
