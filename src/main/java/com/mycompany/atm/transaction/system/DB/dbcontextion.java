
package com.mycompany.atm.transaction.system.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbcontextion {
    
    // 🔧 Database info
    private static final String URL = "jdbc:postgresql://localhost:5433/dbATM";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    // ✅ Function to get Connection
    public static Connection getConnection() {
        try {
            // Load PostgreSQL Driver
            Class.forName("org.postgresql.Driver");
            
            // Create connection
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
            return con;

        } catch (ClassNotFoundException e) {
            System.out.println("❌ PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
        return null;
    }

    // Optional: test connection
    public static void main(String[] args) {
        getConnection();
    }
}
