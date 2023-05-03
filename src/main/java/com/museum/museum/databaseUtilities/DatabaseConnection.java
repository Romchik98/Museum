package com.museum.museum.databaseUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    public static Connection connectToDb() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DB_URL = "jdbc:mysql://localhost/museum";
            String USER = "root";
            String PASS = "admin";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            /*if(!conn.isClosed()) {
                System.out.println("DB connected.");
            }
            conn.close();
            if (conn.isClosed()) {
                System.out.println("DB disconnected.");
            }*/
        } catch (SQLException | ClassNotFoundException t) {
            t.printStackTrace();
        }
        return conn;
    }

    public static void disconnectFromDb(Connection connection, Statement statement) {

        try {
            if (connection != null && statement != null) {
                connection.close();
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}