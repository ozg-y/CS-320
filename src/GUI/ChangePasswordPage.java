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
    private final Student student;
    private final DatabaseOperation operation;
    private final JFrame frame;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton changePasswordButton;
    private JPanel passwordPanel;

    public ChangePasswordPage(JFrame frame, DatabaseOperation operation, Student student) {

        this.student = student;
        this.operation = operation;
        this.frame = frame;

        changePasswordButton.addActionListener(new changePasswordListener());
        changePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                changePasswordButton.setBackground(Color.white);
                changePasswordButton.setForeground(new Color(163, 0, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                changePasswordButton.setBackground(new Color(163, 0, 80));
                changePasswordButton.setForeground(Color.white);
            }
        });
    }

    public boolean changePassword(String newPassword1, String newPassword2) {

        if (newPassword1.equals("") || newPassword2.equals("") || newPassword1.trim().isEmpty() || newPassword2.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password fields cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            passwordField1.setText("");
            passwordField2.setText("");
            return false;
        } else if (newPassword1.equals(newPassword2)) {
            student.setStudentPassword(newPassword1);
            operation.change_student_password(student.getStudentEmail(), newPassword1);
            JOptionPane.showMessageDialog(null, "Password updated.", "Success", JOptionPane.ERROR_MESSAGE);
            return true;
        } else { //if the passwords do not match each other, error
            JOptionPane.showMessageDialog(null, "Passwords do not match each other.", "Error", JOptionPane.ERROR_MESSAGE);
            passwordField1.setText("");
            passwordField2.setText("");
            return false;
        }
    }

    public JPanel getPasswordPanel() {
        return passwordPanel;
    }

    public class changePasswordListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            changePassword(passwordField1.getText(), passwordField2.getText());
        }
    }
}
