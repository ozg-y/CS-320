package GUI;

import Model.DatabaseOperation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Garage {
    public JPanel productPanel;
    private JButton product1;
    private JButton product4;
    private JButton product2;
    private JButton product3;
    private JButton product5;
    private JButton product9;
    private JButton product6;
    private JButton product7;
    private JButton product8;
    private JButton product10;
    private JButton product11;
    private JButton product12;
    private JButton downScrollButton;
    private JButton upScrollButton;
    private JTextField textField1;
    private JButton refreshButtonButton;
    private JComboBox filterComboBox;
    private ArrayList<JButton> productButtons = new ArrayList<>();
    private ArrayList<Integer> productIds = new ArrayList<>();
    private ArrayList<ImageIcon> productImages = new ArrayList<>();
    private DatabaseOperation operation;

    int order = 0;
    int imageArrayIndex = 0;
    int nextProduct = 0;


    public Garage(DatabaseOperation operation){
        this.operation = operation;

        try {

            // Adding productID's in a ArrayList(productIDs) from database
            String query = "SELECT productID FROM Product GROUP BY productID ORDER BY productID DESC LIMIT " + order + ",12;";
            Statement statement = operation.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                productIds.add(resultSet.getInt("productID"));
            }

            // Adding productID's in a ArrayList(productImages) from database
            query = "SELECT productPhotos FROM ProductPhotos ORDER BY productID DESC LIMIT " + order + ",12;";
            statement = operation.con.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                InputStream x = (resultSet.getBinaryStream("productPhotos"));
                Image image = ImageIO.read(x);
                ImageIcon icon = new ImageIcon(image);
                productImages.add(icon);
            }

            // Adding product button to ArrayList(productButtons)
            productButtons.add(product1);
            productButtons.add(product2);
            productButtons.add(product3);
            productButtons.add(product4);
            productButtons.add(product5);
            productButtons.add(product6);
            productButtons.add(product7);
            productButtons.add(product8);
            productButtons.add(product9);
            productButtons.add(product10);
            productButtons.add(product11);
            productButtons.add(product12);

            // Setting Icons to proper product
            for(JButton but : productButtons){
                but.setIcon(productImages.get(imageArrayIndex++));
            }



            File upArrow = new File("\\Icons\\Arrow-Up.png");
            upScrollButton.setIcon(new ImageIcon(upArrow.getAbsolutePath()));

            File downArrow = new File("\\Icons\\Arrow-Down.png");
            downScrollButton.setIcon(new ImageIcon(downArrow.getAbsolutePath()));




                // Backup plan
//            product1.setIcon(images.get(imageArrayIndex++));
//            product2.setIcon(images.get(imageArrayIndex++));
//            product3.setIcon(images.get(imageArrayIndex++));
//            product4.setIcon(images.get(imageArrayIndex++));
//            product5.setIcon(images.get(imageArrayIndex++));
//            product6.setIcon(images.get(imageArrayIndex++));
//            product7.setIcon(images.get(imageArrayIndex++));
//            product8.setIcon(images.get(imageArrayIndex++));
//            product9.setIcon(images.get(imageArrayIndex++));
//            product10.setIcon(images.get(imageArrayIndex++));
//            product11.setIcon(images.get(imageArrayIndex++));
//            product12.setIcon(images.get(imageArrayIndex++));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ActionListener getProductDetails = e -> {
            int selectedProductIndex = productButtons.indexOf((JButton)e.getSource()) + ((nextProduct)*12);

            // todo GetProductDetails() with specific index
            System.out.println(productIds.get(selectedProductIndex));
        };

        // Added all buttons to getProductDetails actionListener
        for(JButton but : productButtons){
            but.addActionListener(getProductDetails);
        }

        downScrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order+=12;
                nextProduct++;
                try {
                    String query = "SELECT productID FROM Product GROUP BY productID ORDER BY productID DESC LIMIT " + order + ",12;";
                    Statement statement = operation.con.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {
                        productIds.add(resultSet.getInt("productID"));
                    }

                    query = "SELECT productPhotos FROM ProductPhotos ORDER BY productID DESC LIMIT " + order + ",12;";
                    statement = operation.con.createStatement();
                    resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {
                        InputStream x = (resultSet.getBinaryStream("productPhotos"));
                        Image image = ImageIO.read(x);
                        ImageIcon icon = new ImageIcon(image);
                        productImages.add(icon);
                    }

                    for(JButton but : productButtons){
                        but.setIcon(productImages.get(imageArrayIndex++));
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException z) {
                    z.printStackTrace();
                }
            }
        });


        upScrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pastImageArrayIndex = imageArrayIndex-24;
                nextProduct--;
                order-=12;

                for(JButton but : productButtons){
                    but.setIcon(productImages.get(pastImageArrayIndex));
                }
            }
        });
    }

}
