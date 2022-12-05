package pl.agh.edu.to.rzulfie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.agh.edu.to.rzulfie.controller.ApplicationController;

import java.io.IOException;

@SpringBootApplication
public class RzulfieApplication extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void init() {
        context = SpringApplication.run(RzulfieApplication.class);
    }

    private void configureStage(Stage primaryStage, BorderPane rootLayout) {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rzulfie");
        primaryStage.minWidthProperty().bind(rootLayout.minWidthProperty());
        primaryStage.minHeightProperty().bind(rootLayout.minHeightProperty());
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            var loader = new FXMLLoader(getClass().getResource("/view/ApplicationPane.fxml"));
            loader.setControllerFactory(context::getBean);
            BorderPane rootLayout = loader.load();

            // set initial data into controller
            ApplicationController applicationController = loader.getController();
            applicationController.initializeResultTable();

            // add layout to a scene and show them all
            configureStage(primaryStage, rootLayout);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
