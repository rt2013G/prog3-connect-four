package connectfour.database;

/**
 * Represents users from the database as objects manageable in the program
 *
 * @author Raffaele Talente
 * @see Database
 */
public class User {
    protected final String name;
    protected final String surname;
    private int numberOfWins;

    public User(String name, String surname) {
        this.name = name.toLowerCase();
        this.surname = surname.toLowerCase();
        this.numberOfWins = 0;
    }

    public User(String name, String surname, int numberOfWins) {
        this.name = name.toLowerCase();
        this.surname = surname.toLowerCase();
        this.numberOfWins = numberOfWins;
    }

    public User(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.numberOfWins = user.getNumberOfWins();
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public int getNumberOfWins() {
        return this.numberOfWins;
    }

    public void addWin() {
        this.numberOfWins++;
    }
}
