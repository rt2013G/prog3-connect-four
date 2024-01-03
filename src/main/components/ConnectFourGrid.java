package src.main.components;

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
        if(!isMoveValid(column)) {
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

    public boolean isMoveValid(int column) {
        return this.gridState[0][column] == TOKEN_EMPTY_SYMBOL;
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

    private boolean checkWinner() {
        return getWinnerSymbolOrEmptySymbol() != TOKEN_EMPTY_SYMBOL;
    }

    private boolean isGridCellEmpty(int row, int col) {
        return this.gridState[row][col] == TOKEN_EMPTY_SYMBOL;
    }

    private char getWinnerSymbolOrEmptySymbol() {
        for (int j = 0; j < ROWS - 3; j++) {
            for (int i = 0; i < COLUMNS; i++) {
                try {
                    if (this.gridState[i][j] == lastPlayedMoveSymbol &&
                        this.gridState[i][j + 1] == lastPlayedMoveSymbol &&
                        this.gridState[i][j + 2] == lastPlayedMoveSymbol &&
                        this.gridState[i][j + 3] == lastPlayedMoveSymbol) {
                        return lastPlayedMoveSymbol;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        for (int i = 0; i < COLUMNS - 3; i++) {
            for (int j = 0; j < ROWS; j++) {
                try {
                    if (this.gridState[i][j] == lastPlayedMoveSymbol &&
                        this.gridState[i + 1][j] == lastPlayedMoveSymbol &&
                        this.gridState[i + 2][j] == lastPlayedMoveSymbol &&
                        this.gridState[i + 3][j] == lastPlayedMoveSymbol) {
                        return lastPlayedMoveSymbol;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        for (int i = 3; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS - 3; j++) {
                try {
                    if (this.gridState[i][j] == lastPlayedMoveSymbol &&
                        this.gridState[i - 1][j + 1] == lastPlayedMoveSymbol &&
                        this.gridState[i - 2][j + 2] == lastPlayedMoveSymbol &&
                        this.gridState[i - 3][j + 3] == lastPlayedMoveSymbol) {
                        return lastPlayedMoveSymbol;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        for (int i = 3; i < COLUMNS; i++) {
            for (int j = 3; j < ROWS; j++) {
                try {
                    if (this.gridState[i][j] == lastPlayedMoveSymbol &&
                        this.gridState[i - 1][j - 1] == lastPlayedMoveSymbol &&
                        this.gridState[i - 2][j - 2] == lastPlayedMoveSymbol &&
                        this.gridState[i - 3][j - 3] == lastPlayedMoveSymbol) {
                        return lastPlayedMoveSymbol;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return TOKEN_EMPTY_SYMBOL;
    }
}
