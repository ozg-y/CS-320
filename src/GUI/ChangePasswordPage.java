package GUI;

import Model.DatabaseOperation;
import Model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChangePasswordPage {
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton changePasswordButton;
    private Student student;
    private DatabaseOperation operation;

    public ChangePasswordPage() {
        Student student= new Student();
        this.student=student;
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword1=passwordField1.getText();
                String newPassword2=passwordField2.getText();

                if(newPassword1.equals(newPassword2)){
                    student.setStudentPassword(newPassword1);
                    operation.change_student_password(student.getStudentEmail(), newPassword1);
                    JOptionPane.showMessageDialog(null, "Password updated.");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match each other.");
                    passwordField1.setText("");
                    passwordField2.setText("");
                }
            }
        });
        changePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                changePasswordButton.setBackground(Color.white);
                changePasswordButton.setForeground(new Color(163,0,80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                changePasswordButton.setBackground(new Color(163,0,80));
                changePasswordButton.setForeground(Color.white);
            }
        });
    }
}
