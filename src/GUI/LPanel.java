package GUI;

import Model.DatabaseOperation;
import Model.Student;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LPanel {

    private HomePage homePage;
    private JTextField searchBar;
    private JButton profile;
    private JButton electronics;
    private JButton tickets;
    private JButton furniture;
    private JButton other;
    private JButton addProduct;
    private JComboBox filter;
    private JButton refresh;
    private JButton home;
    private JButton category1IconButton;
    private JButton category2IconButton;
    private JButton category3IconButton;
    private JButton profilePhotoButton;
    private JButton addProductButton;
    public JPanel lpanel;
    private ArrayList<Integer> productIds = new ArrayList<>();
    private ArrayList<ImageIcon> productImages = new ArrayList<>();

    public JPanel getLpanel(){
        return lpanel;
    }

    void refreshGarage() {}


    public LPanel(JFrame frame,DatabaseOperation operation, Garage garage, Student student) {


        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProductPage addProductPage = new AddProductPage(operation, student);
                frame.getContentPane().removeAll();
                frame.repaint();
                frame.getContentPane().add(addProductPage.getAddPanel());
                frame.revalidate();
            }
        });
        profilePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfilePage profilep = new ProfilePage(frame, operation);
                frame.getContentPane().removeAll();
                frame.repaint();
                frame.getContentPane().add(profilep.getProfilePPanel());
                frame.revalidate();
            }
        });
        category1IconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                ResultSet set = operation.book_category();
                 while(set.next()){
                    productIds.add(set.getInt("productID"));
                 }
                    set = operation.book_photos();
                 while(set.next()){
                     ImageIcon icon = new ImageIcon(ImageIO.read(set.getBinaryStream("productPhotos"))) ;
                     productImages.add(icon);
                 }
                 garage.setProductIds(productIds);
                 garage.setProductImages(productImages);
                 garage.Refresh();
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        category2IconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ResultSet set = operation.ticket_category();
                    while(set.next()){
                        productIds.add(set.getInt("productID"));
                    }
                    set = operation.ticket_photos();
                    while(set.next()){
                        ImageIcon icon = new ImageIcon(ImageIO.read(set.getBinaryStream("productPhotos"))) ;
                        productImages.add(icon);
                    }
                    garage.setProductIds(productIds);
                    garage.setProductImages(productImages);
                    garage.Refresh();
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        category3IconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ResultSet set = operation.furniture_category();
                    while(set.next()){
                        productIds.add(set.getInt("productID"));
                    }
                    set = operation.furniture_photos();
                    while(set.next()){
                        ImageIcon icon = new ImageIcon(ImageIO.read(set.getBinaryStream("productPhotos"))) ;
                        productImages.add(icon);
                    }
                    garage.setProductIds(productIds);
                    garage.setProductImages(productImages);
                    garage.Refresh();
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

}
