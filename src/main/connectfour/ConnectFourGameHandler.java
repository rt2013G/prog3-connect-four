package connectfour;

import connectfour.auth.Authenticator;
import connectfour.components.ConnectFourGrid;
import connectfour.computer.ComputerStrategy;
import connectfour.computer.NeutralStrategy;
import connectfour.database.Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConnectFourGameHandler {
    public static ConnectFourGameHandler getInstance() {
        return Instance;
    }

    public ConnectFourGrid getGrid() {
        return this.connectFourGrid;
    }

    public void setGrid(ConnectFourGrid connectFourGrid) {
        this.connectFourGrid = connectFourGrid;
    }

    public void setComputerStrategy(ComputerStrategy computerStrategy) {
        this.computerStrategy = computerStrategy;
    }

    public void makeComputerMove() {
        int column = this.computerStrategy.computerMoveColumn(this.connectFourGrid);
        this.connectFourGrid.insertToken(connectFourGrid.TOKEN_COMPUTER_SYMBOL, column);
    }
    private static final ConnectFourGameHandler Instance = new ConnectFourGameHandler();
    private ConnectFourGrid connectFourGrid = new ConnectFourGrid();
    private ComputerStrategy computerStrategy = new NeutralStrategy();
    private ConnectFourGameHandler() {}

    public void loadGame() {
        File f = new File("last_game.txt");
        Database db = new Database();
        try {
            if(!f.createNewFile()) {
                // the file already existed
                Scanner s = new Scanner(f);
                String encodedGridState = s.nextLine();
                String turnSymbol = s.nextLine();
                this.connectFourGrid.setGridState(db.decodeGridState(encodedGridState));
                this.connectFourGrid.setLastPlayedMoveSymbol(turnSymbol.toCharArray()[0]);
                f.delete();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveAndQuit() {
        Database db = new Database();
        db.updateUser(Authenticator.getInstance().getCurrentUser());
        String encodedString = db.encodeGridState(connectFourGrid);
        char lastPlayedMove = connectFourGrid.getLastPlayedMoveSymbol();
        File f = new File("last_game.txt");
        try {
            f.createNewFile();
            FileWriter writer = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(encodedString);
            bufferedWriter.newLine();
            bufferedWriter.write(lastPlayedMove);
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }

    public void exitWithoutSavingGame() {
        File f = new File("last_game.txt");
        Database db = new Database();
        db.updateUser(Authenticator.getInstance().getCurrentUser());
        try {
            f.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        db.printTopUsers();
        System.exit(0);
    }
}
