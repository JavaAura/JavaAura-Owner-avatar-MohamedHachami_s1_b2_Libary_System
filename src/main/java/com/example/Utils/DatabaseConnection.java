package com.example.Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;


public class DatabaseConnection {
    private static Connection connection = null;

    static {
        Dotenv dotenv = Dotenv.configure().load();
    
        // Access environment variables
        String url = dotenv.get("DATABASE_URL");
        String user = dotenv.get("USER_NAME");
        String pass = dotenv.get("USER_PWD");


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
