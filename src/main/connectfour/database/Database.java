package connectfour.database;

import connectfour.components.ConnectFourGrid;

import javax.xml.transform.Result;
import java.sql.*;

public class Database {
    private Connection connection = null;
    private final String protocol = "jdbc:sqlite:";

    public Database() {
    }

    public void initTables() {
        try {
            connection = DriverManager.getConnection(protocol + "app.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("create table users (name string, " +
                                                            "surname string, " +
                                                            "wins integer default 0," +
                                                            "primary key(name, surname))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User getUserOrNull(String name, String surname) {
        try {
            connection = DriverManager.getConnection(protocol + "app.db");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name=? and surname=?;");
            statement.setString(1, name);
            statement.setString(2, surname);
            ResultSet rs = statement.executeQuery();
            User dbUser = new User(rs.getString("name"),
                                   rs.getString("surname"),
                                   rs.getInt("wins"));
            connection.close();
            return dbUser;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public void insertUserIntoDatabase(User user) {
        try {
            connection = DriverManager.getConnection(protocol + "app.db");
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

    public void updateUser(User user) {
        try {
            connection = DriverManager.getConnection(protocol + "app.db");
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

    public void printTopUsers() {
        try {
            connection = DriverManager.getConnection(protocol + "app.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users ORDER BY wins desc");
            while(rs.next()) {
                System.out.println("name = " + rs.getString("name"));
                System.out.println("surname = " + rs.getString("surname"));
                System.out.println("wins = " + rs.getString("wins"));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    public String encodeGridState(ConnectFourGrid grid) {
        char[][] gridState = grid.getGridState();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < grid.ROWS; i++) {
            for(int j = 0; j < grid.COLUMNS; j++) {
                sb.append(gridState[i][j]);
            }
        }
        return sb.toString();
    }

    public char[][] decodeGridState(String encodedString) {
        ConnectFourGrid grid = new ConnectFourGrid();
        int rows = grid.ROWS;
        int cols = grid.COLUMNS;
        char[][] gridState = new char[rows][cols];
        char[] encodedStringArray = encodedString.toCharArray();
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                gridState[i][j] = encodedStringArray[i * cols + j];
            }
        }
        return gridState;
    }
}
