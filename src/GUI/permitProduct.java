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

public class permitProduct {
    private final DatabaseOperation operation;
    public JPanel permitPanel;
    public boolean isOK = false;
    private JTextArea productDescription;
    private JButton nextButton;
    private JLabel productPhoto;
    private JLabel productName;
    private JLabel productCategory;
    private JLabel productPrice;
    private JLabel productSeller;
    private JButton permitButton;
    private JButton declineButton;
    private JButton goToLogin;
    private ArrayList<InputStream> productPhotos = new ArrayList<>();
    private ArrayList<Integer> productIDs = new ArrayList<>();
    private int productID;
    private int index = 0;
    private int size = 0;


    public permitProduct(JFrame frame, DatabaseOperation operation, LoginPage loginPage) {

        this.operation = operation;

        try {
            productIDs = operation.pull_product_permitted();
            productPhotos = operation.pull_product_permitted_photos(productIDs.get(index));
            productID = productIDs.get(index);

            size = productIDs.size();
            Product product = operation.pull_product(productIDs.get(index));
            Image icon = ImageIO.read(productPhotos.get(index));
            ImageIcon icon2 = new ImageIcon(icon);

            productPhoto.setIcon(icon2);
            productName.setText(product.getProductName());
            productSeller.setText(product.getProductSeller().getStudentEmail());
            productPrice.setText(Double.toString(product.getProductPrice()));
            productDescription.setText(product.getProductDescription());

            index++;
            isOK = true;
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "There are no products waiting to be permitted");
            isOK = false;

        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
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
                try {
                    if (index == size) {
                        nextButton.setEnabled(false);
                    } else {
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

                        index++;
                    }
                } catch (IOException ioException) {
                    nextButton.setEnabled(false);
                    ioException.printStackTrace();
                }
            }
        });

        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextButton.setForeground(new Color(0, 32, 96));
                nextButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
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
                permitButton.setForeground(new Color(60, 163, 209));
                permitButton.setBackground(Color.WHITE);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                permitButton.setForeground(Color.WHITE);
                permitButton.setBackground(new Color(60, 163, 209));
            }
        });
    }

}
