package GUI;

import Model.DatabaseOperation;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PermitProduct {
    public JPanel permitPanel;
    public int isOK = 0;
    private final DatabaseOperation operation;
    private JTextArea productDescription;
    private JButton nextButton;
    private JLabel productPhotoLabel;
    private JLabel productName;
    private JLabel productPrice;
    private JLabel productSeller;
    private JButton permitButton;
    private JButton declineButton;
    private JButton goToLogin;
    private ImageIcon productPhoto;
    private ArrayList<Integer> productIDs = new ArrayList<>();
    private int productID;
    private int index = 0;
    private int size = 0;
    private final LoginPage loginPage;
    private final JFrame frame;


    public PermitProduct(JFrame frame, DatabaseOperation operation, LoginPage loginPage) {
        this.frame = frame;
        this.operation = operation;
        this.loginPage = loginPage;

        try {
            productIDs = operation.pull_product_not_permitted();
            System.out.println(productIDs);
            productPhoto = operation.pull_product_photo(productIDs.get(index));
            productID = productIDs.get(index);

            size = productIDs.size();
            System.out.println(index);
            Product product = operation.pull_product(productIDs.get(index));

            Image image = productPhoto.getImage().getScaledInstance(240, 240, Image.SCALE_FAST);
            productPhoto = new ImageIcon(image);
            productPhotoLabel.setIcon(productPhoto);

            productName.setText(product.getProductName());
            productSeller.setText(product.getProductSeller().getStudentEmail());
            productPrice.setText(Double.toString(product.getProductPrice()));

            productDescription.setEnabled(true);
            productDescription.setText(product.getProductDescription());
            productDescription.setEnabled(false);

            index++;
            isOK = 1;

        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "There are no products waiting to be permitted");
            isOK = -1;

        } catch (Exception e) {
            e.printStackTrace();
            isOK = 0;
        }

        permitButton.addActionListener(new PermissionListener());
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index == size) {
                    nextButton.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "No more products");
                } else {
                    Product product = operation.pull_product(productIDs.get(index));
                    try {
                        productPhoto = operation.pull_product_photo(productIDs.get(index));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    productID = productIDs.get(index);

                    Image image = productPhoto.getImage().getScaledInstance(240, 240, Image.SCALE_FAST);
                    productPhoto = new ImageIcon(image);
                    productPhotoLabel.setIcon(productPhoto);

                    productName.setText(product.getProductName());
                    productSeller.setText(product.getProductSeller().getStudentEmail());
                    productPrice.setText(Double.toString(product.getProductPrice()));

                    productDescription.setEnabled(true);
                    productDescription.setText(product.getProductDescription());
                    productDescription.setEnabled(false);

                    index++;
                }
            }
        });
        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation.delete_product(productIDs.get(index));
            }
        });
        goToLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(loginPage.getMainPanel());

                frame.pack();
                frame.repaint();
                frame.revalidate();
            }
        });

        nextButton.addMouseListener(new ButtonColorListener());
        permitButton.addMouseListener(new ButtonColorListener());
        goToLogin.addMouseListener(new ButtonColorListener());
        declineButton.addMouseListener(new ButtonColorListener());
    }

    public boolean permitProduct(int productID) {
        try {

            String query = "UPDATE Product set productPermit = 1 where productID = " + productID + ";";

            operation.statement = operation.con.createStatement();
            operation.statement.executeUpdate(query);

            // Gets the last inserted productID
            operation.statement = operation.con.createStatement();
            ResultSet set = operation.statement.executeQuery("SELECT productID FROM Product ORDER BY productID DESC");

            productID = 0;

            if (set.next()) {
                productID = set.getInt(1);
            }
            return true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
    }

    public class PermissionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            permitProduct(productID);
        }
    }

}
