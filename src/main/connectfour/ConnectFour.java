package connectfour;

import connectfour.auth.Authenticator;
import connectfour.components.ConnectFourGrid;
import connectfour.database.Database;

import java.io.File;
import java.util.Scanner;

public class ConnectFour {
    public static void main(String... args) {
        Database db = new Database();
        db.initTables();
        Authenticator.getInstance().authenticate();
        ConnectFourGameHandler.getInstance().loadGame();
        ConnectFourGrid fakeGrid = new ConnectFourGrid();
        while(true) {
            if(ConnectFourGameHandler.getInstance().getGrid().getLastPlayedMoveSymbol() == fakeGrid.TOKEN_COMPUTER_SYMBOL) {
                // player's turn now
                int column = getInput();
                ConnectFourGameHandler.getInstance().getGrid().insertToken(
                        ConnectFourGameHandler.getInstance().getGrid().TOKEN_PLAYER_SYMBOL, column);
                if(ConnectFourGameHandler.getInstance().getGrid().checkWinner()) {
                    ConnectFourGameHandler.getInstance().getGrid().printBoard();
                    ConnectFourGameHandler.getInstance().getGrid().printWinner();
                    break;
                }
            } else {
                // computer's turn now
                ConnectFourGameHandler.getInstance().makeComputerMove();
                ConnectFourGameHandler.getInstance().getGrid().printBoard();
                if(ConnectFourGameHandler.getInstance().getGrid().checkWinner()) {
                    ConnectFourGameHandler.getInstance().getGrid().printWinner();
                    break;
                }
            }
        }
        ConnectFourGameHandler.getInstance().exitWithoutSavingGame();
    }

    private static int getInput() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a number between 1 and 7, or 'q' to save the game and quit");
        char c = s.nextLine().toCharArray()[0];
        if(c == 'q') {
            ConnectFourGameHandler.getInstance().saveAndQuit();
        }
        return Integer.parseInt(String.valueOf(c)) - 1;
    }
}
