package connectfour.gui;

import connectfour.ConnectFourGameHandler;
import connectfour.auth.Authenticator;
import connectfour.database.Database;

import javax.naming.ldap.Control;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GamePage extends JFrame {
    private final int WIDTH = 650;
    private final int HEIGHT = 800;

    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private JPanel mainPanel;
    private JPanel boardPanel;
    private JPanel inputPanel;
    private JLabel InputPrompt;
    private JButton saveAndQuitButton;

    private Container container;

    private JButton[][] connectFourBoardGridButtons = new JButton[ROWS][COLUMNS];

    public GamePage() {
        init();
    }

    private void init() {
        setContentPane(mainPanel);
        fillBoard();
        setTitle("Let's play!");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        addListeners();
    }

    private void fillBoard() {
        container = boardPanel;
        container.setLayout(new GridLayout(ROWS, COLUMNS));
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                connectFourBoardGridButtons[i][j] = new JButton();
                connectFourBoardGridButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(ConnectFourGameHandler.getInstance().getGrid().checkWinner()) {
                            ConnectFourGameHandler.getInstance().getGrid().printWinnerAndUpdateUserWins();
                            Database db = new Database();
                            db.updateUser(Authenticator.getInstance().getCurrentUser());
                            Controller.getInstance().switchGameToLeaderboard();
                        }

                        if(ConnectFourGameHandler.getInstance().getGrid().isComputerTurn()) {
                            ConnectFourGameHandler.getInstance().makeComputerMove();
                            Controller.getInstance().updateGameView();
                            ConnectFourGameHandler.getInstance().getGrid().printBoard();
                            return;
                        }
                        JButton source = (JButton) e.getSource();
                        for(int i = 0; i < ROWS; i++) {
                            for(int j = 0; j < COLUMNS; j++) {
                                if(source == connectFourBoardGridButtons[i][j]) {
                                    insertVisualToken(j);
                                    updateBoardVisual();
                                    return;
                                }
                            }
                        }
                    }
                });
                connectFourBoardGridButtons[i][j].setBorder(new LineBorder(Color.BLACK));
                container.add(connectFourBoardGridButtons[i][j]);
            }
        }
    }

    private void insertVisualToken(int j) {
        ConnectFourGameHandler.getInstance().getGrid().insertToken(
                ConnectFourGameHandler.getInstance().getGrid().TOKEN_PLAYER_SYMBOL, j);
        ConnectFourGameHandler.getInstance().getGrid().setPlayerTurn(false);
    }

    public void updateBoardVisual() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                if(ConnectFourGameHandler.getInstance().getGrid().isPlayerToken(i, j)) {
                    connectFourBoardGridButtons[i][j].setBackground(Color.RED);
                }
                if(ConnectFourGameHandler.getInstance().getGrid().isComputerToken(i, j)) {
                    connectFourBoardGridButtons[i][j].setBackground(Color.YELLOW);
                }
            }
        }
    }

    public void addListeners() {
        this.saveAndQuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectFourGameHandler.getInstance().saveAndQuit();
            }
        });
    }
}
