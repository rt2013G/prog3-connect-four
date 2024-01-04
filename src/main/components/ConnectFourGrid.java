package src.main.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectFourGrid {
    public final int ROWS = 6;
    public final int COLUMNS = 7;
    public final char TOKEN_EMPTY_SYMBOL = ' ';
    public final char TOKEN_COMPUTER_SYMBOL = 'c';
    public final char TOKEN_PLAYER_SYMBOL = 'p';

    public ConnectFourGrid() {
        this.gridState = initGridState();
    }

    public ConnectFourGrid cloneSelf() {
        ConnectFourGrid newGrid = new ConnectFourGrid();
        newGrid.setGridState(this.cloneGridState());
        return newGrid;
    }

    public char[][] cloneGridState() {
        char[][] newGridState = new char[ROWS][COLUMNS];
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                newGridState[i][j] = this.gridState[i][j];
            }
        }
        return newGridState;
    }

    public void setGridState(char[][] newGrid) {
        this.gridState = newGrid;
    }

    public void insertToken(char tokenSymbol, int column) {
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

    public boolean isMoveInvalid(int column) {
        return this.gridState[0][column] != TOKEN_EMPTY_SYMBOL;
    }

    public void printBoard() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                System.out.print(gridState[i][j]);
                System.out.print("-");
            }
            System.out.println();
        }
    }

    public boolean checkWinner() {
        return getWinnerSymbolOrEmptySymbol() != TOKEN_EMPTY_SYMBOL;
    }

    public void printWinner() {
        if(!checkWinner()) {
            System.out.println("There's no winner after the last token inserted");
        } else {
            char winnerSymbol = getWinnerSymbolOrEmptySymbol();
            if(winnerSymbol == TOKEN_COMPUTER_SYMBOL) {
                System.out.println("Computer wins");
            } else if(winnerSymbol == TOKEN_PLAYER_SYMBOL) {
                System.out.println("PLayer wins");
            }
        }
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

    public void resetGridState() {
        this.gridState = initGridState();
    }

    public boolean isGridEmpty() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                if(this.gridState[i][j] != TOKEN_EMPTY_SYMBOL) {
                    return false;
                }
            }
        }
        return true;
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
                // column was empty
                return;
            }
            if(this.gridState[i][column] != TOKEN_EMPTY_SYMBOL) {
                this.gridState[i][column] = TOKEN_EMPTY_SYMBOL;
                return;
            }
        }
    }


    private char[][] gridState;
    private char lastPlayedMoveSymbol = TOKEN_EMPTY_SYMBOL;

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
