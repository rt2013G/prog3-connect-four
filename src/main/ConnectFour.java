package src.main;

import javafx.application.Application;
import javafx.stage.Stage;
import src.main.components.GameGrid;
import src.main.computer.DefenseStrategy;

/**
 * This is the entry point of the application
 * @author Raffaele Talente
 */
public class ConnectFour extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        /*stage.setTitle("Connect Four");
        Pane root = new Pane();
        Scene scene = new Scene(root, 1366, 768);
        Button button = new Button("hello world");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        root.getChildren().add(button);

        stage.setScene(scene);
        stage.show();*/
        GameGrid grid = new GameGrid(new DefenseStrategy());
        grid.makeMove(grid.TOKEN_COMPUTER, 1);
        grid.makeMove(grid.TOKEN_COMPUTER, 2);
        grid.makeMove(grid.TOKEN_COMPUTER, 3);
        grid.makeMove(grid.TOKEN_PLAYER, 1);
        grid.makeMove(grid.TOKEN_PLAYER, 2);
        grid.makeMove(grid.TOKEN_PLAYER, 3);
        grid.makeMove(grid.TOKEN_PLAYER, 4);
        grid.makeMove(grid.TOKEN_PLAYER, 4);
        grid.printBoard();
        grid.printWinner();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
