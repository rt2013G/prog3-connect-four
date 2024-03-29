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

/**
 * The login page allows the user to insert their name and surname and select a gamemode.
 *
 * @author Raffaele Talente
 */
public class LoginPage extends PageTemplate {
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

    public void createPage() {
        setContentPane(globalPanel);
        setTitle("Welcome to Connect Four");
        setSize(WIDTH, HEIGHT);
        pack();
    }

    public void fillPage() {
        neutralRadioButton.setSelected(true);
        warningLabel.setForeground(Color.RED);
    }

    public void addListeners() {
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

    /**
     * @return The appropriate chosen computer strategy based on the radio buttons
     */
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
