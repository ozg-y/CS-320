package GUI;

import Model.DatabaseOperation;
import Model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class ProfilePage {
    private final JFrame frame;
    private final DatabaseOperation operation;
    private final Student student;
    private JButton profilePhotoButton;
    private JButton changePasswordButton;
    private JPanel profilePPanel;
    private JLabel name;
    private JLabel email;
    private File photo;

    public ProfilePage(JFrame frame, DatabaseOperation operation, Student student) {
        this.frame = frame;
        this.operation = operation;
        this.student = student;

        name.setText(student.getStudentName() + " " + student.getStudentSurname());
        email.setText(student.getStudentEmail());
        ImageIcon PIcon = new ImageIcon((student.getStudentProfilePhoto()).getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH));

        if (PIcon != null) {
            profilePhotoButton.setIcon(PIcon);
            profilePhotoButton.setText("");
        }

        profilePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                j.showSaveDialog(null);
                photo = j.getSelectedFile();
                changeProfilePhoto(photo);
            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePasswordPage passwordPage = new ChangePasswordPage(frame, operation, student);

                frame.getContentPane().removeAll();
                frame.setLayout(new BorderLayout());
                frame.repaint();

                Garage garage = new Garage(frame, operation, student);
                LPanel lPanel = new LPanel(frame, operation, garage, student);
                frame.getContentPane().add(passwordPage.getPasswordPanel(), BorderLayout.CENTER);
                frame.getContentPane().add(lPanel.lPanel, BorderLayout.WEST);
                frame.pack();
                frame.repaint();
                frame.revalidate();
            }
        });
        changePasswordButton.addMouseListener(new ButtonColorListener());

    }

    public boolean changeProfilePhoto(File photo) {

        if (photo == null) {
            JOptionPane.showMessageDialog(null, "Choose a photo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {

            ImageIcon icon = new ImageIcon(photo.getAbsolutePath());
            student.setStudentProfilePhoto(icon);
            operation.change_student_photo(student.getStudentEmail(), photo.getAbsolutePath());

            profilePhotoButton.setText(null);
            profilePhotoButton.setBackground(new java.awt.Color(187, 187, 187));
            profilePhotoButton.setOpaque(true);
            profilePhotoButton.setBorderPainted(false);
            profilePhotoButton.setIcon(icon);

            frame.getContentPane().removeAll();
            frame.setLayout(new BorderLayout());

            Garage garage = new Garage(frame, operation, student);
            LPanel lPanel = new LPanel(frame, operation, garage, student);

            frame.getContentPane().add(garage.getProductPanel(), BorderLayout.CENTER);
            frame.getContentPane().add(lPanel.lPanel, BorderLayout.WEST);

            frame.pack();
            frame.repaint();
            frame.revalidate();

            return true;
        }
    }

    public JPanel getProfilePPanel() {
        return profilePPanel;
    }

}
