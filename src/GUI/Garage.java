package GUI;

import Model.DatabaseOperation;
import Model.Student;
import com.placeholder.PlaceHolder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static GUI.LPanel.scaleFile;

public class Garage {

    private final DatabaseOperation operation;
    public JPanel productPanel;
    int imageArrayIndex = 0;
    int pageNumber = 1;
    int functionCode = -1;
    String searchBarText = "";
    String filterOption = "";
    PlaceHolder placeHolder;
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

    public Garage(JFrame frame, DatabaseOperation operation, Student student) {


        placeHolder = new PlaceHolder(searchBar, "Search in OzU-Garage");

        this.operation = operation;

        upScrollButton.setEnabled(false);

        filterComboBox.addItem("Most expensive first");
        filterComboBox.addItem("Cheapest first");
        filterComboBox.addItem("Oldest first");
        filterComboBox.addItem("Newest first");
        filterComboBox.setVisible(true);

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

        ImageIcon upIcon = scaleFile(130, 30, "Arrow-Up.png");
        upScrollButton.setIcon(upIcon);

        ImageIcon downIcon = scaleFile(130, 30, "Arrow-Down.png");
        downScrollButton.setIcon(downIcon);

        update_garage("ALL");

        ActionListener getProductDetails = e -> {

            int selectedProductIndex = productButtons.indexOf(e.getSource()) + ((pageNumber - 1) * 12);
            int productID = productIds.get(selectedProductIndex);
            ProductPage productPage = new ProductPage(productID, operation, student);

            frame.getContentPane().removeAll();
            frame.setLayout(new BorderLayout());
            frame.repaint();

            LPanel lPanel = new LPanel(frame, operation, this, student);
            frame.getContentPane().add(productPage.getProductPPanel(), BorderLayout.CENTER);
            frame.getContentPane().add(lPanel.lPanel, BorderLayout.WEST);
            frame.pack();
            frame.repaint();
            frame.revalidate();
        };

        // Added all buttons to getProductDetails actionListener
        for (JButton but : productButtons) {
            but.addActionListener(getProductDetails);
        }

        downScrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isEnabled = false;
                upScrollButton.setEnabled(true);
                if (productIds.size() > (pageNumber * 12)) {
                    pageNumber++;
                    for (JButton but : productButtons) {
                        if (imageArrayIndex < productImages.size())
                            but.setIcon(productImages.get(imageArrayIndex++));
                        else {
                            isEnabled = true;
                            but.setIcon(null);
                        }
                    }
                }
                if (isEnabled)
                    downScrollButton.setEnabled(false);
            }
        });

        upScrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downScrollButton.setEnabled(true);
                if (pageNumber > 1) {
                    pageNumber--;
                    imageArrayIndex -= 13;
                    for (JButton but : productButtons) {
                        if (imageArrayIndex < productImages.size())
                            but.setIcon(productImages.get(imageArrayIndex++));
                        else
                            but.setIcon(null);
                    }
                }
                if (pageNumber == 1)
                    upScrollButton.setEnabled(false);
            }
        });

        // filter based on title -> product Name
        searchBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBarText = e.getActionCommand();
                search_bar(e.getActionCommand());

            }
        });

        filterComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String productOrder = (String) filterComboBox.getSelectedItem();


                imageArrayIndex = 0;

                if (productOrder.equals("Cheapest first")) {
                    sort_price_increasing();
                } else if (productOrder.equals("Most expensive first")) {
                    sort_price_decreasing();
                } else if (productOrder.equals("Newest first")) {
                    sort_date_latest();
                } else if (productOrder.equals("Oldest first")) {
                    sort_date_earliest();
                }
            }
        });


        refreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                refreshButton.setBackground(Color.white);
                refreshButton.setForeground(new Color(163, 0, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                refreshButton.setBackground(new Color(163, 0, 80));
                refreshButton.setForeground(Color.white);
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
    }

    public void update_garage(String condition) {

        productIds.clear();
        productImages.clear();
        imageArrayIndex = 0;

        // Initializing icons on buttons
        for (int i = 0; i < productButtons.size(); i++) {
            productButtons.get(i).setIcon(null);
        }

        if (condition.equals("ALL")) {
            try {

                functionCode = 5;

                // Adding productID's in a ArrayList(productIDs) from database
                String query = "SELECT productID FROM Product where productPermit = 1 GROUP BY productID ORDER BY productID DESC";
                Statement statement = operation.con.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    productIds.add(resultSet.getInt("productID"));
                }

                // Adding productID's in a ArrayList(productImages) from database
                query = "SELECT productPhoto FROM Product where productPermit = 1 ORDER BY productID DESC;";

                statement = operation.con.createStatement();
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    InputStream x = resultSet.getBinaryStream("productPhoto");
                    Image image = ImageIO.read(x);
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(245, 245, Image.SCALE_SMOOTH));
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

                functionCode = 6;

                ResultSet resultSet = operation.book_id();

                while (resultSet.next()) {
                    productIds.add(resultSet.getInt("productID"));
                }

                // Adding all of the photos that match the search request
                // Finding photos based on previously identified productID
                for (int i : productIds) {
                    resultSet = operation.book_images(i);

                    while (resultSet.next()) {
                        InputStream x = (resultSet.getBinaryStream("productPhoto"));
                        Image image = ImageIO.read(x);
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(245, 245, Image.SCALE_SMOOTH));
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

                functionCode = 7;

                ResultSet resultSet = operation.furniture_id();

                while (resultSet.next()) {
                    productIds.add(resultSet.getInt("productID"));
                }

                // Adding all of the photos that match the search request
                // Finding photos based on previously identified productID
                for (int i : productIds) {
                    resultSet = operation.furniture_images(i);

                    while (resultSet.next()) {
                        InputStream x = (resultSet.getBinaryStream("productPhoto"));
                        Image image = ImageIO.read(x);
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(245, 245, Image.SCALE_SMOOTH));
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

                functionCode = 8;

                ResultSet resultSet = operation.ticket_id();

                while (resultSet.next()) {
                    productIds.add(resultSet.getInt("productID"));
                }

                // Adding all of the photos that match the search request
                // Finding photos based on previously identified productID
                for (int i : productIds) {
                    resultSet = operation.ticket_images(i);

                    while (resultSet.next()) {
                        InputStream x = (resultSet.getBinaryStream("productPhoto"));
                        Image image = ImageIO.read(x);
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(245, 245, Image.SCALE_SMOOTH));
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

        for (int i = 0; i < productButtons.size(); i++) {
            if (i < productImages.size())
                productButtons.get(i).setIcon(productImages.get(imageArrayIndex++));
        }

        for (JButton b : productButtons) {
            b.setEnabled(b.getIcon() != null);
        }
    }

    public void refresh() {
        if (functionCode == 0)
            search_bar(searchBarText);
        else if (functionCode == 1)
            sort_price_increasing();
        else if (functionCode == 2)
            sort_price_decreasing();
        else if (functionCode == 3)
            sort_date_latest();
        else if (functionCode == 4)
            sort_date_earliest();
        else if (functionCode == 5)
            update_garage("ALL");
        else if (functionCode == 6)
            update_garage("book");
        else if (functionCode == 7)
            update_garage("furniture");
        else if (functionCode == 8)
            update_garage("ticket");
    }

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

    public void sort_price_increasing() {
        try {

            functionCode = 1;
            imageArrayIndex = 0;

            ResultSet myRst = operation.sort_price_increasing(filterOption);

            productIds.clear();
            productImages.clear();

            while (myRst.next()) {
                productIds.add(myRst.getInt("productID"));
            }

            for (int id : productIds) {
                String sql = "SELECT productPhoto FROM Product WHERE productID = " + id + " AND productPermit = 1 GROUP BY productID;";
                operation.statement = operation.con.createStatement();
                myRst = operation.statement.executeQuery(sql);

                while (myRst.next()) {
                    InputStream x = (myRst.getBinaryStream("productPhoto"));
                    Image image = ImageIO.read(x);
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(240,240,Image.SCALE_DEFAULT));
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

    public void sort_price_decreasing() {
        try {

            functionCode = 2;
            imageArrayIndex = 0;

            ResultSet myRst = operation.sort_price_decreasing(filterOption);

            productIds.clear();
            productImages.clear();

            while (myRst.next()) {
                productIds.add(myRst.getInt("productID"));
            }

            for (int id : productIds) {
                String sql = "SELECT productPhoto FROM Product WHERE productID = " + id + " AND productPermit = 1 GROUP BY productID ;";
                operation.statement = operation.con.createStatement();
                myRst = operation.statement.executeQuery(sql);

                while (myRst.next()) {
                    InputStream x = (myRst.getBinaryStream("productPhoto"));
                    Image image = ImageIO.read(x);
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(240,240,Image.SCALE_DEFAULT));
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

    public void sort_date_latest() {
        try {

            functionCode = 3;
            imageArrayIndex = 0;

            ResultSet myRst = operation.sort_date_latest(filterOption);

            productIds.clear();
            productImages.clear();

            while (myRst.next()) {
                productIds.add(myRst.getInt("productID"));
            }

            for (int id : productIds) {
                String sql = "SELECT productPhoto FROM Product WHERE productID = " + id + " AND productPermit = 1 GROUP BY productID ;";
                operation.statement = operation.con.createStatement();
                myRst = operation.statement.executeQuery(sql);

                while (myRst.next()) {
                    InputStream x = (myRst.getBinaryStream("productPhoto"));
                    Image image = ImageIO.read(x);
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(240,240,Image.SCALE_DEFAULT));
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

    public void sort_date_earliest() {
        try {

            functionCode = 4;
            imageArrayIndex = 0;

            ResultSet myRst = operation.sort_date_earliest(filterOption);

            productIds.clear();
            productImages.clear();

            while (myRst.next()) {
                productIds.add(myRst.getInt("productID"));
            }

            for (int id : productIds) {
                String sql = "SELECT productPhoto FROM Product WHERE productID = " + id + " AND productPermit = 1 GROUP BY productID ;";
                operation.statement = operation.con.createStatement();
                myRst = operation.statement.executeQuery(sql);

                while (myRst.next()) {
                    InputStream x = (myRst.getBinaryStream("productPhoto"));
                    Image image = ImageIO.read(x);
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(240,240,Image.SCALE_DEFAULT));
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

    public void search_bar(String search_request) {
        functionCode = 0;

        for (int i = 0; i < productButtons.size(); i++) {
            productButtons.get(i).setIcon(null);
        }

        productIds.clear();
        productImages.clear();
        imageArrayIndex = 0;

        try {
            String query = "SELECT productID FROM Product WHERE productName = '" + search_request + "' AND productPermit = 1;";

            Statement statement = operation.con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                productIds.add(resultSet.getInt("productID"));
            }

            // Adding all of the photos that match the search request
            // Finding photos based on previously identified productID
            for (int i : productIds) {
                query = "SELECT productPhoto FROM Product WHERE productID = " + i + ";";
                statement = operation.con.createStatement();
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    InputStream x = (resultSet.getBinaryStream("productPhoto"));
                    Image image = ImageIO.read(x);
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(245, 245, Image.SCALE_SMOOTH));
                    productImages.add(icon);
                }
            }

            for (int i = 0; i < productImages.size(); i++) {
                productButtons.get(i).setIcon(productImages.get(imageArrayIndex++));
            }

            for (JButton b : productButtons) {
                b.setEnabled(b.getIcon() != null);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}

