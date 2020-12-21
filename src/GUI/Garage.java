package GUI;

import Model.DatabaseOperation;
import Model.Student;

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

import static GUI.LPanel.scaleFile;

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
    private JButton refreshButton;
    private JComboBox<String> filterComboBox;
    private ArrayList<JButton> productButtons = new ArrayList<>();
    private ArrayList<Integer> productIds = new ArrayList<>();
    private ArrayList<ImageIcon> productImages = new ArrayList<>();
    private DatabaseOperation operation;

    int imageArrayIndex = 0;
    int nextProduct = 0;

    public Garage(JFrame frame, DatabaseOperation operation, Student student){

        this.operation = operation;

        filterComboBox.addItem("Most expensive first");
        filterComboBox.addItem("Cheapest first");
        filterComboBox.addItem("Oldest first");
        filterComboBox.addItem("Newest first");

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

        ImageIcon upIcon = scaleFile(130,30,"Arrow-Up.png");
        upScrollButton.setIcon(upIcon);

        ImageIcon downIcon = scaleFile(130,30,"Arrow-Down.png");
        downScrollButton.setIcon(downIcon);

       update_garage("ALL");

       ActionListener getProductDetails = e -> {

            int selectedProductIndex = productButtons.indexOf((JButton)e.getSource()) + ((nextProduct)*12);
            int productID = productIds.get(selectedProductIndex);
            ProductPage productp = new ProductPage(productID,operation, student);

            frame.getContentPane().removeAll();
            frame.setLayout(new BorderLayout());
            frame.repaint();

            LPanel lPanel = new LPanel(frame,operation,this, student);
            frame.getContentPane().add(productp.getProductPPanel(), BorderLayout.CENTER);
            frame.getContentPane().add(lPanel.lPanel, BorderLayout.WEST);
            frame.pack();
            frame.repaint();
            frame.revalidate();
            frame.setSize(1400, 900);
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

        // filter based on title -> product Name
        searchBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for(int i = 0; i < productButtons.size(); i++){
                    productButtons.get(i).setIcon(null);
                }


                String search_request = e.getActionCommand();
                productIds.clear();
                productImages.clear();
                imageArrayIndex = 0;

                try {
                    String query = "SELECT productID FROM Product WHERE productName = \'" + search_request + "\';";

                    Statement statement = operation.con.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {
                        productIds.add(resultSet.getInt("productID"));
                    }


                    // Adding all of the photos that match the search request
                    // Finding photos based on previously identified productID
                    for(int i : productIds) {
                        query = "SELECT productPhotos FROM ProductPhotos WHERE productID = " + i + ";";
                        statement = operation.con.createStatement();
                        resultSet = statement.executeQuery(query);

                        while (resultSet.next()) {
                            InputStream x = (resultSet.getBinaryStream("productPhotos"));
                            Image image = ImageIO.read(x);
                            ImageIcon icon = new ImageIcon(image.getScaledInstance(250,250, Image.SCALE_SMOOTH));
                            productImages.add(icon);
                        }
                    }

                    System.out.println("image size : " + productImages.size());
                    for(int i = 0; i < productImages.size(); i++){
                        productButtons.get(i).setIcon(productImages.get(imageArrayIndex++));
                    }


                    System.out.println("No errors until this point");
                    for (JButton but : productButtons) {
                        if (but.getIcon() == null) {
                            but.setEnabled(false);
                            but.setOpaque(false);
                        }
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        // TODO FilterComboBox doesn't work -> should be fixed
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

                            while(myRst.next()) {
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

        refreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                refreshButton.setBackground(Color.white);
                refreshButton.setForeground(new Color(163,0,80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                refreshButton.setBackground(new Color(163,0,80));
                refreshButton.setForeground(Color.white);
            }
        });
    }


    public void update_garage(String condition) {
        productIds.clear();
        productImages.clear();
        imageArrayIndex = 0;

        // Initializing icons on buttons
        for(int i = 0; i < productButtons.size(); i++){
            productButtons.get(i).setIcon(null);
        }

        if (condition.equals("ALL")) {
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


                display_garage();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (condition.equals("book")) {
            try {
                ResultSet resultSet = operation.book_id();

                while (resultSet.next()) {
                    productIds.add(resultSet.getInt("productID"));
                }

                // Adding all of the photos that match the search request
                // Finding photos based on previously identified productID
                for(int i : productIds) {
                    resultSet = operation.book_images(i);

                    while (resultSet.next()) {
                        InputStream x = (resultSet.getBinaryStream("productPhotos"));
                        Image image = ImageIO.read(x);
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(250,250, Image.SCALE_SMOOTH));
                        productImages.add(icon);
                    }
                }

                display_garage();


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } else if (condition.equals("furniture")) {
            try {
                ResultSet resultSet = operation.furniture_id();

                while (resultSet.next()) {
                    productIds.add(resultSet.getInt("productID"));
                }

                // Adding all of the photos that match the search request
                // Finding photos based on previously identified productID
                for(int i : productIds) {
                    resultSet = operation.furniture_images(i);

                    while (resultSet.next()) {
                        InputStream x = (resultSet.getBinaryStream("productPhotos"));
                        Image image = ImageIO.read(x);
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(250,250, Image.SCALE_SMOOTH));
                        productImages.add(icon);
                    }
                }

                display_garage();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } else if (condition.equals("ticket")) {
            try {
                ResultSet resultSet = operation.ticket_id();

                while (resultSet.next()) {
                    productIds.add(resultSet.getInt("productID"));
                }

                // Adding all of the photos that match the search request
                // Finding photos based on previously identified productID
                for(int i : productIds) {
                    resultSet = operation.ticket_images(i);

                    while (resultSet.next()) {
                        InputStream x = (resultSet.getBinaryStream("productPhotos"));
                        Image image = ImageIO.read(x);
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(250,250, Image.SCALE_SMOOTH));
                        productImages.add(icon);
                    }
                }

                display_garage();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void display_garage() {

        for(int i = 0; i < productImages.size(); i++){
            productButtons.get(i).setIcon(productImages.get(imageArrayIndex++));
        }

        for (JButton b : productButtons) {
            if (b.getIcon() == null) {
                b.setEnabled(false);
            }
            else{
                b.setEnabled(true);
            }
        }
    }

    //************************************************************
    public void Refresh(){
        for(int i = 0; i < productImages.size();i++){
            productButtons.get(i).setIcon(productImages.get(i));
        }
    }
    //************************************************************
    public JPanel getProductPanel() {
        return productPanel;
    }

    public ArrayList<JButton> getProductButtons() {
        return productButtons;
    }

    public void setProductButtons(ArrayList<JButton> productButtons) {
        this.productButtons = productButtons;
    }

    public ArrayList<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(ArrayList<Integer> productIds) {
        this.productIds = productIds;
    }

    public ArrayList<ImageIcon> getProductImages() {
        return productImages;
    }

    public void setProductImages(ArrayList<ImageIcon> productImages) {
        this.productImages = productImages;
    }
}
