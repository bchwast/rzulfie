package pl.agh.edu.to.rzulfie.model.game.map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static pl.agh.edu.to.rzulfie.controller.ApplicationController.CELL_SIZE;

public class EmptyCell {
    private final Rectangle image;
    private final ObjectProperty<FlowPane> fieldRepresentationProperty;

    public EmptyCell() {
        this.fieldRepresentationProperty = new SimpleObjectProperty<>();
        this.image = new Rectangle(CELL_SIZE, CELL_SIZE, Color.LIGHTGRAY);
        calculateFieldRepresentationProperty();

    }
    public ObjectProperty<FlowPane> fieldRepresentationProperty() {
        return fieldRepresentationProperty;
    }

    private void calculateFieldRepresentationProperty() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL, 0, 1);
        flowPane.getChildren().add(image);
        flowPane.setPrefWrapLength(60);
        fieldRepresentationProperty.set(flowPane);
    }


    }
