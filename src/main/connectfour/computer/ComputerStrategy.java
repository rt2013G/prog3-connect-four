package connectfour.computer;

import connectfour.components.ConnectFourGrid;

public interface ComputerStrategy {
    int computerMoveColumn(ConnectFourGrid connectFourGrid);
}
