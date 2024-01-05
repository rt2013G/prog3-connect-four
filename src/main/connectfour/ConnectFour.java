package connectfour;

import connectfour.auth.User;
import connectfour.database.Database;

import java.util.Scanner;

public class ConnectFour {
    public static void main(String... args) {
        User newUser = getNewUser();
        Database db = new Database();
        db.insertUserIntoDatabase(newUser);

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
        db.printTopUsers();
    }

    private static int getInput() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a number between 1 and 7");
        return s.nextInt() - 1;
    }

    private static User getNewUser() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your name");
        String name = s.nextLine();
        System.out.println("Please enter your surname");
        String surname = s.nextLine();
        return new User(name, surname);
    }
}
