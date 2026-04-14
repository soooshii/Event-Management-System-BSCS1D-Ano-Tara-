/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eventmanagementsystem;

/**
 *
 * @author admin
 */
public class EventManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Check the database connection
DatabaseConnection.getConnection();

// Create and show the Login Screen
LoginScreen login = new LoginScreen();
login.setVisible(true);
        // TODO code application logic here
        DatabaseConnection.getConnection();
    }
    
}
