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


public class LPanel {

    public JButton category1IconButton;
    public JButton category2IconButton;
    public JButton category3IconButton;
    public JPanel lPanel;
    private HomePage homePage;
    private JTextField searchBar;
    private JButton profile;
    private JButton electronics;
    private JButton tickets;
    private JButton furniture;
    private JButton other;
    private JComboBox filter;
    private JButton home;
    private JButton profilePhotoButton;
    private JButton addProduct;
    private final JFrame frame;
    private final DatabaseOperation operation;
    private final Garage garage;
    private final Student student;


    public LPanel(JFrame frame, DatabaseOperation operation, Garage garage, Student student) {
        this.frame = frame;
        this.operation = operation;
        this.garage = garage;
        this.student = student;

        ImageIcon PIcon = new ImageIcon((student.getStudentProfilePhoto()).getImage().getScaledInstance(195, 220, Image.SCALE_SMOOTH));
        profilePhotoButton.setIcon(PIcon);

        ImageIcon bIcon = scaleFile(200, 210, "book.png");
        category1IconButton.setIcon(bIcon);

        ImageIcon fIcon = scaleFile(250, 150, "furniture2.png");
        category2IconButton.setIcon(fIcon);

        ImageIcon tIcon = scaleFile(135, 135, "ticket.png");
        category3IconButton.setIcon(tIcon);

        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                category1IconButton.setEnabled(false);
                category2IconButton.setEnabled(false);
                category3IconButton.setEnabled(false);

                AddProductPage addProductPage = new AddProductPage(operation, student, frame);

                frame.getContentPane().removeAll();
                frame.setLayout(new BorderLayout());

                frame.getContentPane().add(addProductPage.getAddPanel(), BorderLayout.CENTER);
                frame.getContentPane().add(lPanel, BorderLayout.WEST);
                frame.pack();
                frame.repaint();
                frame.revalidate();

            }
        });

        profilePhotoButton.addActionListener(new profilePageListener());

        category1IconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                garage.filterOption = "book";
                garage.update_garage("book");
                frame.getContentPane().removeAll();
                frame.setLayout(new BorderLayout());

                frame.getContentPane().add(garage.getProductPanel(), BorderLayout.CENTER);
                frame.getContentPane().add(lPanel, BorderLayout.WEST);
                frame.pack();
                frame.repaint();
                frame.revalidate();
            }
        });

        category2IconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                garage.filterOption = "furniture";
                garage.update_garage("furniture");
                frame.getContentPane().removeAll();
                frame.setLayout(new BorderLayout());


                frame.getContentPane().add(garage.getProductPanel(), BorderLayout.CENTER);
                frame.getContentPane().add(lPanel, BorderLayout.WEST);
                frame.pack();
                frame.repaint();
                frame.revalidate();
            }
        });

        category3IconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                garage.filterOption = "ticket";
                garage.update_garage("ticket");
                frame.getContentPane().removeAll();
                frame.setLayout(new BorderLayout());


                frame.getContentPane().add(garage.getProductPanel(), BorderLayout.CENTER);
                frame.getContentPane().add(lPanel, BorderLayout.WEST);
                frame.pack();
                frame.repaint();
                frame.revalidate();

            }
        });

        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                category1IconButton.setEnabled(true);
                category2IconButton.setEnabled(true);
                category3IconButton.setEnabled(true);

                garage.pageNumber = 1;
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
                ((JButton) e.getSource()).setBackground(Color.white);
                ((JButton) e.getSource()).setForeground(new Color(163, 0, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                ((JButton) e.getSource()).setBackground(new Color(163, 0, 80));
                ((JButton) e.getSource()).setForeground(Color.white);
            }
        };
        home.addMouseListener(listener);
        addProduct.addMouseListener(listener);
    }

    public static ImageIcon scaleFile(int width, int height, String filename) {
        String path = System.getProperty("user.dir");
        String OS = System.getProperty("os.name");

        if (OS.contains("Mac") || OS.contains("Linux")) {
            File file = new File(path + "/src/Icons/" + filename);
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image transformed = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon = new ImageIcon(transformed);
            return icon;
        } else {
            File file = new File(path + "\\src\\Icons\\" + filename);
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image transformed = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon = new ImageIcon(transformed);
            return icon;
        }
    }

    public JPanel getLPanel() {
        return lPanel;
    }

    public boolean goToProfilePage() {

        try {
            category1IconButton.setEnabled(false);
            category2IconButton.setEnabled(false);
            category3IconButton.setEnabled(false);

            ProfilePage profilePage = new ProfilePage(frame, operation, student);

            frame.getContentPane().removeAll();
            frame.setLayout(new BorderLayout());

            frame.getContentPane().add(profilePage.getProfilePPanel(), BorderLayout.CENTER);
            frame.getContentPane().add(lPanel, BorderLayout.WEST);
            frame.pack();
            frame.repaint();
            frame.revalidate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public class profilePageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            goToProfilePage();

        }
    }
}
