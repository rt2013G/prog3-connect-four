package connectfour;

import connectfour.gui.Controller;

/**
 * Entry point of the application, it first lets the game handler load the game, then gives control to the controller
 * for handling the GUI
 *
 * @author Raffaele Talente
 * @see GameHandler
 * @see Controller
 */
public class ConnectFour {
    public static void main(String... args) {
        GameHandler.getInstance().initGame();
        Controller.getInstance().initView();
    }
}
