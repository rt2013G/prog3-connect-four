package connectfour;

import connectfour.components.ConnectFourGrid;
import connectfour.computer.ComputerStrategy;
import connectfour.computer.NeutralStrategy;

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

    public void setComputerStrategy(ComputerStrategy computerStrategy) {
        this.computerStrategy = computerStrategy;
    }

    public void makeComputerMove() {
        int column = this.computerStrategy.computerMoveColumn(this.connectFourGrid);
        this.connectFourGrid.insertToken(connectFourGrid.TOKEN_COMPUTER_SYMBOL, column);
    }
    private static final ConnectFourGameHandler Instance = new ConnectFourGameHandler();
    private ConnectFourGrid connectFourGrid = new ConnectFourGrid();
    private ComputerStrategy computerStrategy = new NeutralStrategy();
    private ConnectFourGameHandler() {}

}