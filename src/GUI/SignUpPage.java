package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import Model.Student;
import Model.DatabaseOperation;

public class SignUpPage {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton signUpButton;
    private DatabaseOperation operation;
    private String studentName;
    private String studentSurname;
    private InputStream studentProfilePhoto;
    private String studentEmail;
    private String studentPassword;


    public SignUpPage() {
        signUpButton.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password1 = passwordField1.getText();
                String password2 = passwordField2.getText();

                if(password1.equals(password2)) {
                    Student student = new Student(studentName,studentSurname,studentProfilePhoto,studentEmail,studentPassword);
                      studentName = student.getStudentName();
                      studentSurname = student.getStudentSurname();
                      studentProfilePhoto = student.getStudentProfilePhoto();
                      studentEmail = student.getStudentEmail();
                      studentPassword = student.getStudentPassword();

                    operation.push_student(studentName,studentSurname,studentProfilePhoto,studentEmail,studentPassword){
                    //studentProfilePhoto string
                        SignUpConfirmPage confirm = new SignUpConfirmPage();
                        panel1.removeAll();
                        panel1.add(confirm.getpanelC());
                    }
                }

            }
        });
    }
}
