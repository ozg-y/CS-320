package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;

import Model.Student;
import Model.DatabaseOperation;

public class SignUpPage {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton signUpButton;
    private JButton photoButton;
    private JTextField textField2;
    private JTextField textField3;
    private DatabaseOperation operation;
    private String studentName;
    private String studentSurname;
    private String studentProfilePhoto;
    private String studentEmail;
    private File photo;

    public JPanel getSignUpPage() {
        return panel1;
    }

    public SignUpPage() {
        signUpButton.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password1 = passwordField1.getText();
                String password2 = passwordField2.getText();

                if(password1.equals(password2)) {
                      studentName = textField2.getText();
                      studentSurname = textField3.getText();
                      studentProfilePhoto = photo.getAbsolutePath();
                      studentEmail = textField1.getText();

                    operation.push_student(studentName,studentSurname,studentProfilePhoto,studentEmail,password1);
                    SignUpConfirmPage confirm = new SignUpConfirmPage();
                    panel1.removeAll();
                    panel1.add(confirm.getpanelC());
                    }
                }


        });
        photoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                j.showSaveDialog(null);
                photo = j.getSelectedFile();
                ImageIcon icon = new ImageIcon(photo.getAbsolutePath());
                photoButton.setBackground(new java.awt.Color(187,187,187));
                photoButton.setOpaque(true);
                photoButton.setBorderPainted(false);
                photoButton.setIcon(icon);
            }
        });


    }
}
