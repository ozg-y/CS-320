package GUI;

import Model.DatabaseOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    public JPanel getLpanel(){
        return lpanel;
    }

    void refreshGarage() {}
    void profile() {}
    void returnHome() {}


    public LPanel(JFrame frame,DatabaseOperation operation) {


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

            }
        });
        category2IconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        category3IconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

}
