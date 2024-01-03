package src.main.computer;

import src.main.components.ConnectFourGrid;

public interface ComputerStrategy {
    int computerMoveColumn(ConnectFourGrid connectFourGrid);
}
