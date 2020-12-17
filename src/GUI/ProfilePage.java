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

    public ProfilePage(JFrame frame, DatabaseOperation operation) {

        Student student = new Student();
        name.setText(student.getStudentName()+student.getStudentSurname());
        email.setText(student.getStudentEmail());

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
                ChangePasswordPage passwordPage = new ChangePasswordPage(frame, operation);
                frame.getContentPane().removeAll();
                frame.repaint();
                frame.getContentPane().add(passwordPage.getPasswordPanel());
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
