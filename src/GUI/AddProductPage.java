package GUI;

import Model.DatabaseOperation;
import Model.Student;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class AddProductPage {
    private JButton productPhotoButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JButton addButton;
    private JPanel addPanel;
    private File photo;
    private JComboBox<String> comboBox1;
    private final JFrame frame;
    private final DatabaseOperation operation;
    private final Student student;
    private ImageIcon imageIcon;


    public AddProductPage(DatabaseOperation operation, Student student, JFrame frame) {
        this.operation = operation;
        this.student = student;
        this.frame = frame;

        comboBox1.addItem("Furniture");
        comboBox1.addItem("Ticket");
        comboBox1.addItem("Book");

        addButton.addActionListener(new AddProductListener());
        productPhotoButton.addActionListener(new UploadPhotoListener());

        addButton.addMouseListener(new ButtonColorListener());
        productPhotoButton.addMouseListener(new ButtonColorListener());
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

    public boolean uploadPhoto(String path) {


        BufferedImage in = null;
        try {
            in = ImageIO.read(new File(path));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while loading the photo. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        BufferedImage newImage = new BufferedImage(
                in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(in, 0, 0, null);
        g.dispose();


        try {
            ImageIO.write(newImage, "png", photo);
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null, "Error occurred while loading the photo. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        productPhotoButton.setText(null);
        productPhotoButton.setBackground(new java.awt.Color(187, 187, 187));
        productPhotoButton.setOpaque(true);
        productPhotoButton.setBorderPainted(false);

        imageIcon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
        productPhotoButton.setIcon(imageIcon);
        return true;
    }

    public boolean addProduct(File photo, String title, String price, String description, String productCategory) {

        if (photo == null) {
            JOptionPane.showMessageDialog(null, "You must upload a photo to add a product.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (title.equals("")) { //if no title is written for the product, user will not be able to add a new product
            JOptionPane.showMessageDialog(null, "Title field cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (price.equals("")) {//if no price is written for the product, user will not be able to add a new product
            JOptionPane.showMessageDialog(null, "Price field cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!isNumeric(price)) { //the price is not entered as a numerical value
            JOptionPane.showMessageDialog(null, "Price must be a numerical value.", "Error", JOptionPane.ERROR_MESSAGE);
//
//                } else if (!comboBox1.isCursorSet()) {//if no label is chosen for the product, user will not be able to add a new product
//                    JOptionPane.showMessageDialog(null, "You must select a label.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (description.equals("")) {//if no description is written for the product, user will not be able to add a new product
            JOptionPane.showMessageDialog(null, "You must provide a description for the product.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {

            if (productCategory.equals("Book")) {
                operation.push_product(title, "book", Double.parseDouble(price), student.getStudentEmail(), description, photo);
            } else if (productCategory.equals("Ticket")) {
                operation.push_product(title, "ticket", Double.parseDouble(price), student.getStudentEmail(), description, photo);
            } else if (productCategory.equals("Furniture")) {
                operation.push_product(title, "furniture", Double.parseDouble(price), student.getStudentEmail(), description, photo);
            } else {
                JOptionPane.showMessageDialog(null, "You must choose a category for the product.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            frame.getContentPane().removeAll();
            Garage garage = new Garage(frame, operation, student);
            LPanel lPanel = new LPanel(frame, operation, garage, student);
            frame.getContentPane().add(garage.productPanel, BorderLayout.CENTER);
            frame.getContentPane().add(lPanel.lPanel, BorderLayout.WEST);
            frame.pack();
            frame.repaint();
            frame.revalidate();
            return true;
        }
    }

    public JPanel getAddPanel() {
        return addPanel;
    }

    public class UploadPhotoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser j = new JFileChooser();
            j.showSaveDialog(null);
            photo = j.getSelectedFile();
            uploadPhoto(photo.getAbsolutePath());

        }
    }

    public class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addProduct(photo, textField1.getText(), textField2.getText(), textArea1.getText(), (String) comboBox1.getSelectedItem());
        }
    }
}
