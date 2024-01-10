package connectfour;

import connectfour.components.ConnectFourGrid;
import connectfour.database.Database;
import connectfour.utils.GridCodec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameLoader {
    private final String GAME_FILE_NAME = "last_game.txt";

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

    public void saveGridToGameFile(ConnectFourGrid grid) {
        updateCurrentUserInDb();
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

    public void exitWithoutSavingGame() {
        updateCurrentUserInDb();
        File f = new File(GAME_FILE_NAME);
        f.delete();
        System.exit(0);
    }

    private void updateCurrentUserInDb() {
        Database db = new Database();
        db.updateUser(GameHandler.getInstance().getCurrentUser());
    }
}
