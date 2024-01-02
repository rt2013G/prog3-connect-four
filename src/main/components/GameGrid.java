package src.main.components;

import src.main.computer.ComputerStrategy;

/**
 * The grid class manages the current position of the tokens and exposes methods to make a new move and get the current
 * position
 * @author Raffaele Talente
 */
public class GameGrid {
    public final int TOKEN_EMPTY = 0;
    public final int TOKEN_PLAYER = 1;
    public final int TOKEN_COMPUTER = 2;
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private final int[][] grid = new int[ROWS][COLUMNS];
    private ComputerStrategy computerStrategy = null;
    private int lastMoveToken = TOKEN_EMPTY;

    public GameGrid(ComputerStrategy computerStrategy) {
        this.computerStrategy = computerStrategy;
    }

    public void makeMove(int tokenColor, int column) {
        for(int i = 0; i < ROWS; i++) {
            if(grid[i][column] == TOKEN_EMPTY) {
                if(i == ROWS - 1) {
                    grid[i][column] = tokenColor;
                    lastMoveToken = tokenColor;
                } else if(grid[i + 1][column] != TOKEN_EMPTY) {
                    grid[i][column] = tokenColor;
                    lastMoveToken = tokenColor;
                }
            }
        }
    }

    public void makeComputerMove() {
        int column = computerStrategy.getComputerMoveColumn(this);
        makeMove(TOKEN_COMPUTER, column);
    }

    public int[][] getGameGrid() {
        return this.grid;
    }

    public void printBoard() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                System.out.print(grid[i][j]);
                System.out.print("-");
            }
            System.out.println();
        }
    }

    public void printWinner() {
        int winner = evaluatePosition();
        switch(winner) {
            case TOKEN_COMPUTER:
                System.out.println("COMPUTER WINS");
                break;
            case TOKEN_PLAYER:
                System.out.println("PLAYER WINS");
                break;
        }
    }

    /**
     * Evaluate the board to check if there's a winner, i.e. 4 tokens are aligned in the same direction
     * @return TOKEN_EMPTY if the game is still going, lastMoveToken othwerise
     */
    private int evaluatePosition() {
        for (int j = 0; j < ROWS - 3; j++) {
            for (int i = 0; i < COLUMNS; i++) {
                try {
                    if (this.grid[i][j] == lastMoveToken && this.grid[i][j + 1] == lastMoveToken &&
                            this.grid[i][j + 2] == lastMoveToken && this.grid[i][j + 3] == lastMoveToken) {
                        return lastMoveToken;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        for (int i = 0; i < COLUMNS - 3; i++) {
            for (int j = 0; j < ROWS; j++) {
                try {
                    if (this.grid[i][j] == lastMoveToken && this.grid[i + 1][j] == lastMoveToken &&
                            this.grid[i + 2][j] == lastMoveToken && this.grid[i + 3][j] == lastMoveToken) {
                        return lastMoveToken;
                    }
                } catch (Exception e) {
                    continue;
                }

            }
        }
        for (int i = 3; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS - 3; j++) {
                try {
                    if (this.grid[i][j] == lastMoveToken && this.grid[i - 1][j + 1] == lastMoveToken &&
                            this.grid[i - 2][j + 2] == lastMoveToken && this.grid[i - 3][j + 3] == lastMoveToken) {
                        return lastMoveToken;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        for (int i = 3; i < COLUMNS; i++) {
            for (int j = 3; j < ROWS; j++) {
                try {
                    if (this.grid[i][j] == lastMoveToken && this.grid[i - 1][j - 1] == lastMoveToken &&
                            this.grid[i - 2][j - 2] == lastMoveToken && this.grid[i - 3][j - 3] == lastMoveToken) {
                        return lastMoveToken;
                    }
                } catch (Exception e) {
                    continue;
                }

            }
        }
        return TOKEN_EMPTY;
    }
}
