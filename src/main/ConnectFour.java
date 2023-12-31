package src.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This is the entry point of the application
 * @author Raffaele Talente
 */
public class ConnectFour extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Connect Four");
        Pane root = new Pane();
        Scene scene = new Scene(root, 1366, 768);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
