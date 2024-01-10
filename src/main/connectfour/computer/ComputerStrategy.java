package connectfour.computer;

import connectfour.components.ConnectFourGrid;

/**
 * Interface that implements the Strategy design pattern, used to dynamically assign a different strategy or gamemode
 * to the current game
 *
 * @author Raffaele Talente
 * @see "Design Patterns, GoF, 1994"
 */
public interface ComputerStrategy {
    /**
     * Returns a column of the grid passed as a parameter
     *
     * @param connectFourGrid A ConnectFourGrid object to evaluate in order to get a column
     * @return A column representing the computer move based on the strategy and the input grid
     */
    int computerMoveColumn(ConnectFourGrid connectFourGrid);
}
