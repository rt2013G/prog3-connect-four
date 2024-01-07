package connectfour.gui;

import connectfour.GameHandler;
import connectfour.database.Database;

public class Controller {
    private static LoginPage loginPage = new LoginPage();
    private static GamePage gamePage = new GamePage();
    private static LeaderboardPage leaderboardPage = new LeaderboardPage();

    private static final Controller Instance = new Controller();

    public static Controller getInstance() {
        return Instance;
    }

    private Controller() {
        loginPage.setVisible(false);
        gamePage.setVisible(false);
        leaderboardPage.setVisible(false);
    }

    public void initView() {
        loginPage.setVisible(true);
    }

    public void switchLoginToGame() {
        loginPage.setVisible(false);
        gamePage.setVisible(true);
    }

    public void checkWinnerAndSwitchToLeaderboardIfGameOver() {
        if(GameHandler.getInstance().getGrid().checkWinner()) {
            GameHandler.getInstance().getGrid().printWinnerAndUpdateUserWins();
            Database db = new Database();
            db.updateUser(GameHandler.getInstance().getCurrentUser());
            Controller.getInstance().switchGameToLeaderboard();
        }
    }

    public void switchGameToLeaderboard() {
        gamePage.setVisible(false);
        leaderboardPage.updateLeaderboard();
        leaderboardPage.setVisible(true);
    }

    public void updateGameView() {
        gamePage.updateBoardVisual();
    }
}
