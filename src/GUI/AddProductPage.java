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


public class AddProductPage {
    private JButton button1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
   // private JComboBox comboBox1;
    private JButton ADDButton;
    private JPanel addppanel;
    private File photo;
    private JComboBox<String> comboBox1;
    private String productPhoto;
    private String productTitle;
    private double productPrice;
    private String productCategory;
    private String productDescription;
    private ArrayList<Integer> productIds = new ArrayList<>();
    private ArrayList<ImageIcon> productImages = new ArrayList<>();
    private DatabaseOperation operation;

    public AddProductPage(DatabaseOperation operation) {
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productCategory = (String) comboBox1.getSelectedItem();
                if (productCategory.equals("Book")) {
                    operation.push_product(textField1.getText(), "book", Double.parseDouble(textField2.getText(), productSeller, textArea1.getText()));
                } else if (productCategory.equals("Ticket")) {

                    operation.push_product(textField1.getText(), "ticket", Double.parseDouble(textField2.getText(), productSeller, textArea1.getText()));

                } else if (productCategory.equals("Furniture")) {

                    operation.push_product(textField1.getText(), "furniture", Double.parseDouble(textField2.getText(), productSeller, textArea1.getText()));

                }
            }
        });

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productTitle = textField1.getText();
                productPrice = Double.parseDouble(textField2.getText());
                productDescription = textArea1.getText();
                productCategory = (String)comboBox1.getSelectedItem();

                operation.push_product(productTitle,productCategory,productPrice,productSeller,productDescription,productPhoto);

            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                j.showSaveDialog(null);
                photo = j.getSelectedFile();
                ImageIcon icon = new ImageIcon(photo.getAbsolutePath());
                button1.setText(null);
                button1.setBackground(new java.awt.Color(187,187,187));
                button1.setOpaque(true);
                button1.setBorderPainted(false);
                button1.setIcon(icon);
            }
        });
    }
}
