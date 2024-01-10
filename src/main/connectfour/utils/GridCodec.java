package connectfour.utils;

import connectfour.components.ConnectFourGrid;

public class GridCodec {
    public String encodeGridState(ConnectFourGrid grid) {
        char[][] gridState = grid.getGridState();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < grid.ROWS; i++) {
            for(int j = 0; j < grid.COLUMNS; j++) {
                sb.append(gridState[i][j]);
            }
        }
        return sb.toString();
    }

    public char[][] decodeGridState(String encodedString) {
        ConnectFourGrid grid = new ConnectFourGrid();
        int rows = grid.ROWS;
        int cols = grid.COLUMNS;
        char[][] gridState = new char[rows][cols];
        char[] encodedStringArray = encodedString.toCharArray();
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                gridState[i][j] = encodedStringArray[i * cols + j];
            }
        }
        return gridState;
    }
}
