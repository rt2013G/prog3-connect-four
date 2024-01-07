package connectfour;

import connectfour.components.ConnectFourGrid;
import connectfour.computer.ComputerStrategy;
import connectfour.computer.NeutralStrategy;
import connectfour.database.Database;
import connectfour.database.EmptyUser;
import connectfour.database.User;
import connectfour.gui.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameHandler {
    private static final GameHandler Instance = new GameHandler();
    private final ConnectFourGrid connectFourGrid = new ConnectFourGrid();
    private ComputerStrategy computerStrategy = new NeutralStrategy();
    private User currentUser;
    private final String GAME_FILE_NAME = "last_game.txt";

    private GameHandler() {}

    public static GameHandler getInstance() {
        return Instance;
    }

    public void authenticate(String name, String surname) {
        Database db = new Database();
        User user = db.getUserOrEmptyUser(name, surname);
        if(user instanceof EmptyUser) {
            user = new User(name, surname);
            db.insertUserIntoDatabase(user);
            loginUser(user);
        } else {
            loginUser(user);
        }
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    private void loginUser(User user) {
        this.currentUser = user;
    }

    public boolean isComputerTurn() {
        return !(connectFourGrid.isPlayerTurn());
    }

    public boolean isPlayerToken(int row, int col) {
        return connectFourGrid.getGridState()[row][col] == connectFourGrid.TOKEN_PLAYER_SYMBOL;
    }

    public boolean isComputerToken(int row, int col) {
        return connectFourGrid.getGridState()[row][col] == connectFourGrid.TOKEN_COMPUTER_SYMBOL;
    }

    public void makePlayerMoveAndUpdateCurrentTurn(int column) {
        connectFourGrid.insertTokenIfColumnValid(connectFourGrid.TOKEN_PLAYER_SYMBOL, column);
        connectFourGrid.setPlayerTurn(false);
    }

    public void setComputerStrategy(ComputerStrategy computerStrategy) {
        this.computerStrategy = computerStrategy;
    }

    public void makeComputerMoveAndUpdateCurrentTurn() {
        int column = this.computerStrategy.computerMoveColumn(this.connectFourGrid);
        this.connectFourGrid.insertTokenIfColumnValid(connectFourGrid.TOKEN_COMPUTER_SYMBOL, column);
        this.connectFourGrid.setPlayerTurn(true);
    }

    public boolean checkWinner() {
        return connectFourGrid.getWinnerSymbol() != connectFourGrid.TOKEN_EMPTY_SYMBOL;
    }

    public void checkWinnerAndUpdateUserWins() {
        if(checkWinner()) {
            if(connectFourGrid.getWinnerSymbol() == connectFourGrid.TOKEN_PLAYER_SYMBOL) {
                currentUser.addWin();
                Database db = new Database();
                db.updateUser(currentUser);
            }
        }
    }

    public String getWinnerString() {
        if(connectFourGrid.getWinnerSymbol() == connectFourGrid.TOKEN_PLAYER_SYMBOL) {
            return "You win!";
        } else if(connectFourGrid.getWinnerSymbol() == connectFourGrid.TOKEN_COMPUTER_SYMBOL) {
            return "Computer wins!";
        } else {
            return "Draw!";
        }
    }

    public void loadLastGame() {
        File f = new File(GAME_FILE_NAME);
        if(f.exists()) {
            parseGameFile(f);
        }
        Controller.getInstance().updateGameView();
    }

    private void parseGameFile(File f) {
        try {
                Scanner s = new Scanner(f);
                String encodedGridState = s.nextLine();
                String turnSymbol = s.nextLine();
                this.connectFourGrid.setGridState(decodeGridState(encodedGridState));
                this.connectFourGrid.setLastPlayedMoveSymbol(turnSymbol.toCharArray()[0]);
                f.delete();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            f.delete();
        }
    }

    public void saveAndQuit() {
        updateCurrentUserInDb();
        String encodedString = encodeGridState(connectFourGrid);
        char lastPlayedMove = connectFourGrid.getLastPlayedMoveSymbol();
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
        System.exit(0);
    }

    private String encodeGridState(ConnectFourGrid grid) {
        char[][] gridState = grid.getGridState();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < grid.ROWS; i++) {
            for(int j = 0; j < grid.COLUMNS; j++) {
                sb.append(gridState[i][j]);
            }
        }
        return sb.toString();
    }

    private char[][] decodeGridState(String encodedString) {
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

    public void exitWithoutSavingGame() {
        updateCurrentUserInDb();
        File f = new File(GAME_FILE_NAME);
        f.delete();
        System.exit(0);
    }

    private void updateCurrentUserInDb() {
        Database db = new Database();
        db.updateUser(currentUser);
    }
}
