package src.main;

import javafx.application.Application;
import javafx.stage.Stage;
import src.main.components.ConnectFourGrid;

import java.util.Scanner;

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
        while(true) {
            ConnectFourGameHandler.getInstance().makeComputerMove();
            ConnectFourGameHandler.getInstance().getGrid().printBoard();
            if(ConnectFourGameHandler.getInstance().getGrid().checkWinner()) {
                ConnectFourGameHandler.getInstance().getGrid().printWinner();
                break;
            }
            int column = getInput();
            ConnectFourGameHandler.getInstance().getGrid().insertToken(
                    ConnectFourGameHandler.getInstance().getGrid().TOKEN_PLAYER_SYMBOL, column);
            if(ConnectFourGameHandler.getInstance().getGrid().checkWinner()) {
                ConnectFourGameHandler.getInstance().getGrid().printBoard();
                ConnectFourGameHandler.getInstance().getGrid().printWinner();
                break;
            }
        }
    }

    private int getInput() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a number between 1 and 7");
        return s.nextInt() - 1;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
