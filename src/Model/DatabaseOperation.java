package Model;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

public class DatabaseOperation {

    public Connection con;
    public Statement statement;
    public PreparedStatement preparedStatement;

    public DatabaseOperation(){

        JFrame frame = new JFrame();
        String url = "jdbc:mysql://" + Database.DBip + ":" + Database.DBport + "/" + "ozug";

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

    public void push_student(String studentName,String studentSurname,String studentPhoto,String studentEmail,String studentPassword){
        try{

            // Changing type of the studentPhoto
            InputStream sqlPhoto = new FileInputStream(studentPhoto);

            // Writing SQL query for push
            String query = "INSERT INTO Student VALUES (\'" + studentName + "\',\'" + studentSurname + "\',?,\'" + studentEmail + "\',\'" + studentPassword + "\',0);";
            preparedStatement = con.prepareStatement(query);

            // Pushing the studentPhoto in binary format
            preparedStatement.setBinaryStream(1,sqlPhoto);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Student pull_student(String studentEmail){
        try {

            // Writing SQL query for push
            String query = "SELECT * FROM Student WHERE studentEmail = \'" + studentEmail + "\'";
            statement = con.createStatement();

            // Creating ResultSet type to pull data from database
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                Student student = new Student(resultSet.getString(0),resultSet.getString(1),resultSet.getBinaryStream(2),studentEmail,resultSet.getString(4));
                return student;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
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


    public int pull_student_confirmation_code(String email) throws SQLException {
        try {
            String query = "SELECT studentConfirmationCode\n" +
                    "            FROM StudentConfirmation\n" +
                    "            WHERE studentEmail = ";
            query += "'" + email + "'" + ";";

            ResultSet resultSet = null;

            statement = con.createStatement();
            resultSet = statement.executeQuery(query);

            if(resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error occured");
            System.out.println(e);
        }
        return -1;
    }

}
