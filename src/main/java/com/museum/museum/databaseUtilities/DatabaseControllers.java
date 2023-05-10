package com.museum.museum.databaseUtilities;

import com.museum.museum.controllers.LoginControl;
import com.museum.museum.controllers.SignUpControl;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.Exhibit;
import com.museum.museum.ds.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseControllers {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    /*public DatabaseControllers(String dbUrl, String username, String password) {
        try {
            // Establish a database connection
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            // Handle any exceptions that may occur during connection setup
            e.printStackTrace();
        }
    }*/

    public static int getLatestCollectionCreationId() throws SQLException {

        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT * FROM collection WHERE collection_id=(SELECT max(collection_id) FROM collection)";
        ResultSet rs = statement.executeQuery(query);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    public static int getLatestExhibitCreationId() throws SQLException {

        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT * FROM exhibit WHERE exhibit_id=(SELECT max(exhibit_id) FROM exhibit)";
        ResultSet rs = statement.executeQuery(query);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Users//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public static User validateLogin(String login, String password) throws Exception {

        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();
        LoginControl loginControl = new LoginControl();
        String query = "SELECT * FROM user WHERE login = '" + login + "' AND password = '" + loginControl.encrypt(password) + "'";
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
            LoginControl.alertMessage("Vartotojas sukurtas");
        } catch (Exception e) {
            LoginControl.alertMessage("Vartotojas nesukurtas" + e);
        }
    }

    public static void editUser(String loginName, String password, String name, String surname, int id) {
        try {
            connection = DatabaseConnection.connectToDb();
            String insertString = "UPDATE user SET login = '" + loginName + "', password = '" + password + "', person_name = '" + name + "', person_surname = '" + surname + "' where person_id = '" + id + "'";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.execute();
            DatabaseConnection.disconnectFromDb(connection, preparedStatement);
            LoginControl.alertMessage("Vartotojo duomenys atnaujinti");
        } catch (Exception e) {
            LoginControl.alertMessage("Klaida vartotojo redagavimo metu" + e);
        }
    }

    public static ArrayList<User> getAllUsers() throws SQLException {//////not done
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

    /*public static User getUser(int userId) throws SQLException{//webui
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
    }*/


////////////////////////////////////////////////////////////////////////////////////////////////////
    //Exhibit//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<Exhibit> getAllExhibits() throws SQLException {
        ArrayList<Exhibit> exhibits = new ArrayList<>();

        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();

        String query = "SELECT * FROM exhibit";
        ResultSet rs1 = statement.executeQuery(query);
        while (rs1.next()) {
            exhibits.add(new Exhibit(rs1.getInt(1), rs1.getString("name"), rs1.getString("description")));
        }

        DatabaseConnection.disconnectFromDb(connection, statement);
        return exhibits;
    }

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

    public static ArrayList<Exhibit> getExhibitData(int exhibitId) throws SQLException {
        ArrayList<Exhibit> exhibits = new ArrayList<>();

        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();

        String query2 = "SELECT * FROM exhibit WHERE exhibit_id = '" + exhibitId + "'";
        ResultSet rs2 = statement.executeQuery(query2);
        while (rs2.next()) {
            exhibits.add(new Exhibit(rs2.getInt(1), rs2.getString("name"), rs2.getString("description"),
                    rs2.getDate("date_of_creation"), rs2.getDate("date_of_discovery"),
                    rs2.getInt("quantity"), rs2.getString("condition"), rs2.getInt("collection_id"),
                    rs2.getString("place_of_creation"), rs2.getString("place_of_discovery"), rs2.getString("dimensions"),
                    rs2.getString("materials"), rs2.getString("type"), rs2.getString("object"),
                    rs2.getString("licence")));
        }
        DatabaseConnection.disconnectFromDb(connection, statement);
        return exhibits;
    }

    public static void createExhibit(Exhibit exhibit) {
        try {
            connection = DatabaseConnection.connectToDb();
            String insertString = "INSERT INTO exhibit(`name`, `collection_id`, `description`, `date_of_creation`, `date_of_discovery`," +
                    "`quantity`, `condition`, `place_of_creation`, `place_of_discovery`, `dimensions`, `materials`, `type`, `object`," +
                    "`licence`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, exhibit.getName());
            preparedStatement.setInt(2, exhibit.getCollectionId());
            preparedStatement.setString(3, exhibit.getDescription());
            preparedStatement.setDate(4, exhibit.getDateOfCreation());
            preparedStatement.setDate(5, exhibit.getDateOfDiscovery());
            preparedStatement.setInt(6, exhibit.getQuantity());
            preparedStatement.setString(7, exhibit.getCondition());
            preparedStatement.setString(8, exhibit.getPlaceOfCreation());
            preparedStatement.setString(9, exhibit.getPlaceOfDiscovery());
            preparedStatement.setString(10, exhibit.getDimensions());
            preparedStatement.setString(11, exhibit.getMaterials());
            preparedStatement.setString(12, exhibit.getType());
            preparedStatement.setString(13, exhibit.getObject());
            preparedStatement.setString(14, exhibit.getLicence());
            preparedStatement.execute();

            int id = DatabaseControllers.getLatestExhibitCreationId();
            exhibit.setId(id);

            DatabaseConnection.disconnectFromDb(connection, preparedStatement);
            LoginControl.alertMessage("Eksponatas sukurtas");
        } catch (Exception e) {
            System.out.println(e);
            LoginControl.alertMessage("Klaida eksponato kūrimo metu" + e);
        }
    }

    public static void editExhibit(Exhibit exhibit) {
        try {
            connection = DatabaseConnection.connectToDb();
            String insertString = "UPDATE exhibit SET name = '" + exhibit.getName() + "', description = '" + exhibit.getDescription() + "', date_of_creation = '" + exhibit.getDateOfCreation() + "'," +
                    " date_of_discovery = '" + exhibit.getDateOfDiscovery() + "', quantity = '" + exhibit.getQuantity() + "', condition = '" + exhibit.getCondition() + "', place_of_creation = '" + exhibit.getPlaceOfCreation() + "'," +
                    " place_of_discovery = '" + exhibit.getPlaceOfDiscovery() + "', dimensions = '" + exhibit.getDimensions() + "', materials = '" + exhibit.getMaterials() + "', type = '" + exhibit.getType() + "'," +
                    " object = '" + exhibit.getObject() + "', licence = '" + exhibit.getLicence() + "' where exhibit_id = '" + exhibit.getId() + "'";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.execute();
            DatabaseConnection.disconnectFromDb(connection, preparedStatement);
            LoginControl.alertMessage("Eksponatas atnaujintas");
        } catch (Exception e) {
            LoginControl.alertMessage("Klaida eksponato atnaujinimo metu" + e);
        }
    }

    public static void deleteExhibit(int id) throws SQLException{
        connection = DatabaseConnection.connectToDb();
        String query1 = "DELETE FROM exhibit WHERE exhibit_id = '" + id + "'";
        preparedStatement = connection.prepareStatement(query1);
        preparedStatement.execute();
        DatabaseConnection.disconnectFromDb(connection, preparedStatement);
        LoginControl.alertMessage("Eksponatas ištrintas");
    }

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
        ArrayList<Collection> collections = new ArrayList<>();

        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();

        String query = "SELECT * FROM collection";
        ResultSet rs1 = statement.executeQuery(query);
        while (rs1.next()) {
            collections.add(new Collection(rs1.getInt(1), rs1.getString("name"), rs1.getString("description")));
        }

        DatabaseConnection.disconnectFromDb(connection, statement);
        return collections;
    }

    public static void editCollection(Collection collection, int id) throws SQLException {
        try {
            connection = DatabaseConnection.connectToDb();
            String insertString = "UPDATE collection SET name = '" + collection.getName() + "', description = '" + collection.getDescription() + "' where collection_id = '" + id + "'";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.execute();
            DatabaseConnection.disconnectFromDb(connection, preparedStatement);
            LoginControl.alertMessage("Kolekcija atnaujinta");
        } catch (Exception e) {
            LoginControl.alertMessage("Klaida kolekcijos atnaujinimo metu" + e);
        }
    }

    public static void createCollection(Collection collection, User user) throws SQLException{
        try {
            connection = DatabaseConnection.connectToDb();
            String insertString = "INSERT INTO collection(`name`, `description`) VALUES (?,?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, collection.getName());
            preparedStatement.setString(2, collection.getDescription());
            preparedStatement.execute();

            int id = DatabaseControllers.getLatestCollectionCreationId();
            collection.setId(id);

            DatabaseConnection.disconnectFromDb(connection, preparedStatement);
            LoginControl.alertMessage("Kolekcija sukurta");
        } catch (Exception e) {
            System.out.println(e);
            LoginControl.alertMessage("Klaida kolekcijos kūrimo metu: " + e);
        }
    }

    public static void deleteCollection(int id) throws SQLException{

        //delete exhibits of collection
        connection = DatabaseConnection.connectToDb();
        String query1 = "DELETE FROM exhibit WHERE collection_id = '" + id + "'";
        preparedStatement = connection.prepareStatement(query1);
        preparedStatement.execute();

        //delete collection
        String query3 = "DELETE FROM collection WHERE collection_id = '" + id + "'";
        preparedStatement = connection.prepareStatement(query3);
        preparedStatement.execute();

        DatabaseConnection.disconnectFromDb(connection, preparedStatement);
        LoginControl.alertMessage("Kolekcija ištrinta");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //TESTS
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean doesUserNameExist (String name) throws SQLException {//testing
        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT login FROM user";
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            if(name.equals(rs.getString("login")))
                return true;
        }
        DatabaseConnection.disconnectFromDb(connection, statement);
        return false;
    }

    public static void deleteLogin(String login) throws SQLException{
        connection = DatabaseConnection.connectToDb();
        String query1 = "DELETE FROM user WHERE login = '" + login + "'";
        preparedStatement = connection.prepareStatement(query1);
        preparedStatement.execute();
        DatabaseConnection.disconnectFromDb(connection, preparedStatement);
    }

    public static void deleteCollections(String name) throws SQLException{//testing
        deleteCollection(getCollectionId(name));
    }

    public static int getCollectionId(String name) throws SQLException{//testing
        connection = DatabaseConnection.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT collection_id FROM collection WHERE name = '" + name + "'";
        ResultSet rs = statement.executeQuery(query);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("collection_id");
        }
        DatabaseConnection.disconnectFromDb(connection, statement);

        return id;
    }

    public static void deleteExhibits(String name) throws SQLException{
        connection = DatabaseConnection.connectToDb();
        String query1 = "DELETE FROM exhibit WHERE name = '" + name + "'";
        preparedStatement = connection.prepareStatement(query1);
        preparedStatement.execute();
        DatabaseConnection.disconnectFromDb(connection, preparedStatement);
    }
}