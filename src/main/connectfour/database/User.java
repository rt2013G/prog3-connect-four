package connectfour.database;

public class User {
    private final String name;
    private final String surname;
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
