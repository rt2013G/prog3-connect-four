package connectfour.gui;

import javax.swing.*;

/**
 * Implements the Template Method pattern to create a defined method for creating pages
 *
 * @author Raffaele Talente
 * @see "Design Patterns, GoF, 1994"
 */
public abstract class PageTemplate extends JFrame {
    /**
     * The template method
     */
    public final void init() {
        createPage();
        fillPage();
        setLookAndFeel();
        addListeners();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * Part of the template method that defines how each page should create iself
     */
    protected abstract void createPage();

    /**
     * Part of the template method that defines how each page should fill its contents
     */
    protected void fillPage() {
    }

    /**
     * Sets the look and feel of each page to be the same
     */
    final void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Part of the template method where each page adds the necessary listeners
     */
    protected abstract void addListeners();
}
