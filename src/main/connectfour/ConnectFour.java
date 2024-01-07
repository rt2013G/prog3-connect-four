package connectfour;

import connectfour.database.Database;
import connectfour.gui.Controller;

import java.util.Scanner;

public class ConnectFour {
    public static void main(String... args) {
        Database db = new Database();
        db.initTables();
        ConnectFourGameHandler.getInstance().loadGame();
        Controller.getInstance().initView();
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
