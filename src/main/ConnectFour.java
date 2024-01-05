package src.main;

import java.util.Scanner;

public class ConnectFour {
    public static void main(String... args) {
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

    private static int getInput() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a number between 1 and 7");
        return s.nextInt() - 1;
    }
}
