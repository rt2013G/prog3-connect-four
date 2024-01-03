package src.main.computer;

import src.main.components.ConnectFourGrid;

public interface ComputerStrategy {
    int bestMoveColumn(ConnectFourGrid connectFourGrid);
}
