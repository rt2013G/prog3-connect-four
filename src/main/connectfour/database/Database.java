package connectfour.database;

import connectfour.auth.User;

import java.sql.*;

public class Database {
    private Connection connection = null;
    private final String protocol = "jdbc:sqlite:";

    public Database() {
        initTables();
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

    public void insertUserIntoDatabase(User user) {
        try {
            connection = DriverManager.getConnection(protocol + "app.db");
            String sql = "insert into users (name, surname, wins)"
                    + " values(?, ?, ?)";
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

    public void printTopUsers() {
        try {
            connection = DriverManager.getConnection(protocol + "app.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users order by wins");
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
}
