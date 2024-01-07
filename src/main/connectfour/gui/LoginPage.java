package connectfour.gui;

import connectfour.GameHandler;
import connectfour.computer.AttackStrategy;
import connectfour.computer.ComputerStrategy;
import connectfour.computer.DefenseStrategy;
import connectfour.computer.NeutralStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    public final int WIDTH = 400;
    public final int HEIGHT = 600;
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

    public LoginPage() {
        init();
    }

    private void init() {
        setContentPane(globalPanel);
        setTitle("Welcome to Connect Four");
        setSize(WIDTH, HEIGHT);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        neutralRadioButton.setSelected(true);
        warningLabel.setForeground(Color.RED);
        setVisible(true);
        setResizable(false);
        addListeners();
    }

    private void addListeners() {
        this.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameField.getText().isEmpty() || surnameField.getText().isEmpty()) {
                    warningLabel.setText("Please insert a name and a surname");
                    return;
                }
                GameHandler.getInstance().authenticate(nameField.getText(), surnameField.getText());
                GameHandler.getInstance().setComputerStrategy(getComputerStrategyFromRadioButtons());
                Controller.getInstance().switchLoginToGame();
            }
        });
    }

    private ComputerStrategy getComputerStrategyFromRadioButtons() {
        if(neutralRadioButton.isSelected()) {
            return new NeutralStrategy();
        } else if(attackRadioButton.isSelected()) {
            return new AttackStrategy();
        } else if(defenseRadioButton.isSelected()) {
            return new DefenseStrategy();
        }
        return new NeutralStrategy();
    }
}
