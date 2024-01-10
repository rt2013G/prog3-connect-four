package connectfour.gui;

import connectfour.GameHandler;
import connectfour.database.Database;

/**
 * Handles GUI functionalities without exposing the underlying frames
 * Implements the Singleton pattern as there should always be only one set of frames
 *
 * @author Raffaele Talente
 * @see "Design Patterns, GoF, 1994"
 */
public class Controller {
    private static final LoginPage loginPage = new LoginPage();
    private static final GamePage gamePage = new GamePage();
    private static final LeaderboardPage leaderboardPage = new LeaderboardPage();
    private static final Controller Instance = new Controller();

    public static Controller getInstance() {
        return Instance;
    }

    private Controller() {
    }

    /**
     * At the start of the application only the login page should be visible
     */
    public void initView() {
        loginPage.setVisible(true);
    }

    /**
     * Switches from the login page to the game page
     */
    public void switchLoginToGame() {
        loginPage.setVisible(false);
        gamePage.setVisible(true);
    }

    /**
     * Calls the game handler to check if there's a winner.
     * If there's a winner, calls the game handler to update the current user, sets the leaderboard page title
     * appropriately and switches the visible page to the leaderboard
     */
    public void checkWinnerAndSwitchToLeaderboardIfGameOver() {
        if(GameHandler.getInstance().checkWinner()) {
            GameHandler.getInstance().checkWinnerAndUpdateUserWins();
            leaderboardPage.setTitle(GameHandler.getInstance().getWinnerString());
            switchGameToLeaderboard();
        }
    }

    public void switchGameToLeaderboard() {
        gamePage.setVisible(false);
        leaderboardPage.updateLeaderboard();
        leaderboardPage.setVisible(true);
    }

    /**
     * Updates the game page visualization of the board
     */
    public void updateGameView() {
        gamePage.updateBoardVisual();
    }
}
