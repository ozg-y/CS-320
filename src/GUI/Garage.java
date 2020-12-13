package GUI;

import Model.DatabaseOperation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JTextField searchBar;
    private JButton refreshButtonButton;
    private JComboBox<String> filterComboBox;
    private ArrayList<JButton> productButtons = new ArrayList<>();
    private ArrayList<Integer> productIds = new ArrayList<>();
    private ArrayList<ImageIcon> productImages = new ArrayList<>();
    private DatabaseOperation operation;

    int imageArrayIndex = 0;
    int nextProduct = 0;


    public Garage(DatabaseOperation operation){
        this.operation = operation;

        /*product1.setBounds(0,50,100,150);
        product2.setBounds(0,50,0,50);
        product3.setBounds(0,50,0,50);
        product4.setBounds(0,50,0,50);
        product5.setBounds(0,50,0,50);
        product6.setBounds(0,50,0,50);
        product7.setBounds(0,50,0,50);
        product8.setBounds(0,50,0,50);
        product9.setBounds(0,50,0,50);
        product10.setBounds(0,50,0,50);
        product11.setBounds(0,50,0,50);
        product12.setBounds(0,50,0,50);

         */



        try {

            // Adding productID's in a ArrayList(productIDs) from database
            String query = "SELECT productID FROM Product GROUP BY productID ORDER BY productID DESC";
            Statement statement = operation.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                productIds.add(resultSet.getInt("productID"));
            }

            // Adding productID's in a ArrayList(productImages) from database
            query = "SELECT productPhotos FROM ProductPhotos GROUP BY productID ORDER BY productID DESC ;";
            statement = operation.con.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                InputStream x = (resultSet.getBinaryStream("productPhotos"));
                Image image = ImageIO.read(x);
                ImageIcon icon = new ImageIcon(image.getScaledInstance(250,250, Image.SCALE_SMOOTH));
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
            for(int i = 0;i<productImages.size();i++){
                productButtons.get(i).setIcon(productImages.get(imageArrayIndex++));
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
                for(JButton but : productButtons){
                    but.setIcon(productImages.get(imageArrayIndex++));
                }
            }
        });


        upScrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageArrayIndex -= 24;

                for(JButton but : productButtons){
                    but.setIcon(productImages.get(imageArrayIndex++));
                }

            }
        });

        /*String[] productOrder = { "Newest first", "Oldest first", "Most expensive first", "Cheapest first" };
        filterComboBox = new JComboBox(productOrder);
        filterComboBox.setSelectedIndex(0);
        productPanel.add(filterComboBox);

         */

        filterComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String productOrder = (String)filterComboBox.getSelectedItem();

                if(productOrder.equals("Most expensive first")){
                    try {
                        ResultSet myRst = operation.sort_price_increasing();

                        productIds.clear();
                        productImages.clear();

                        while (myRst.next()) {
                            productIds.add(myRst.getInt("productID"));
                        }

                        for(int id : productIds){
                            String sql = "SELECT * FROM ProductPhotos WHERE productID = " + id + " GROUP BY productID ;";
                            operation.statement = operation.con.createStatement();
                            myRst = operation.statement.executeQuery(sql);

                            while(myRst.next()){
                                InputStream x = (myRst.getBinaryStream("productPhotos"));
                                Image image = ImageIO.read(x);
                                ImageIcon icon = new ImageIcon(image);
                                productImages.add(icon);
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }

                else if(productOrder.equals("Cheapest first")){
                    try {
                        ResultSet myRst = operation.sort_price_decreasing();

                        productIds.clear();
                        productImages.clear();

                        while (myRst.next()) {
                            productIds.add(myRst.getInt("productID"));
                        }

                        for(int id : productIds){
                            String sql = "SELECT * FROM ProductPhotos WHERE productID = " + id + " GROUP BY productID ;";
                            operation.statement = operation.con.createStatement();
                            myRst = operation.statement.executeQuery(sql);

                            while(myRst.next()){
                                InputStream x = (myRst.getBinaryStream("productPhotos"));
                                Image image = ImageIO.read(x);
                                ImageIcon icon = new ImageIcon(image);
                                productImages.add(icon);
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                else if(productOrder.equals("Oldest first")){
                    try {
                        ResultSet myRst = operation.sort_date_latest();

                        productIds.clear();
                        productImages.clear();

                        while (myRst.next()) {
                            productIds.add(myRst.getInt("productID"));
                        }

                        for(int id : productIds){
                            String sql = "SELECT * FROM ProductPhotos WHERE productID = " + id + " GROUP BY productID ;";
                            operation.statement = operation.con.createStatement();
                            myRst = operation.statement.executeQuery(sql);

                            while(myRst.next()){
                                InputStream x = (myRst.getBinaryStream("productPhotos"));
                                Image image = ImageIO.read(x);
                                ImageIcon icon = new ImageIcon(image);
                                productImages.add(icon);
                            }
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                else if(productOrder.equals("Newest first")){
                    try {
                        ResultSet myRst = operation.sort_date_earliest();

                        productIds.clear();
                        productImages.clear();

                        while (myRst.next()) {
                            productIds.add(myRst.getInt("productID"));
                        }

                        for(int id : productIds){
                            String sql = "SELECT * FROM ProductPhotos WHERE productID = " + id + " GROUP BY productID ;";
                            operation.statement = operation.con.createStatement();
                            myRst = operation.statement.executeQuery(sql);

                            while(myRst.next()){
                                InputStream x = (myRst.getBinaryStream("productPhotos"));
                                Image image = ImageIO.read(x);
                                ImageIcon icon = new ImageIcon(image);
                                productImages.add(icon);
                            }
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ((JButton)e.getSource()).setBackground(new Color(8,33,41));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                ((JButton)e.getSource()).setBackground(new Color(6,9,15));
            }
        };
        product4.addMouseListener(listener);
        product9.addMouseListener(listener);
        product8.addMouseListener(listener);
        product7.addMouseListener(listener);
        product6.addMouseListener(listener);
        product2.addMouseListener(listener);
        product3.addMouseListener(listener);
        product5.addMouseListener(listener);
        product12.addMouseListener(listener);
        product10.addMouseListener(listener);
        product11.addMouseListener(listener);
        product1.addMouseListener(listener);
    }
}
