package Model;

import javax.swing.*;
import java.sql.*;

public class DatabaseOperation {

    public Connection con;
    public Statement statement;
    public PreparedStatement preparedStatement;

    public DatabaseOperation(){

        JFrame frame = new JFrame();
        String url = "jdbc:mysql://" + Database.DBip + ":" + Database.DBport + "/" + Database.DBusername;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException ex) {
            System.out.println("Couldn't Find JDBC Driver");
        }

        try {
            con = DriverManager.getConnection(url, Database.DBusername,Database.DBpassword);
            System.out.println("Database Connection Successful");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame,"Database Connection Fails.Try Again.");
        }
    }

    public void push_student_confirmation(String email, int confirmation_code) throws SQLException {
        try {
            String query = "INSERT INTO StudentConfirmation VALUES (";
            query += "\'" + email + "\'" + "," + confirmation_code + ");";

            statement = con.createStatement();
            statement.executeUpdate(query);
            System.out.println("Succesfully executed");
        } catch (SQLException e) {
            System.out.println("Error occured");
            System.out.println(e);
        }
    }
}
