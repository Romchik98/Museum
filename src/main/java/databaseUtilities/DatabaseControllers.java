package databaseUtilities;

import controllers.LoginControl;
import ds.Exhibit;
import ds.User;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseControllers {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    public DatabaseControllers(String dbUrl, String username, String password) {
        try {
            // Establish a database connection
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            // Handle any exceptions that may occur during connection setup
            e.printStackTrace();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Users//
////////////////////////////////////////////////////////////////////////////////////////////////////
    public static User validateLogin(String login, String password) throws SQLException{

        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT * FROM user WHERE login = '" + login + "' AND password = '" + password + "'";
        ResultSet rs = statement.executeQuery(query);
        String userName = "null";
        String userSurname = "null";
        String userPosition = "null";
        String email = "null";
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
            userName = rs.getString("person_name");
            userSurname = rs.getString("person_surname");
            userPosition = rs.getString("person_position");
            email = rs.getString("person_email");
        }
        DatabaseConnection.disconnectFromDb(connection, statement);

        User user = new User(id, userName, userSurname, email, userPosition);
        return user;
    }

    public static void createUser(User user) {
        try {
            connection = DatabaseConnection.connectToDb();
            String insertString = "INSERT INTO user(`login`, `password`, `person_name`, `person_surname`) VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.execute();
            DatabaseConnection.disconnectFromDb(connection, preparedStatement);
            LoginControl.alertMessage("User created");
        } catch (Exception e) {
            LoginControl.alertMessage("User not created" + e);
        }
    }

    public static void editUser(String loginName, String password, String name, String surname, String email, int id) {
        try {
            connection = DatabaseConnection.connectToDb();
            String insertString = "UPDATE user SET login = '" + loginName + "', password = '" + password + "', person_name = '" + name + "', person_surname = '" + surname + "' where id = '" + id + "'";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.execute();
            DatabaseConnection.disconnectFromDb(connection, preparedStatement);
            LoginControl.alertMessage("User details updated");
        } catch (Exception e) {
            LoginControl.alertMessage("Error updating user" + e);
        }
    }

    public static ArrayList<User> getUsers() throws SQLException {//////not done
        ArrayList<User> users = new ArrayList<>();
        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();
        String query1 = "SELECT * FROM user";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            users.add(new User(rs1.getString("login"), rs1.getInt(1), rs1.getString("person_name"), rs1.getString("person_surname"), rs1.getString("person_email")));
        }
        DatabaseConnection.disconnectFromDb(connection, statement);
        return users;
    }

    public static User getUser(int userId) throws SQLException{//webui
        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT * FROM user WHERE id = '" + userId + "'";
        ResultSet rs = statement.executeQuery(query);
        String userName = "null";
        String userSurname = "null";
        String userPosition = "null";
        String email = "null";
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
            userName = rs.getString("person_name");
            userSurname = rs.getString("person_surname");
            userPosition = rs.getString("person_position");
            email = rs.getString("person_email");
        }
        DatabaseConnection.disconnectFromDb(connection, statement);

        User user = new User(id, userName, userSurname, email, userPosition);
        return user;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
    //Exhibit//
////////////////////////////////////////////////////////////////////////////////////////////////////


    // Add a new exhibit to the database
    public void addExhibitToDatabase(Exhibit exhibit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO exhibits (name, description, year) VALUES (?, ?, ?)"
            );
            statement.setString(1, exhibit.getName());
            statement.setString(2, exhibit.getDescription());
            statement.setInt(3, exhibit.getYear());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle any exceptions that may occur during database operation
            e.printStackTrace();
        }
    }

    // Update an existing exhibit in the database
    public void updateExhibitInDatabase(Exhibit exhibit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE exhibits SET name = ?, description = ?, year = ? WHERE id = ?"
            );
            statement.setString(1, exhibit.getName());
            statement.setString(2, exhibit.getDescription());
            statement.setInt(3, exhibit.getYear());
            statement.setInt(4, exhibit.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle any exceptions that may occur during database operation
            e.printStackTrace();
        }
    }

    // Delete an exhibit from the database
    public void deleteExhibitFromDatabase(Exhibit exhibit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM exhibits WHERE id = ?"
            );
            statement.setInt(1, exhibit.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle any exceptions that may occur during database operation
            e.printStackTrace();
        }
    }

    // Retrieve all exhibits from the database
    public ArrayList<Exhibit> getAllExhibitsFromDatabase() {
        ArrayList<Exhibit> exhibits = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM exhibits"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int year = resultSet.getInt("year");
                Exhibit exhibit = new Exhibit(id, name, description, year);
                exhibits.add(exhibit);
            }
        } catch (SQLException e) {
            // Handle any exceptions that may occur during database operation
            e.printStackTrace();
        }
        return exhibits;
    }

    // Search for exhibits in the database by name
    public ArrayList<Exhibit> searchExhibitsByNameInDatabase(String name) {
        ArrayList<Exhibit> exhibits = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM exhibits WHERE name LIKE ?"
            );
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String exhibitName = resultSet.getString("name");
                String description = resultSet.getString("description");
                int year = resultSet.getInt("year");
                Exhibit exhibit = new Exhibit(id, exhibitName, description, year);
                exhibits.add(exhibit);
            }
        } catch (SQLException e) {
            // Handle any exceptions that may occur during database operation
            e.printStackTrace();
        }
        return exhibits;
    }

}