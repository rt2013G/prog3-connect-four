package connectfour;

import connectfour.components.ConnectFourGrid;
import connectfour.computer.ComputerStrategy;
import connectfour.computer.NeutralStrategy;
import connectfour.database.Database;
import connectfour.database.EmptyUser;
import connectfour.database.User;
import connectfour.gui.Controller;

/**
 * Defines a set of behaviors that lets clients interact with a specific game without exposing the underlying grid.
 * Implements the Singleton pattern so that there's only one instance of it, while providing a unique access
 * point to its methods.
 *
 * @author Raffaele Talente
 * @see ConnectFourGrid
 * @see "Design Patterns, GoF, 1994"
 */
public class GameHandler {
    private static final GameHandler Instance = new GameHandler();
    private ConnectFourGrid connectFourGrid = new ConnectFourGrid();
    private ComputerStrategy computerStrategy = new NeutralStrategy();
    private User currentUser;

    private GameHandler() {
    }

    public static GameHandler getInstance() {
        return Instance;
    }

    /**
     * Queries the database for a user, if a maching user row exists already, its data is retrieved from the database
     * and returned as a User object, then the user is logged in.
     * If it doesn't exist, it creates a new User and inserts it into the database
     *
     * @param name Name of the user
     * @param surname Surname of the user
     * @see #loginUser(User)
     * @see User
     * @see Database#getUserOrEmptyUser(String, String)
     * @see Database#insertUserIntoDatabase(User)
     */
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

    /**
     * Allows access to the current logged user
     *
     * @return The current user logged into the application
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Logs in a user object
     *
     * @param user A user object representing a user in the database
     */
    private void loginUser(User user) {
        this.currentUser = user;
    }

    /**
     * Checks if it's the computer turn to add a token, without exposing the grid method
     *
     * @return True if it's computer turn, False if it isn't
     * @see ConnectFourGrid#isPlayerTurn()
     */
    public boolean isComputerTurn() {
        return !(connectFourGrid.isPlayerTurn());
    }

    /**
     * Checks if a specific position in the grid contains a player token
     *
     * @param row The row in the grid to check
     * @param col The column in the grid to check
     * @return True if there's a player token in the specified position, False otherwise
     * @see ConnectFourGrid#TOKEN_PLAYER_SYMBOL
     */
    public boolean isPlayerToken(int row, int col) {
        return connectFourGrid.getGridState()[row][col] == connectFourGrid.TOKEN_PLAYER_SYMBOL;
    }

    /**
     * Checks if a specific position in the grid contains a computer token
     *
     * @param row The row in the grid to check
     * @param col The column in the grid to check
     * @return True if there's a computer token in the specified position, False otherwise
     * @see ConnectFourGrid#TOKEN_COMPUTER_SYMBOL
     */
    public boolean isComputerToken(int row, int col) {
        return connectFourGrid.getGridState()[row][col] == connectFourGrid.TOKEN_COMPUTER_SYMBOL;
    }

    /**
     * Inserts the token in the specified column and updates the current turn to that of the computer
     *
     * @param column The column to insert the token into
     * @see ConnectFourGrid#insertTokenIfColumnValid(char, int)
     */
    public void makePlayerMoveAndUpdateCurrentTurn(int column) {
        connectFourGrid.insertTokenIfColumnValid(connectFourGrid.TOKEN_PLAYER_SYMBOL, column);
        connectFourGrid.setPlayerTurn(false);
    }

    /**
     * Sets the computer strategy (also known as "gamemode") of the current game
     *
     * @param computerStrategy The concrete strategy to use
     * @see ComputerStrategy
     */
    public void setComputerStrategy(ComputerStrategy computerStrategy) {
        this.computerStrategy = computerStrategy;
    }

    /**
     * Uses the current set strategy to obtain a move from the computer, calls the method to insert a computer token
     * in the obtained column and sets the current turn to that of the player's
     *
     * @see ComputerStrategy#computerMoveColumn(ConnectFourGrid)
     * @see ConnectFourGrid#insertTokenIfColumnValid(char, int)
     */
    public void makeComputerMoveAndUpdateCurrentTurn() {
        int column = this.computerStrategy.computerMoveColumn(this.connectFourGrid);
        this.connectFourGrid.insertTokenIfColumnValid(connectFourGrid.TOKEN_COMPUTER_SYMBOL, column);
        this.connectFourGrid.setPlayerTurn(true);
    }

    /**
     * Checks if there's a winner and if said winner is the player, if that's the case updates the win count
     * of the current logged player
     *
     * @see User#addWin()
     */
    public void checkWinnerAndUpdateUserWins() {
        if(checkWinner()) {
            if(connectFourGrid.getWinnerSymbol() == connectFourGrid.TOKEN_PLAYER_SYMBOL) {
                currentUser.addWin();
                Database db = new Database();
                db.updateUser(currentUser);
            }
        }
    }

    /**
     * Checks the winner of the current position without exposing the grid method
     *
     * @return True if there's a winner, False otherwise
     * @see ConnectFourGrid#getWinnerSymbol()
     */
    public boolean checkWinner() {
        return connectFourGrid.getWinnerSymbol() != connectFourGrid.TOKEN_EMPTY_SYMBOL;
    }

    /**
     * Converts the winner of the current position into a readable String
     *
     * @return The appropriate String based on the evaluated grid position
     */
    public String getWinnerString() {
        if(connectFourGrid.getWinnerSymbol() == connectFourGrid.TOKEN_PLAYER_SYMBOL) {
            return "You win!";
        } else if(connectFourGrid.getWinnerSymbol() == connectFourGrid.TOKEN_COMPUTER_SYMBOL) {
            return "Computer wins!";
        } else {
            return "Draw!";
        }
    }

    /**
     * Initializes the game by creating the necessary tables in the database, then loads a saved game and
     * makes the controller update the game view
     *
     * @see GameLoader#parseGameFile()
     * @see Database#initTables()
     * @see Controller#updateGameView()
     */
    public void initGame() {
        Database db = new Database();
        db.initTables();
        GameLoader loader = new GameLoader();
        this.connectFourGrid = loader.parseGameFile();
        Controller.getInstance().updateGameView();
    }

    /**
     * Updates the current user info in the database, calls the loader to save the grid into the game file
     * and then exits
     *
     * @see GameLoader#saveGridToGameFile(ConnectFourGrid)
     */
    public void saveAndQuit() {
        (new Database()).updateUser(currentUser);
        GameLoader loader = new GameLoader();
        loader.saveGridToGameFile(connectFourGrid);
        System.exit(0);
    }

    /**
     * Updates the current user in the database, calls the loader to delete the game file and then exits
     *
     * @see GameLoader#deleteGameFile()
     */
    public void exitWithoutSavingGame() {
        (new Database()).updateUser(currentUser);
        GameLoader loader = new GameLoader();
        loader.deleteGameFile();
        System.exit(0);
    }
}
