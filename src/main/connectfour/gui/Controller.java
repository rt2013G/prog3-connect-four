package connectfour.gui;

import connectfour.ConnectFourGameHandler;

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

    public void switchGameToLeaderboard() {
        gamePage.setVisible(false);
        leaderboardPage.updateLeaderboard();
        leaderboardPage.setVisible(true);
    }

    public void updateGameView() {
        gamePage.updateBoardVisual();
    }
}
