package connectfour.utils;

import connectfour.components.ConnectFourGrid;

/**
 * Exposes methods to decode and encode a String into a grid state (stored as a char[][])
 * and vice-versa
 *
 * @author Raffaele Talente
 */
public class GridCodec {
    /**
     * Uses a StringBuilder to encode a char[][] into a String
     *
     * @param grid The grid to encode
     * @return The encoded String
     */
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

    /**
     * Decodes a String representation of a grid into the actual representation stored as a char[][]
     *
     * @param encodedString The String to decode
     * @return The grid state represented by a char[][]
     */
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
