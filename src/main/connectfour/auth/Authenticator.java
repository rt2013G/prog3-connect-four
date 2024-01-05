package connectfour.auth;

import connectfour.ConnectFourGameHandler;
import connectfour.computer.AttackStrategy;
import connectfour.computer.ComputerStrategy;
import connectfour.computer.DefenseStrategy;
import connectfour.computer.NeutralStrategy;
import connectfour.database.Database;
import connectfour.database.User;

import java.util.Scanner;

public class Authenticator {
    private static final Authenticator Instance = new Authenticator();
    private User currentUser = null;
    private Authenticator() {}

    public static Authenticator getInstance() {
        return Instance;
    }

    public void loginUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void authenticate() {
        Scanner s = new Scanner(System.in);
        System.out.println("Do you want to (1) log-in or (2) create a new user?");
        int answer = Integer.parseInt(s.nextLine());
        if(answer == 1) {
            loginProcedure();
        } else if(answer == 2) {
            registerProcedure();
        } else {
            System.out.println("Unknown command");
            System.exit(1);
        }
        gamemodeProcedure();
    }

    private void loginProcedure() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your name");
        String name = s.nextLine();
        System.out.println("Please enter your surname");
        String surname = s.nextLine();
        Database db = new Database();
        User user = db.getUserOrNull(name, surname);
        if(user == null) {
            System.out.println("User not found");
            System.exit(1);
        }
        loginUser(user);
    }

    private void registerProcedure() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your name");
        String name = s.nextLine();
        System.out.println("Please enter your surname");
        String surname = s.nextLine();
        User newUser = new User(name, surname);
        Database db = new Database();
        db.insertUserIntoDatabase(newUser);
        loginUser(newUser);
    }

    private void gamemodeProcedure() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please choose your game mode: (1) Defense, (2) Attack, (3) Neutral");
        int answer = Integer.parseInt(s.nextLine());
        ComputerStrategy strategy;
        if(answer == 1) {
            strategy = new DefenseStrategy();
        } else if(answer == 2) {
            strategy = new AttackStrategy();
        } else {
            strategy = new NeutralStrategy();
        }
        ConnectFourGameHandler.getInstance().setComputerStrategy(strategy);
    }
}
