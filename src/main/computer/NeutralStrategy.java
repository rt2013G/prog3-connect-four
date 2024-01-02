package src.main.computer;

import src.main.components.GameGrid;

public class NeutralStrategy implements ComputerStrategy {
    /**
     * @return the column where to insert the token
     */
    @Override
    public int getComputerMoveColumn(GameGrid gameGrid) {
        return 0;
    }
}
