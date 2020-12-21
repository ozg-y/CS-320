package GUI;

import Model.DatabaseOperation;
import Model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.InputMismatchException;

public class ChangePasswordPage {
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton changePasswordButton;
    private JPanel passwordPanel;
    private Student student;
    private DatabaseOperation operation;

    public JPanel getPasswordPanel() {
        return passwordPanel;
    }

    public ChangePasswordPage(JFrame frame,DatabaseOperation operation,Student student) {

        this.student=student;
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword1=passwordField1.getText();
                String newPassword2=passwordField2.getText();

                if(newPassword1.equals(newPassword2)){
                    System.out.println(newPassword1);
                    student.setStudentPassword(newPassword1);
                    operation.change_student_password(student.getStudentEmail(), newPassword1);
                    JOptionPane.showMessageDialog(null, "Password updated.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(newPassword1.equals("") || newPassword2.equals("") || newPassword1.trim().isEmpty() || newPassword2.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Password fields cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
                    passwordField1.setText("");
                    passwordField2.setText("");
                }
                else { //if the passwords do not match each other, error
                    JOptionPane.showMessageDialog(null, "Passwords do not match each other.", "Error", JOptionPane.ERROR_MESSAGE);
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
