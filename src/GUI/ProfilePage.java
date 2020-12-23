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


public class ProfilePage {
    private JButton profilePhotoButton;
    private JButton changePasswordButton;
    private JPanel profilePPanel;
    private JLabel name;
    private JLabel email;
    private File photo;

    public JPanel getProfilePPanel(){
        return profilePPanel;
    }

    public ProfilePage(JFrame frame, DatabaseOperation operation, Student student) {

        name.setText(student.getStudentName() + " " + student.getStudentSurname());
        email.setText(student.getStudentEmail());
        ImageIcon PIcon = new ImageIcon((student.getStudentProfilePhoto()).getImage().getScaledInstance(250,300,Image.SCALE_SMOOTH));
        profilePhotoButton.setIcon(PIcon);

        profilePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                j.showSaveDialog(null);
                photo = j.getSelectedFile();
                ImageIcon icon = new ImageIcon(photo.getAbsolutePath());
                profilePhotoButton.setText(null);
                profilePhotoButton.setBackground(new java.awt.Color(187,187,187));
                profilePhotoButton.setOpaque(true);
                profilePhotoButton.setBorderPainted(false);
                profilePhotoButton.setIcon(icon);
            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePasswordPage passwordPage = new ChangePasswordPage(frame, operation,student);

                frame.getContentPane().removeAll();
                frame.setLayout(new BorderLayout());
                frame.repaint();

                Garage garage = new Garage(frame, operation, student);
                LPanel lPanel = new LPanel(frame,operation,garage, student);
                frame.getContentPane().add(passwordPage.getPasswordPanel(), BorderLayout.CENTER);
                frame.getContentPane().add(lPanel.lPanel, BorderLayout.WEST);
                frame.pack();
                frame.repaint();
                frame.revalidate();
            }
        });

        changePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                changePasswordButton.setBackground(Color.white);
                changePasswordButton.setForeground(new Color(163,0,80));
            }
        });

        changePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                changePasswordButton.setBackground(new Color(163,0,80));
                changePasswordButton.setForeground(Color.white);
            }
        });


    }


}
