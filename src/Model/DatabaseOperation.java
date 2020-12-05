package Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

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

    public boolean checkForLogin(String studentEmail,String studentPassword){

        try {

            // Creating sql query
            String query = "SELECT studentName FROM Student WHERE studentEmail = \"" + studentEmail + "\" AND studentPassword = \"" + studentPassword + "\"";

            // Creating statement for database
            statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            // If the student exists return true
            if(resultSet.next()){
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // If the student doesn't exists return false
        return false;

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
                return new Student(resultSet.getString(0),resultSet.getString(1),resultSet.getBinaryStream(2),studentEmail,resultSet.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

    public void push_student_confirmation(String email, int confirmation_code) {
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

    public void push_product(String productName, String  productCategory, double productPrice,
                             String productSeller, String productDescription, ArrayList<String> productPhotos) {

        try {
            String query = "INSERT INTO Product (productName, productCategory, productPrice, productSeller, productDescription) VALUES (" +
                    "\'" + productName + "\',\'" + productCategory + "\'," + productPrice + "\'"  +
                    productSeller + "\',\'" + productDescription + "\');";

            statement = con.createStatement();
            statement.executeUpdate(query);

            // Gets the last inserted productID
            statement = con.createStatement();
            ResultSet set = statement.executeQuery("SELECT productID FROM  Product GROUP BY productID ORDER BY DESC");

            int productID = 0;

            if(set.next()) {
                productID = set.getInt(0);
            }

            // Push photo
            for(int i = 0; i < productPhotos.size(); i++) {
                String photo_query = "INSERT INTO ProductPhotos VALUES (" + productID + ",?);";

                preparedStatement = con.prepareStatement(photo_query);

                File file = new File(productPhotos.get(i));

                InputStream sqlPhoto = new FileInputStream(file);
                preparedStatement.setBinaryStream(1, sqlPhoto);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void push_comment(int productID, String comment) {
        try {

            String query = "INSERT INTO ProductComments (productID, productComments) VALUES (" + productID + "," + "\'" + comment + "\'" + ");";
            statement = con.createStatement();
            statement.execute(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<String> pull_comment(int productID) {
        try {
            String query = "SELECT productComments FROM ProductComments WHERE productID =" + productID + ";";
            statement = con.createStatement();
            ResultSet set = statement.executeQuery(query);

            ArrayList<String> comments = new ArrayList<>();

            while(set.next()) {
                comments.add(set.getString("productComments"));
            }

            return comments;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
