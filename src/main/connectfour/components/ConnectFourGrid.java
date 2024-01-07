package connectfour.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public char[][] getGridState() {
        return this.gridState;
    }

    public void setGridState(char[][] newGrid) {
        this.gridState = newGrid;
    }

    public void insertTokenIfColumnValid(char tokenSymbol, int column) {
        if(isMoveInvalid(column)) {
            return;
        }
        for(int i = 0; i < ROWS; i++) {
            if(i == ROWS - 1) {
                if(isGridCellEmpty(i, column)) {
                    gridState[i][column] = tokenSymbol;
                    this.lastPlayedMoveSymbol = tokenSymbol;
                }
            } else if(!isGridCellEmpty(i + 1, column) && isGridCellEmpty(i, column)) {
                gridState[i][column] = tokenSymbol;
                this.lastPlayedMoveSymbol = tokenSymbol;
            }
        }
    }

    public boolean isPlayerTurn() {
        return this.playerTurn;
    }

    public void setPlayerTurn(Boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isMoveInvalid(int column) {
        return this.gridState[0][column] != TOKEN_EMPTY_SYMBOL;
    }

    public char getWinnerSymbol() {
        return getWinnerSymbolOrEmptySymbol();
    }

    public char getLastPlayedMoveSymbol() {
        return this.lastPlayedMoveSymbol;
    }

    public void setLastPlayedMoveSymbol(char lastPlayedMoveSymbol) {
        this.lastPlayedMoveSymbol = lastPlayedMoveSymbol;
    }

    public int getGridEvaluation(char currentTurnSymbol) {
        char winner = getWinnerSymbolOrEmptySymbol();
        if(winner == TOKEN_EMPTY_SYMBOL) {
            return 0;
        }
        if(winner == currentTurnSymbol) {
            return 1;
        }
        return -1;
    }

    public boolean isGridFull() {
        for(int j = 0; j < COLUMNS; j++) {
            if(!isMoveInvalid(j)) {
                return false;
            }
        }
        return true;
    }

    public int getRandomColumnWithLeastTokens() {
        List<Integer> values = getColumnsWithLeastTokens();
        Random r = new Random();
        int index = r.nextInt(values.size());
        return values.get(index);
    }

    public void removeTopToken(int column) {
        for(int i = 0; i <= ROWS; i++) {
            if(i == 0 && this.gridState[i][column] != TOKEN_EMPTY_SYMBOL) {
                this.gridState[i][column] = TOKEN_EMPTY_SYMBOL;
                return;
            }
            if(i == ROWS) {
                return;
            }
            if(this.gridState[i][column] != TOKEN_EMPTY_SYMBOL) {
                this.gridState[i][column] = TOKEN_EMPTY_SYMBOL;
                return;
            }
        }
    }

    private char[][] initGridState() {
        char[][] grid = new char[ROWS][COLUMNS];
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                grid[i][j] = TOKEN_EMPTY_SYMBOL;
            }
        }
        return grid;
    }

    private boolean isGridCellEmpty(int row, int col) {
        return this.gridState[row][col] == TOKEN_EMPTY_SYMBOL;
    }

    private List<Integer> getColumnsWithLeastTokens() {
        List<Integer> values = new ArrayList<Integer>();
        int[] tokenCount = new int[COLUMNS];
        int minValue = Integer.MAX_VALUE;
        for(int j = 0; j < COLUMNS; j++) {
            int currentColumnCount = 0;
            for(int i = ROWS - 1; i >= 0; i--) {
                if(this.gridState[i][j] != TOKEN_EMPTY_SYMBOL) {
                    currentColumnCount++;
                }
            }
            tokenCount[j] = currentColumnCount;
            if(currentColumnCount < minValue) {
                minValue = currentColumnCount;
            }
        }
        for(int i = 0; i < COLUMNS; i++) {
            if(tokenCount[i] == minValue) {
                values.add(i);
            }
        }
        return values;
    }

    private char getWinnerSymbolOrEmptySymbol() {
        // horizontal
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
        // vertical
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
