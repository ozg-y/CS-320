package GUI;

import Model.DatabaseOperation;

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
    void profile() {}
    void returnHome() {}


    public LPanel(JFrame frame,DatabaseOperation operation, Garage garage) {


        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductPage productp = new ProductPage();
                frame.getContentPane().removeAll();
                frame.repaint();
                frame.getContentPane().add(productp.getProductppanel());
                frame.revalidate();
            }
        });
        profilePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfilePage profilep = new ProfilePage();
                frame.getContentPane().removeAll();
                frame.repaint();
                frame.getContentPane().add(profilep.getProfileppanel());
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
