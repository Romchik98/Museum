package com.museum.museum.databaseUtilities;

import com.museum.museum.controllers.LoginControl;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.Exhibit;
import com.museum.museum.ds.User;

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
        String userType = "null";
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
            userName = rs.getString("person_name");
            userSurname = rs.getString("person_surname");
            userType = rs.getString("person_type");
        }
        DatabaseConnection.disconnectFromDb(connection, statement);

        User user = new User(id, userName, userSurname, userType);
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
            preparedStatement.setString(4, user.getUserSurname());
            preparedStatement.execute();
            DatabaseConnection.disconnectFromDb(connection, preparedStatement);
            LoginControl.alertMessage("User created");
        } catch (Exception e) {
            LoginControl.alertMessage("User not created" + e);
        }
    }

    public static void editUser(String loginName, String password, String name, String surname, int id) {
        try {
            connection = DatabaseConnection.connectToDb();
            String insertString = "UPDATE user SET login = '" + loginName + "', password = '" + password + "', person_name = '" + name + "', person_surname = '" + surname + "' where person_id = '" + id + "'";
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
            users.add(new User(rs1.getString("login"), rs1.getInt(1), rs1.getString("person_name"), rs1.getString("person_surname")));
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
        String userType = "null";
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
            userName = rs.getString("person_name");
            userSurname = rs.getString("person_surname");
            userType = rs.getString("person_type");
        }
        DatabaseConnection.disconnectFromDb(connection, statement);

        User user = new User(id, userName, userSurname, userType);
        return user;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
    //Exhibit//
////////////////////////////////////////////////////////////////////////////////////////////////////


    public static ArrayList<Exhibit> getExhibits(int collectionId) throws SQLException {
        ArrayList<Exhibit> exhibits = new ArrayList<>();

        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();

        String query1 = "SELECT * FROM exhibit WHERE collection_id = '" + collectionId + "'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            exhibits.add(new Exhibit(rs1.getInt(1), rs1.getString("name"), rs1.getString("description"),
                    rs1.getDate("date_of_creation"), rs1.getDate("date_of_discovery"),
                    rs1.getInt("quantity"), rs1.getString("condition"), rs1.getInt("collection_id"),
                    rs1.getString("place_of_creation"), rs1.getString("place_of_discovery"), rs1.getString("dimensions"),
                    rs1.getString("materials"), rs1.getString("type"), rs1.getString("object"),
                    rs1.getString("licence")));
        }
        DatabaseConnection.disconnectFromDb(connection, statement);
        return exhibits;
    }

    // Add a new exhibit to the database
    /*public void addExhibitToDatabase(Exhibit exhibit) {
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
                    "UPDATE exhibit SET name = ?, description = ?, year = ? WHERE id = ?"
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
                    "DELETE FROM exhibit WHERE id = ?"
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
                    "SELECT * FROM exhibit"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date dateOfCreation = resultSet.getDate("dateOfCreation");
                Exhibit exhibit = new Exhibit(id, name, description, dateOfCreation, );
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
    }*/


////////////////////////////////////////////////////////////////////////////////////////////////////
    //Collections//
////////////////////////////////////////////////////////////////////////////////////////////////////

    /*public static ArrayList<Collection> getCollections(int userId) throws SQLException {
        ArrayList<Collection> collections = new ArrayList<>();
        ArrayList<Integer> collectionId = new ArrayList<>();

        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();

        String query1 = "SELECT collection_id user FROM collection WHERE person_id = '" + userId + "'";
        ResultSet rs = statement.executeQuery(query1);
        while (rs.next()) {
            collectionId.add(rs.getInt(1));
        }
        for (int i = 0; i < collectionId.size(); i++)
        {
            String query = "SELECT * FROM collection WHERE id = '" + collectionId.get(i) + "'";
            ResultSet rs1 = statement.executeQuery(query);
            while (rs1.next()) {
                collections.add(new Collection(rs1.getInt(1), rs1.getString("course_name"), rs1.getString("description")));
            }

        }
        DatabaseConnection.disconnectFromDb(connection, statement);
        return collections;
    }*/

    public static ArrayList<Collection> getAllCollections() throws SQLException {
        ArrayList<Collection> courses = new ArrayList<>();

        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();

        String query = "SELECT * FROM collection";
        ResultSet rs1 = statement.executeQuery(query);
        while (rs1.next()) {
            courses.add(new Collection(rs1.getInt(1), rs1.getString("name"), rs1.getString("description")));
        }

        DatabaseConnection.disconnectFromDb(connection, statement);
        return courses;
    }

}