package src.main.computer;

import src.main.components.GameGrid;

public interface ComputerStrategy {
    /**
     * @return the column where to insert the token
     */
    int getComputerMoveColumn(GameGrid gameGrid);
}
