package com.example.Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static Connection connection = null;

    static {

        String url = "jdbc:postgresql://localhost:5432/booktrack";
        String user = "root";
        String pass = "root";
        try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("Connected to the database.");
        } catch (ClassNotFoundException e) {
            System.out.println("PS JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console for more details.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
