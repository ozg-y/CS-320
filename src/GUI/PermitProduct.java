package GUI;

import Model.DatabaseOperation;
import Model.Product;

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
import java.util.ArrayList;

public class PermitProduct {
    private DatabaseOperation operation;
    public JPanel permitPanel;
    public int isOK = 0;
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


    public PermitProduct(JFrame frame, DatabaseOperation operation, LoginPage loginPage) {

        this.operation = operation;

        try {
            productIDs = operation.pull_product_not_permitted();
            System.out.println(productIDs);
            productPhoto = operation.pull_product_photo(productIDs.get(index));
            productID = productIDs.get(index);

            size = productIDs.size();
            System.out.println(index);
            Product product = operation.pull_product(productIDs.get(index));

            Image image = productPhoto.getImage().getScaledInstance(240, 240,  Image.SCALE_FAST);
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

        permitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

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

                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index == size) {
                    nextButton.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"No more products");
                } else {
                    Product product = operation.pull_product(productIDs.get(index));
                    try {
                        productPhoto = operation.pull_product_photo(productIDs.get(index));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    productID = productIDs.get(index);

                    Image image = productPhoto.getImage().getScaledInstance(240, 240,  Image.SCALE_FAST);
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

        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                nextButton.setForeground(new Color(0, 32, 96));
                nextButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nextButton.setForeground(Color.WHITE);
                nextButton.setBackground(new Color(0, 32, 96));
            }
        });
        permitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                permitButton.setForeground(new Color(65, 163, 81));
                permitButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                permitButton.setForeground(Color.WHITE);
                permitButton.setBackground(new Color(65, 163, 81));
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
        goToLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                goToLogin.setForeground(new Color(60, 163, 209));
                goToLogin.setBackground(Color.WHITE);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                goToLogin.setForeground(Color.WHITE);
                goToLogin.setBackground(new Color(60, 163, 209));
            }
        });
        declineButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                declineButton.setForeground(new Color(209, 60, 62));
                declineButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                declineButton.setForeground(Color.WHITE);
                declineButton.setBackground(new Color(209, 60, 62));

            }
        });
    }

}
