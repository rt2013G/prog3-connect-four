package connectfour.gui;

import javax.swing.*;

public abstract class PageTemplate extends JFrame {
    public final void init() {
        createPage();
        fillPage();
        setLookAndFeel();
        addListeners();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    protected abstract void createPage();
    protected abstract void fillPage();
    final void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    protected abstract void addListeners();
}
