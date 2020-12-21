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

public class LPanel {

    private HomePage homePage;
    private JTextField searchBar;
    private JButton profile;
    private JButton electronics;
    private JButton tickets;
    private JButton furniture;
    private JButton other;
    private JComboBox filter;
    private JButton home;
    private JButton category1IconButton;
    private JButton category2IconButton;
    private JButton category3IconButton;
    private JButton profilePhotoButton;
    private JButton addProduct;
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

        addProduct.addActionListener(new ActionListener() {
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
                System.out.println("LPanel started");
                garage.update_garage("book");
                System.out.println("LPanel ended");
            }
        });

        category2IconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("LPanel started");
                garage.update_garage("furniture");
                System.out.println("LPanel ended");
            }
        });

        category3IconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("LPanel started");
                garage.update_garage("ticket");
                System.out.println("LPanel ended");
            }
        });

        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                garage.update_garage("ALL");
                frame.getContentPane().removeAll();

                frame.getContentPane().add(garage.productPanel, BorderLayout.CENTER);
                frame.getContentPane().add(lPanel, BorderLayout.WEST);
                frame.pack();
                frame.repaint();
                frame.revalidate();
            }
        });

        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ((JButton)e.getSource()).setBackground(Color.white);
                ((JButton)e.getSource()).setForeground(new Color(163,0,80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                ((JButton)e.getSource()).setBackground(new Color(163,0,80));
                ((JButton)e.getSource()).setForeground(Color.white);
            }
        };
        home.addMouseListener(listener);
        addProduct.addMouseListener(listener);
    }

    public static ImageIcon scaleFile(int width, int height, String filename) {
        String path = System.getProperty("user.dir");

        String OS = System.getProperty("os.name");
        System.out.println(OS);

        if (OS.contains("Mac")) {
            File file = new File(path + "/src/Icons/" + filename);
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());Image transformed = icon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
            icon = new ImageIcon(transformed);
            return icon;
        } else {
            File file = new File(path + "\\src\\Icons\\" + filename);
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image transformed = icon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
            icon = new ImageIcon(transformed);
            return icon;
        }
    }
}
