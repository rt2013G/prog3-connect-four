package connectfour.gui;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        LoginPage loginPage = new LoginPage();
        setContentPane(loginPage);
    }
}
