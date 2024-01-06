package connectfour.gui;

import javax.swing.*;

public class LoginPage extends JFrame {
    public static void main(String... args) {
        GamePage page = new GamePage();
    }

    public final int WIDTH = 400;
    public final int HEIGHT = 600;
    public LoginPage() {
        init();
    }
    private JPanel globalPanel;
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField surnameField;
    private JRadioButton defenseRadioButton;
    private JRadioButton attackRadioButton;
    private JRadioButton neutralRadioButton;
    private JPanel radioButtonPanel;
    private JPanel radioButtonSubPanel;
    private JLabel warningLabel;
    private JButton playButton;

    private void init() {
        setContentPane(globalPanel);
        setTitle("Welcome to Connect Four");
        setSize(WIDTH, HEIGHT);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        neutralRadioButton.setSelected(true);
        setVisible(true);
    }
}
