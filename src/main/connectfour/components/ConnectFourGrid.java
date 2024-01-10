package connectfour.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Stores information about a grid of tokens, and exposes methods to work with the current grid state
 *
 * @author Raffaele Talente
 */
public class ConnectFourGrid {
    public final int ROWS = 6;
    public final int COLUMNS = 7;
    public final char TOKEN_EMPTY_SYMBOL = 'x';
    public final char TOKEN_COMPUTER_SYMBOL = 'c';
    public final char TOKEN_PLAYER_SYMBOL = 'p';
    private char[][] gridState;
    private char lastPlayedMoveSymbol = TOKEN_EMPTY_SYMBOL;
    private boolean playerTurn = true;

    public ConnectFourGrid() {
        this.gridState = initGridState();
    }

    /**
     * @return a char[][] representing the grid state, set to all empty symbols
     */
    private char[][] initGridState() {
        char[][] grid = new char[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                grid[i][j] = TOKEN_EMPTY_SYMBOL;
            }
        }
        return grid;
    }

    /**
     * @return The current grid state stored as a char[][]
     */
    public char[][] getGridState() {
        return this.gridState;
    }

    /**
     * Sets the current grid state
     *
     * @param newGrid The grid state to use
     */
    public void setGridState(char[][] newGrid) {
        this.gridState = newGrid;
    }

    /**
     * Inserts the token defined by tokenSymbol into column if and only if the move is valid
     *
     * @param tokenSymbol The symbol of the token to insert
     * @param column The column to insert the token into
     * @see ConnectFourGrid#isMoveInvalid(int)
     */
    public void insertTokenIfColumnValid(char tokenSymbol, int column) {
        if (isMoveInvalid(column)) {
            return;
        }
        for (int i = 0; i < ROWS; i++) {
            if (i == ROWS - 1) {
                if (isGridCellEmpty(i, column)) {
                    gridState[i][column] = tokenSymbol;
                    this.lastPlayedMoveSymbol = tokenSymbol;
                }
            } else if (!isGridCellEmpty(i + 1, column) && isGridCellEmpty(i, column)) {
                gridState[i][column] = tokenSymbol;
                this.lastPlayedMoveSymbol = tokenSymbol;
            }
        }
    }

    /**
     * Checks if a move in the given column would be invalid, meaning that the column is full
     *
     * @param column The column to check
     * @return True if the column is full, False otherwise
     */
    public boolean isMoveInvalid(int column) {
        return this.gridState[0][column] != TOKEN_EMPTY_SYMBOL;
    }

    /**
     * @return True if it's the player's turn to move, False otherwise
     */
    public boolean isPlayerTurn() {
        return this.playerTurn;
    }

    /**
     * Sets the current turn of the grid
     *
     * @param playerTurn True for the player's turn, False for the computer's
     */
    public void setPlayerTurn(Boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    /**
     * Allows other classes to get the winner symbol without exposing the method for actually getting the symbol
     *
     * @return The symbol of the winner if there's a winner, otherwise the symbol meaning an empty token
     * @see #getWinnerSymbolOrEmptySymbol()
     */
    public char getWinnerSymbol() {
        return getWinnerSymbolOrEmptySymbol();
    }

    /**
     * @return The symbol of the last played token
     */
    public char getLastPlayedMoveSymbol() {
        return this.lastPlayedMoveSymbol;
    }

    /**
     * Sets the symbol last played token
     *
     * @param lastPlayedMoveSymbol The symbol to set to
     */
    public void setLastPlayedMoveSymbol(char lastPlayedMoveSymbol) {
        this.lastPlayedMoveSymbol = lastPlayedMoveSymbol;
    }

    /**
     * Obtains an evaluation for the current state of the grid, from the perspective of the currentTurnSymbol, as an
     * integer.
     * That is, if the inserted symbol is the computer's, and the grid state indicates the computer winning, the
     * evaluation is +1, if the grid state indicates the player winning, the evaluation is -1.
     * Vice-versa if the inserted symbol is the player's.
     * In both cases, if there's no winner, the evaluation is 0.
     *
     * @param currentTurnSymbol The symbol to check
     * @return 0, 1 or -1 based on the above explanation
     * @see #getWinnerSymbolOrEmptySymbol()
     */
    public int getGridEvaluation(char currentTurnSymbol) {
        char winner = getWinnerSymbolOrEmptySymbol();
        if (winner == TOKEN_EMPTY_SYMBOL) {
            return 0;
        }
        if (winner == currentTurnSymbol) {
            return 1;
        }
        return -1;
    }

    /**
     * @return True if the grid is entirely filled with non-empty tokens, False otherwise
     */
    public boolean isGridFull() {
        for (int j = 0; j < COLUMNS; j++) {
            if (!isMoveInvalid(j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtains a list of the column with the least amount of tokens, and then returns one random column from
     * such list
     *
     * @return A random column with the least amount of tokens
     * @see #getColumnsWithLeastTokens()
     */
    public int getRandomColumnWithLeastTokens() {
        List<Integer> values = getColumnsWithLeastTokens();
        Random r = new Random();
        int index = r.nextInt(values.size());
        return values.get(index);
    }

    /**
     * Iterates through the grid state checking for all the column with the least amount of non-empty tokens
     *
     * @return A list of integers representing the columns with the least amount of non-empty tokens
     */
    private List<Integer> getColumnsWithLeastTokens() {
        List<Integer> values = new ArrayList<Integer>();
        int[] tokenCount = new int[COLUMNS];
        int minValue = Integer.MAX_VALUE;
        for (int j = 0; j < COLUMNS; j++) {
            int currentColumnCount = 0;
            for (int i = ROWS - 1; i >= 0; i--) {
                if (this.gridState[i][j] != TOKEN_EMPTY_SYMBOL) {
                    currentColumnCount++;
                }
            }
            tokenCount[j] = currentColumnCount;
            if (currentColumnCount < minValue) {
                minValue = currentColumnCount;
            }
        }
        for (int i = 0; i < COLUMNS; i++) {
            if (tokenCount[i] == minValue) {
                values.add(i);
            }
        }
        return values;
    }

    /**
     * Removes the top token from a column, if the column is empty it doesn't do anything
     *
     * @param column The column to remove the token from
     */
    public void removeTopToken(int column) {
        for (int i = 0; i <= ROWS; i++) {
            if (i == 0 && this.gridState[i][column] != TOKEN_EMPTY_SYMBOL) {
                this.gridState[i][column] = TOKEN_EMPTY_SYMBOL;
                return;
            }
            if (i == ROWS) {
                return;
            }
            if (this.gridState[i][column] != TOKEN_EMPTY_SYMBOL) {
                this.gridState[i][column] = TOKEN_EMPTY_SYMBOL;
                return;
            }
        }
    }

    /**
     * Checks if a token cell of the current grid state is empty
     *
     * @param row The row to check
     * @param col The column to check
     * @return True if the checked cell in the current grid state is empty, False otherwise
     */
    private boolean isGridCellEmpty(int row, int col) {
        return this.gridState[row][col] == TOKEN_EMPTY_SYMBOL;
    }

    /**
     * Checks in all four directions (horizontal, vertical, ascending diagonal and descending diagonal, respectively)
     * if four tokens are connected. It only makes sense to check for the symbol of the last played token.
     *
     * @return The last played token if there's a connect four, the empty symbol otherwise
     */
    private char getWinnerSymbolOrEmptySymbol() {
        for (int j = 0; j < COLUMNS - 3; j++) {
            for (int i = 0; i < ROWS; i++) {
                if (this.gridState[i][j] == lastPlayedMoveSymbol &&
                        this.gridState[i][j + 1] == lastPlayedMoveSymbol &&
                        this.gridState[i][j + 2] == lastPlayedMoveSymbol &&
                        this.gridState[i][j + 3] == lastPlayedMoveSymbol) {
                    return lastPlayedMoveSymbol;
                }
            }
        }
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (this.gridState[i][j] == lastPlayedMoveSymbol &&
                        this.gridState[i + 1][j] == lastPlayedMoveSymbol &&
                        this.gridState[i + 2][j] == lastPlayedMoveSymbol &&
                        this.gridState[i + 3][j] == lastPlayedMoveSymbol) {
                    return lastPlayedMoveSymbol;
                }
            }
        }
        for (int i = 3; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                if (this.gridState[i][j] == lastPlayedMoveSymbol &&
                        this.gridState[i - 1][j + 1] == lastPlayedMoveSymbol &&
                        this.gridState[i - 2][j + 2] == lastPlayedMoveSymbol &&
                        this.gridState[i - 3][j + 3] == lastPlayedMoveSymbol) {
                    return lastPlayedMoveSymbol;
                }
            }
        }
        for (int i = 3; i < ROWS; i++) {
            for (int j = 3; j < COLUMNS; j++) {
                if (this.gridState[i][j] == lastPlayedMoveSymbol &&
                        this.gridState[i - 1][j - 1] == lastPlayedMoveSymbol &&
                        this.gridState[i - 2][j - 2] == lastPlayedMoveSymbol &&
                        this.gridState[i - 3][j - 3] == lastPlayedMoveSymbol) {
                    return lastPlayedMoveSymbol;
                }
            }
        }
        return TOKEN_EMPTY_SYMBOL;
    }
}
