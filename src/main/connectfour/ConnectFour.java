package connectfour;

import connectfour.database.Database;
import connectfour.gui.Controller;

public class ConnectFour {
    public static void main(String... args) {
        Database db = new Database();
        db.initTables();
        GameHandler.getInstance().loadLastGame();
        Controller.getInstance().initView();
    }
}
