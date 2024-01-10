package connectfour.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates access to the database so that other classes can handle storage of users easily.
 * Uses SQLite as it is a very small local database.
 *
 * @author Raffaele Talente
 */
public class Database {
    private Connection connection = null;
    private final String protocol = "jdbc:sqlite:";
    private final String databaseName = "app.db";

    /**
     * Creates the user table if it doesn't exist, catches any SQL exceptions directly in the method
     */
    public void initTables() {
        try {
            connection = DriverManager.getConnection(protocol + databaseName);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("create table if not exists users (name string, " +
                                                            "surname string, " +
                                                            "wins integer default 0," +
                                                            "primary key(name, surname))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Closes a connection by handling the try/catch statement
     *
     * @param connection The connection to close
     */
    private void closeConnection(Connection connection) {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param name The name of the user
     * @param surname The surname of the user
     * @return The database user mapped from name and surname if it exists, otherwise an EmptyUser
     * @see User
     * @see EmptyUser
     */
    public User getUserOrEmptyUser(String name, String surname) {
        try {
            connection = DriverManager.getConnection(protocol + databaseName);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name=? and surname=?;");
            statement.setString(1, name);
            statement.setString(2, surname);
            ResultSet rs = statement.executeQuery();
            if(rs.isBeforeFirst()) {
                return new User(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("wins"));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return new EmptyUser(name, surname);
    }

    /**
     * Insert the User object into the database
     *
     * @param user The user to insert
     * @see User
     */
    public void insertUserIntoDatabase(User user) {
        try {
            connection = DriverManager.getConnection(protocol + databaseName);
            String sql = "INSERT INTO users (name, surname, wins)"
                    + " VALUES(?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setInt(3, user.getNumberOfWins());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Updates a User object in the database
     *
     * @param user the user to update
     * @see User
     */
    public void updateUser(User user) {
        try {
            connection = DriverManager.getConnection(protocol + databaseName);
            String sql = "UPDATE users SET wins=? WHERE name=? AND surname=?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getNumberOfWins());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Queries the database for a list of users up to a certain number
     *
     * @param value The number of users to return
     * @return A list of Users fetched from the database
     */
    public List<User> getTopUsersUpToValue(int value) {
        ArrayList<User> users = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(protocol + databaseName);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users ORDER BY wins desc");
            int count = 0;
            while(rs.next()) {
                User newUser = new User(rs.getString("name"),
                                        rs.getString("surname"),
                                        Integer.parseInt(rs.getString("wins")));
                users.add(newUser);
                count++;
                if(count >= value) {
                    break;
                }
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return users;
    }
}
