package src.main;

import javafx.application.Application;
import javafx.stage.Stage;
import src.main.components.ConnectFourGrid;

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
        //ConnectFourGameHandler.getInstance().getGrid().printBoard();
        ConnectFourGameHandler.getInstance().getGrid().insertToken(
                ConnectFourGameHandler.getInstance().getGrid().TOKEN_PLAYER_SYMBOL, 0);
        ConnectFourGameHandler.getInstance().getGrid().insertToken(
                ConnectFourGameHandler.getInstance().getGrid().TOKEN_COMPUTER_SYMBOL, 2);
        ConnectFourGameHandler.getInstance().getGrid().insertToken(
                ConnectFourGameHandler.getInstance().getGrid().TOKEN_PLAYER_SYMBOL, 0);
        ConnectFourGameHandler.getInstance().getGrid().insertToken(
                ConnectFourGameHandler.getInstance().getGrid().TOKEN_COMPUTER_SYMBOL, 4);
        ConnectFourGameHandler.getInstance().getGrid().insertToken(
                ConnectFourGameHandler.getInstance().getGrid().TOKEN_PLAYER_SYMBOL, 4);
        ConnectFourGameHandler.getInstance().getGrid().insertToken(
                ConnectFourGameHandler.getInstance().getGrid().TOKEN_PLAYER_SYMBOL, 0);
        ConnectFourGameHandler.getInstance().getGrid().insertToken(
                ConnectFourGameHandler.getInstance().getGrid().TOKEN_COMPUTER_SYMBOL, 2);
        ConnectFourGameHandler.getInstance().getGrid().insertToken(
                ConnectFourGameHandler.getInstance().getGrid().TOKEN_COMPUTER_SYMBOL, 4);
        ConnectFourGameHandler.getInstance().getGrid().insertToken(
                ConnectFourGameHandler.getInstance().getGrid().TOKEN_PLAYER_SYMBOL, 4);
        ConnectFourGameHandler.getInstance().makeComputerMove();
        ConnectFourGameHandler.getInstance().getGrid().printBoard();



    }
    public static void main(String[] args) {
        launch(args);
    }
}
