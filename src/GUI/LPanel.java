package GUI;

import Model.DatabaseOperation;
import Model.Student;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    public JPanel lPanel;
    private ArrayList<Integer> productIds = new ArrayList<>();
    private ArrayList<ImageIcon> productImages = new ArrayList<>();


    public JPanel getlPanel() {
        return lPanel;
    }

    public LPanel(JFrame frame, DatabaseOperation operation, Garage garage, Student student) {

        profilePhotoButton.setIcon(student.getStudentProfilePhoto());

        ImageIcon eIcon = scaleFile(150,150,"electronics.png");
        category1IconButton.setIcon(eIcon);

        ImageIcon fIcon = scaleFile(150,150,"furniture.png");
        category2IconButton.setIcon(fIcon);

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddProductPage addProductPage = new AddProductPage(operation, student, frame);

                frame.getContentPane().removeAll();
                frame.setLayout(new BorderLayout());
                frame.repaint();

                frame.getContentPane().add(addProductPage.getAddPanel(), BorderLayout.CENTER);
                frame.getContentPane().add(lPanel, BorderLayout.WEST);
                frame.pack();
                frame.repaint();
                frame.revalidate();
                frame.setSize(1400, 900);

            }
        });
        profilePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfilePage profilep = new ProfilePage(frame, operation, student);

                frame.getContentPane().removeAll();
                frame.setLayout(new BorderLayout());
                frame.repaint();


                frame.getContentPane().add(profilep.getProfilePPanel(), BorderLayout.CENTER);
                frame.getContentPane().add(lPanel, BorderLayout.WEST);
                frame.pack();
                frame.repaint();
                frame.revalidate();
                frame.setSize(1400, 900);

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

    public static ImageIcon scaleFile(int width, int height, String filename) {
        String path = System.getProperty("user.dir");

        String OS = System.getProperty("os.name");
        System.out.println(OS);

        if (OS.contains("Mac")) {
            File file = new File(path + "/src/Icons/" + filename);
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image transformed = icon.getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH);
            icon = new ImageIcon(transformed);
            return icon;
        } else {
            File file = new File(path + "\\src\\Icons\\" + filename);
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image transformed = icon.getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH);
            icon = new ImageIcon(transformed);
            return icon;
        }
    }
}
