package pl.agh.edu.to.rzulfie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.agh.edu.to.rzulfie.model.service.GameResultService;

@SpringBootApplication
public class RzulfieApplication extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void init() {
        ConfigurableApplicationContext context = SpringApplication.run(RzulfieApplication.class);
        GameResultService service = context.getBean(GameResultService.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        var board = new GridPane();
        board.setGridLinesVisible(true);



        Pane helloPane = new Pane(new Label("Hello JavaFx"));
        primaryStage.setScene(new Scene(helloPane));
        primaryStage.show();
    }
}
