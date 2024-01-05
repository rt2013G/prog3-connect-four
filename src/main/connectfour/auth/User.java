package connectfour.auth;

public class User {
    private String name;
    private String surname;
    private int numberOfWins;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.numberOfWins = 0;
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
