package GUI;

import Model.DatabaseOperation;
import Model.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class permitProduct {
    private JTextArea productDescription;
    private JButton nextProduct;
    private JLabel productPhoto;
    private JLabel productName;
    private JLabel productCategory;
    private JLabel productPrice;
    private JLabel productSeller;
    private JButton permitButton;
    public JPanel permitPanel;
    private DatabaseOperation operation;
    private ArrayList<InputStream> productPhotos = new ArrayList<>();
    private ArrayList<Integer> productIDs = new ArrayList<>();
    private int productID;
    private int index = 0;


    public permitProduct(JFrame frame,DatabaseOperation operation){

        this.operation = operation;


        try {

            productIDs = operation.pull_product_permitted();

            productPhotos = operation.pull_product_permitted_photos(productIDs.get(index));

            productID = productIDs.get(index);
            Product product = operation.pull_product(productIDs.get(index));

            Image icon = ImageIO.read(productPhotos.get(index));
            ImageIcon icon2 = new ImageIcon(icon);
            productPhoto.setIcon(icon2);
            productName.setText(product.getProductName());
            productCategory.setText(product.getProductCategory());
            productSeller.setText(product.getProductSeller().getStudentEmail());
            productPrice.setText(Double.toString(product.getProductPrice()));
            productDescription.setText(product.getProductDescription());

            index++;




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        permitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    //String query = "INSERT INTO Product VALUES (" + productID + ",\"" + productName.getText() + "\",\"" + productCategory.getText() + "\"," + productPrice.getText() + ",\"" + productSeller.getText() + "\",\"" + productDescription.getText() + "\");";

                    String query = "UPDATE Product set productPermit = 1 where productID = " + productID + ";";

                    operation.statement = operation.con.createStatement();
                    operation.statement.executeUpdate(query);

                    // Gets the last inserted productID
                    operation.statement = operation.con.createStatement();
                    ResultSet set = operation.statement.executeQuery("SELECT productID FROM Product ORDER BY productID DESC");

                    int productID = 0;

                    if (set.next()) {
                        productID = set.getInt(1);
                    }

                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        nextProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Product product = operation.pull_product(productIDs.get(index));
                    Image icon = ImageIO.read(productPhotos.get(index));
                    ImageIcon icon2 = new ImageIcon(icon);

                    productID = productIDs.get(index);
                    productPhoto.setIcon(icon2);
                    productName.setText(product.getProductName());
                    productCategory.setText(product.getProductCategory());
                    productSeller.setText(product.getProductSeller().getStudentEmail());
                    productPrice.setText(Double.toString(product.getProductPrice()));
                    productDescription.setText(product.getProductDescription());

                }catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        });
    }
}
