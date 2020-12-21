package GUI;

import Model.DatabaseOperation;
import Model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class AddProductPage {
    private JButton button1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
   // private JComboBox comboBox1;
    private JButton addButton;
    private JPanel addPanel;
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
    private ArrayList<String> productPhotos =new ArrayList<>();



    public JPanel getAddPanel() {
        return addPanel;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public AddProductPage(DatabaseOperation operation, Student student,JFrame frame) {


        comboBox1.addItem("Furniture");
        comboBox1.addItem("Ticket");
        comboBox1.addItem("Book");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //if no photo is uploaded, user will not be able to add a new product
                if(button1.isBorderPainted()){
                    JOptionPane.showMessageDialog(null, "You must upload a photo to add a product.");
                }
                else if(textField1.getText().equals("")) { //if no title is written for the product, user will not be able to add a new product
                    JOptionPane.showMessageDialog(null, "Title field cannot be blank.");
                }
                else if(textField2.getText().equals("")) {//if no price is written for the product, user will not be able to add a new product
                    JOptionPane.showMessageDialog(null, "Price field cannot be blank.");
                }
                else if(isNumeric(textField2.getText())){ //the price is not entered as a numerical value
                    JOptionPane.showMessageDialog(null, "Price must be a numerical value.");
                }
                else if(!comboBox1.isCursorSet()) {//if no label is chosen for the product, user will not be able to add a new product
                    JOptionPane.showMessageDialog(null, "You must select a label.");
                }
                else if(textArea1.getText().equals("")) {//if no description is written for the product, user will not be able to add a new product
                    JOptionPane.showMessageDialog(null, "You must provide a description for the product.");
                }
                else { // else push the product

                    productCategory = (String) comboBox1.getSelectedItem();
                    if (productCategory.equals("Book")) {
                        operation.push_product(textField1.getText(), "book", Double.parseDouble(textField2.getText()), student.getStudentEmail(), textArea1.getText(), productPhotos);
                    } else if (productCategory.equals("Ticket")) {

                        operation.push_product(textField1.getText(), "ticket", Double.parseDouble(textField2.getText()), student.getStudentEmail(), textArea1.getText(), productPhotos);

                    } else if (productCategory.equals("Furniture")) {

                        operation.push_product(textField1.getText(), "furniture", Double.parseDouble(textField2.getText()), student.getStudentEmail(), textArea1.getText(), productPhotos);

                    }

                    frame.getContentPane().removeAll();

                }

            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                j.showSaveDialog(null);
                photo = j.getSelectedFile();
                ImageIcon icon = new ImageIcon(photo.getAbsolutePath());
                productPhotos.add(photo.getAbsolutePath());
                button1.setText(null);
                button1.setBackground(new java.awt.Color(187,187,187));
                button1.setOpaque(true);
                button1.setBorderPainted(false);
                button1.setIcon(icon);

            }
        });

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                addButton.setBackground(Color.white);
                addButton.setForeground(new Color(163,0,80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                addButton.setBackground(new Color(163,0,80));
                addButton.setForeground(Color.white);
            }
        });
    }
}
