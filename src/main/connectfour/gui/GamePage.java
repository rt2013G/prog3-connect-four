package connectfour.gui;

import connectfour.GameHandler;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The game page contains the visual representation of the actual game grid
 *
 * @author Raffaele Talente
 */
public class GamePage extends PageTemplate {
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

    public void createPage() {
        setContentPane(mainPanel);
        setTitle("Let's play!");
        setSize(WIDTH, HEIGHT);
    }

    public void fillPage() {
        fillBoard();
    }

    public void addListeners() {
        this.saveAndQuitButton.addActionListener(e -> GameHandler.getInstance().saveAndQuit());
    }

    /**
     * Fills the page with a grid of button, each button represents a token slot.
     * When any button is pressed, it checks for the current turn, if it's the computer then it makes a computer move,
     * otherwise inserts a player token in the column of that button.
     */
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
                            updateBoardVisual();
                            return;
                        }
                        JButton source = (JButton) e.getSource();
                        for(int i = 0; i < ROWS; i++) {
                            for(int j = 0; j < COLUMNS; j++) {
                                if(source == connectFourBoardGridButtons[i][j]) {
                                    GameHandler.getInstance().makePlayerMoveAndUpdateCurrentTurn(j);
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

    /**
     * Updates token slot depending on the token in it, player tokens are displayed in red, computer tokens
     * are displayed in yellow.
     *
     * @see #connectFourBoardGridButtons
     */
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
}
