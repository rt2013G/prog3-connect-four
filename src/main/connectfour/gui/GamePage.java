package connectfour.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Objects;

public class GamePage extends JFrame {
    private final int WIDTH = 650;
    private final int HEIGHT = 800;
    private JPanel mainPanel;
    private JPanel boardPanel;
    private JPanel inputPanel;
    private JLabel InputPrompt;
    private JButton saveAndQuitButton;

    private Container container;

    private JButton[][] connectFourBoardGridButtons = new JButton[6][7];

    public GamePage() {
        init();
    }

    private void init() {
        setContentPane(mainPanel);
        fillBoard();
        setTitle("Let's play!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setVisible(true);
    }

    private void fillBoard() {
        container = boardPanel;
        container.setLayout(new GridLayout(6, 7));
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                connectFourBoardGridButtons[i][j] = new JButton();
                connectFourBoardGridButtons[i][j].setBorder(new LineBorder(Color.BLACK));
                container.add(connectFourBoardGridButtons[i][j]);
            }
        }
    }
}
