package connectfour.gui;

import connectfour.GameHandler;
import connectfour.database.Database;
import connectfour.database.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LeaderboardPage extends JFrame {
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

    private void init() {
        setContentPane(mainPanel);
        setTitle("Thanks for playing!");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        addListeners();
    }

    private void addListeners() {
        quitButton.addActionListener(e -> GameHandler.getInstance().exitWithoutSavingGame());
    }

    public void updateLeaderboard() {
        container = leaderboardContent;
        container.setLayout(new GridLayout(10, 1));
        Database db = new Database();
        List<User> users = db.getTopUsersUpToValue(LEADERBOARD_LENGTH);
        for(User user : users) {
            JPanel panel = userEntry(user.getName(), user.getSurname(), user.getNumberOfWins());
            container.add(panel);
        }
        setSize(WIDTH, HEIGHT);
    }

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
