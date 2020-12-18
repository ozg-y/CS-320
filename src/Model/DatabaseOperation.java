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

    public DatabaseOperation() {

        JFrame frame = new JFrame();
        String url = "jdbc:mysql://" + Database.DBip + ":" + Database.DBport + "/" + "ozug";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException ex) {
            System.out.println("Couldn't Find JDBC Driver");
        }

        try {
            con = DriverManager.getConnection(url, Database.DBusername, Database.DBpassword);
            System.out.println("Database Connection Successful");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Database Connection Fails.Try Again.");
        }
    }

    public boolean checkForLogin(String studentEmail, String studentPassword) {

        try {

            // Creating sql query
            String query = "SELECT studentName FROM Student WHERE studentEmail = \"" + studentEmail + "\" AND studentPassword = \"" + studentPassword + "\"";

            // Creating statement for database
            statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            // If the student exists return true
            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // If the student doesn't exists return false
        return false;

    }

    public void push_student(String studentName, String studentSurname, String studentPhoto, String studentEmail, String studentPassword) {
        try {

            // Changing type of the studentPhoto
            InputStream sqlPhoto = new FileInputStream(studentPhoto);

            // Writing SQL query for push
            String query = "INSERT INTO Student VALUES (\'" + studentName + "\',\'" + studentSurname + "\',?,\'" + studentEmail + "\',\'" + studentPassword + "\',0);";
            preparedStatement = con.prepareStatement(query);

            // Pushing the studentPhoto in binary format
            preparedStatement.setBinaryStream(1, sqlPhoto);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Student pull_student(String studentEmail) {
        try {

            // Writing SQL query for push
            String query = "SELECT * FROM Student WHERE studentEmail = \'" + studentEmail + "\'";
            statement = con.createStatement();

            // Creating ResultSet type to pull data from database
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return new Student(resultSet.getString(0), resultSet.getString(1), resultSet.getBinaryStream(2), studentEmail, resultSet.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

    public void change_student_password(String studentEmail, String newPassword) {
        try {

            //Writing SQL query for update
            String query = "UPDATE Student set studentPassword = \'" + newPassword + "\' where studentEmail = \'" + studentEmail + "\'";

            //Creating statemnt for our database query
            statement = con.createStatement();
            statement.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "Your Password Has Been Changed Correctly");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(null, "Your Password Was Not Changed Correctly");
        }
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

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error occured");
            System.out.println(e);
        }
        return -1;
    }

    public void confirmed_new_stundet(String studentEmail) {

        try {
            String query = "UPDATE Student set studentConfirmationCheck = 1 where studentEmail = \'" + studentEmail + "\'";

            statement = con.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void push_product(String productName, String productCategory, double productPrice,
                             String productSeller, String productDescription, ArrayList<String> productPhotos) {

        try {
            String query = "INSERT INTO Product (productName, productCategory, productPrice, productSeller, productDescription) VALUES (" +
                    "\'" + productName + "\',\'" + productCategory + "\'," + productPrice + "\'" +
                    productSeller + "\',\'" + productDescription + "\');";

            statement = con.createStatement();
            statement.executeUpdate(query);

            // Gets the last inserted productID
            statement = con.createStatement();
            ResultSet set = statement.executeQuery("SELECT productID FROM  Product GROUP BY productID ORDER BY DESC");

            int productID = 0;

            if (set.next()) {
                productID = set.getInt(0);
            }

            // Push photo
            for (int i = 0; i < productPhotos.size(); i++) {
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

    public Product pull_product(int productID) {
        try {
            ArrayList<Comment> comments = pull_comment(productID);       // comments
            ArrayList<ImageIcon> photos = pull_product_photos(productID);       // product_photos
            ArrayList<String> comment = new ArrayList<>();

            for(Comment c : comments){
                comment.add(c.comment);
            }
            String query = "SELECT * FROM Product WHERE productID = " + productID + ";";
            statement = con.createStatement();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                return new Product(set.getInt(0), set.getString(1), set.getString(2), photos,
                        set.getDouble(3), comment, pull_student(set.getString(4)), set.getString(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public ArrayList<ImageIcon> pull_product_photos(int productID) throws SQLException {
        try {
            statement = con.createStatement();
            ResultSet photo_set = statement.executeQuery("SELECT productPhotos FROM ProductPhotos WHERE productID = " + productID + ";");
            ArrayList<ImageIcon> photos = new ArrayList<>();

            while (photo_set.next()) {
                ImageIcon icon = new ImageIcon(ImageIO.read(photo_set.getBinaryStream("productPhotos")));
                photos.add(icon);
            }

            return photos;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void push_comment(int productID, String comment, String StudentEmail) {

        try {

            String query = "INSERT INTO ProductComments (productID, StudentEmail, productComments) VALUES (" + productID + "," + "\'" + StudentEmail + "\'" + "\'" + comment + "\'" + ");";
            statement = con.createStatement();
            statement.execute(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Comment> pull_comment(int productID) {
        try {
            String query = "SELECT * FROM ProductComments WHERE productID =" + productID + ";";
            statement = con.createStatement();
            ResultSet set = statement.executeQuery(query);

            ArrayList<Comment> comments = new ArrayList<>();

            while (set.next()) {
                Comment comment=new Comment(set.getString("StudentEmail"),set.getString("productComments"));
                comments.add(comment);
            }

            return comments;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet sort_price_decreasing() {
        try {
            statement = con.createStatement();
            ResultSet set = statement.executeQuery("SELECT * \n" +
                    "FROM Product\n" +
                    "ORDER BY productPrice desc;");

            return set;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet sort_price_increasing() {
        try {
            statement = con.createStatement();
            ResultSet set = statement.executeQuery("SELECT * \n" +
                    "FROM Product\n" +
                    "ORDER BY productPrice ASC;");

            return set;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet sort_date_latest() {
        try {
            statement = con.createStatement();
            ResultSet set = statement.executeQuery("SELECT * \n" +
                    "FROM Product\n" +
                    "ORDER BY productID DESC;");

            return set;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet sort_date_earliest() {
        try {
            statement = con.createStatement();
            ResultSet set = statement.executeQuery("SELECT * \n" +
                    "FROM Product\n" +
                    "ORDER BY productID ASC;");

            return set;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet book_category() {
        try {
            String query = "SELECT * FROM Product WHERE productName =\" book \";";
            statement = con.createStatement();
            ResultSet set = statement.executeQuery(query);


            return set;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
    public ResultSet book_photos() {
        try {
            String query = "SELECT * FROM ProductPhotos WHERE productName =\" book \";";
            statement = con.createStatement();
            ResultSet set = statement.executeQuery(query);


            return set;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
    public ResultSet ticket_category() {
        try {
            String query = "SELECT * FROM Product WHERE productCategory =\" ticket \";";
            statement = con.createStatement();
            ResultSet set = statement.executeQuery(query);


            return set;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
    public ResultSet ticket_photos() {
        try {
            String query = "SELECT * FROM ProductPhotos WHERE productCategory =\" ticket \";";
            statement = con.createStatement();
            ResultSet set = statement.executeQuery(query);


            return set;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
    public ResultSet furniture_category() {
        try {
            String query = "SELECT * FROM Product WHERE productCategory =\" furniture \";";
            statement = con.createStatement();
            ResultSet set = statement.executeQuery(query);


            return set;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
    public ResultSet furniture_photos() {
        try {
            String query = "SELECT * FROM ProductPhotos WHERE productCategory =\" furniture \";";
            statement = con.createStatement();
            ResultSet set = statement.executeQuery(query);


            return set;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
}