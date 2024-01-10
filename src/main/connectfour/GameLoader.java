package connectfour;

import connectfour.components.ConnectFourGrid;
import connectfour.utils.GridCodec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Exposes methods to load the game file and to store a grid into a file
 *
 * @author Raffaele Talente
 * @see GridCodec
 */
public class GameLoader {
    private final String GAME_FILE_NAME = "last_game.txt";

    /**
     * Parses the game file decoding it with the codec methods
     *
     * @return A new grid if the game file didn't exist, a decoded grid if it did
     * @see #GAME_FILE_NAME
     */
    public ConnectFourGrid parseGameFile() {
        File f = new File(GAME_FILE_NAME);
        ConnectFourGrid grid = new ConnectFourGrid();
        if(f.exists()) {
            try {
                Scanner s = new Scanner(f);
                String encodedGridState = s.nextLine();
                String turnSymbol = s.nextLine();

                GridCodec codec = new GridCodec();
                grid.setGridState(codec.decodeGridState(encodedGridState));
                grid.setLastPlayedMoveSymbol(turnSymbol.toCharArray()[0]);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Unexpected error");
            } finally {
                f.delete();
            }
        }
        return grid;
    }

    /**
     * Calls the codec to convert a ConnectFourGrid into two Strings representing the grid state, and then
     * stores the result into the game file
     *
     * @param grid The grid to encode and save into a file
     * @see #GAME_FILE_NAME
     */
    public void saveGridToGameFile(ConnectFourGrid grid) {
        GridCodec codec = new GridCodec();
        String encodedString = codec.encodeGridState(grid);
        char lastPlayedMove = grid.getLastPlayedMoveSymbol();
        File f = new File(GAME_FILE_NAME);
        try {
            boolean fileExisted = !(f.createNewFile());
            if(fileExisted) {
                System.err.println("Unexpected error while saving and quitting");
            }
            FileWriter writer = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(encodedString);
            bufferedWriter.newLine();
            bufferedWriter.write(lastPlayedMove);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes the game file if it exists
     *
     * @see #GAME_FILE_NAME
     */
    public void deleteGameFile() {
        File f = new File(GAME_FILE_NAME);
        if(f.exists()) {
            f.delete();
        }
    }
}
