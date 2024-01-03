package src.main;

import src.main.components.ConnectFourGrid;

public class ConnectFourGameHandler {
    public static ConnectFourGameHandler getInstance() {
        return Instance;
    }

    public ConnectFourGrid getGrid() {
        return this.connectFourGrid;
    }

    public void setGrid(ConnectFourGrid connectFourGrid) {
        this.connectFourGrid = connectFourGrid;
    }
    private static final ConnectFourGameHandler Instance = new ConnectFourGameHandler();
    private ConnectFourGrid connectFourGrid = new ConnectFourGrid();
    private ConnectFourGameHandler() {}

}
