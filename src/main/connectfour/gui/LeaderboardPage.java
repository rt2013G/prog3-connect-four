package connectfour.gui;

import connectfour.GameHandler;
import connectfour.GameLoader;
import connectfour.database.Database;
import connectfour.database.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The leaderboard page displays the current leaderboard based on the number of wins and allows the user
 * to quit the game.
 *
 * @author Raffaele Talente
 */
public class LeaderboardPage extends PageTemplate {
    private final int WIDTH = 400;
    private final int HEIGHT = 600;
    private final int LEADERBOARD_LENGTH = 10;
    private JPanel mainPanel;
    private JButton quitButton;
    private JPanel leaderboardContent;
    private Container container;

    public LeaderboardPage() {
        init();
    }

    public void createPage() {
        setContentPane(mainPanel);
        setTitle("Thanks for playing!");
    }

    public void fillPage() {
        pack();
    }

    public void addListeners() {
        quitButton.addActionListener(e -> GameHandler.getInstance().exitWithoutSavingGame());
    }

    /**
     * Fills the main content panel with a list of users with the most wins retrieved from the database
     *
     * @see Database#getTopUsersUpToValue(int)
     */
    public void updateLeaderboard() {
        container = leaderboardContent;
        container.setLayout(new GridLayout(LEADERBOARD_LENGTH, 1));
        Database db = new Database();
        List<User> users = db.getTopUsersUpToValue(LEADERBOARD_LENGTH);
        for(User user : users) {
            JPanel panel = userEntry(user.getName(), user.getSurname(), user.getNumberOfWins());
            container.add(panel);
        }
        setSize(WIDTH, HEIGHT);
    }

    /**
     * Standardizes the creation of the JPanels that store the appropriate information of each of the top users
     *
     * @param name The name of the user
     * @param surname The surname of the user
     * @param wins The wins of the user
     * @return A JPanel displaying these parameters
     */
    private JPanel userEntry(String name, String surname, int wins) {
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel();
        JLabel surnameLabel = new JLabel();
        JLabel winsLabel = new JLabel();

        nameLabel.setText(name);
        surnameLabel.setText(surname);
        winsLabel.setText(String.valueOf(wins));

        panel.add(nameLabel);
        panel.add(surnameLabel);
        panel.add(winsLabel);
        return panel;
    }
}
