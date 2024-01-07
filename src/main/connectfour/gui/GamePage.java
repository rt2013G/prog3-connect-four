package connectfour.gui;

import connectfour.GameHandler;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private final JButton[][] connectFourBoardGridButtons = new JButton[ROWS][COLUMNS];

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
                        Controller.getInstance().checkWinnerAndSwitchToLeaderboardIfGameOver();

                        if(GameHandler.getInstance().isComputerTurn()) {
                            GameHandler.getInstance().makeComputerMoveAndUpdateCurrentTurn();
                            Controller.getInstance().updateGameView();
                            return;
                        }
                        JButton source = (JButton) e.getSource();
                        for(int i = 0; i < ROWS; i++) {
                            for(int j = 0; j < COLUMNS; j++) {
                                if(source == connectFourBoardGridButtons[i][j]) {
                                    insertPlayerToken(j);
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

    private void insertPlayerToken(int j) {
        GameHandler.getInstance().makePlayerMoveAndUpdateCurrentTurn(j);
        updateBoardVisual();
    }

    public void updateBoardVisual() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                if(GameHandler.getInstance().isPlayerToken(i, j)) {
                    connectFourBoardGridButtons[i][j].setBackground(Color.RED);
                }
                if(GameHandler.getInstance().isComputerToken(i, j)) {
                    connectFourBoardGridButtons[i][j].setBackground(Color.YELLOW);
                }
            }
        }
    }

    public void addListeners() {
        this.saveAndQuitButton.addActionListener(e -> GameHandler.getInstance().saveAndQuit());
    }
}
