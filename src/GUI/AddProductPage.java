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
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddProductPage {
    private JButton productPhotoButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JButton addButton;
    private JPanel addPanel;
    private File photo;
    private JComboBox<String> comboBox1;
    private File productPhoto;
    private String productCategory;



    public AddProductPage(DatabaseOperation operation, Student student, JFrame frame) {


        comboBox1.addItem("Furniture");
        comboBox1.addItem("Ticket");
        comboBox1.addItem("Book");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (productPhotoButton.getIcon() == null) {
                    JOptionPane.showMessageDialog(null, "You must upload a photo to add a product.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (textField1.getText().equals("")) { //if no title is written for the product, user will not be able to add a new product
                    JOptionPane.showMessageDialog(null, "Title field cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (textField2.getText().equals("")) {//if no price is written for the product, user will not be able to add a new product
                    JOptionPane.showMessageDialog(null, "Price field cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!isNumeric(textField2.getText())) { //the price is not entered as a numerical value
                    JOptionPane.showMessageDialog(null, "Price must be a numerical value.", "Error", JOptionPane.ERROR_MESSAGE);
//                } else if (!comboBox1.isCursorSet()) {//if no label is chosen for the product, user will not be able to add a new product
//                    JOptionPane.showMessageDialog(null, "You must select a label.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (textArea1.getText().equals("")) {//if no description is written for the product, user will not be able to add a new product
                    JOptionPane.showMessageDialog(null, "You must provide a description for the product.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    productCategory = (String) comboBox1.getSelectedItem();
                    if (productCategory.equals("Book")) {
                        operation.push_product(textField1.getText(), "book", Double.parseDouble(textField2.getText()), student.getStudentEmail(), textArea1.getText(), productPhoto);
                    } else if (productCategory.equals("Ticket")) {
                        operation.push_product(textField1.getText(), "ticket", Double.parseDouble(textField2.getText()), student.getStudentEmail(), textArea1.getText(), productPhoto);
                    } else if (productCategory.equals("Furniture")) {
                        operation.push_product(textField1.getText(), "furniture", Double.parseDouble(textField2.getText()), student.getStudentEmail(), textArea1.getText(), productPhoto);
                    }

                    frame.getContentPane().removeAll();
                    Garage garage = new Garage(frame, operation, student);
                    LPanel lPanel = new LPanel(frame, operation, garage, student);
                    frame.getContentPane().add(garage.productPanel, BorderLayout.CENTER);
                    frame.getContentPane().add(lPanel.lPanel, BorderLayout.WEST);
                    frame.pack();
                    frame.repaint();
                    frame.revalidate();
                }
            }

        });

        productPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                j.showSaveDialog(null);
                photo = j.getSelectedFile();

                ImageIcon icon = new ImageIcon(new ImageIcon(photo.getAbsolutePath()).getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));

                try {
                    ImageIO.write((RenderedImage) productPhoto,"png",j.getSelectedFile());
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null,"Error Occured When Loading Photo.Please Try Again","Error",JOptionPane.ERROR_MESSAGE);
                }

                productPhotoButton.setText(null);
                productPhotoButton.setBackground(new java.awt.Color(187, 187, 187));
                productPhotoButton.setOpaque(true);
                productPhotoButton.setBorderPainted(false);
                productPhotoButton.setIcon(icon);

            }
        });

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                addButton.setBackground(Color.white);
                addButton.setForeground(new Color(163, 0, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                addButton.setBackground(new Color(163, 0, 80));
                addButton.setForeground(Color.white);
            }
        });

        productPhotoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                productPhotoButton.setBackground(Color.white);
                productPhotoButton.setForeground(new Color(163, 0, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                productPhotoButton.setBackground(new Color(163, 0, 80));
                productPhotoButton.setForeground(Color.white);
            }
        });
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

    public JPanel getAddPanel() {
        return addPanel;
    }
}
