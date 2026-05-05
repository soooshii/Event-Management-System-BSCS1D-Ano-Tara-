package eventmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    
    // Database credentials
    private static final String URL = "jdbc:mysql://metro.proxy.rlwy.net:26796/railway";
    private static final String USER = "root"; // Default XAMPP username
    private static final String PASSWORD = "gKpzvYelGMfBeqTqNcDkBxMdltfRKYjC"; 

    // Method to establish and return the connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection successful!");
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL Driver not found. Did you add the .jar file?");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Connection Failed: " + e.getMessage());
        }
        return conn;
    }
}