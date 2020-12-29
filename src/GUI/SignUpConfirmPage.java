package GUI;

import Model.DatabaseOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


public class SignUpConfirmPage {
    private DatabaseOperation operation;
    private JPanel panelC;
    private JButton submitButton;
    private JTextField code;

    int errorCounter = 0;
    public SignUpConfirmPage(JFrame frame, DatabaseOperation operation, String studentEmail) {
        this.operation = operation;

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = code.getText();

                int confirmation_code = Integer.parseInt(text);

                try {
                    if (confirmation_code == operation.pull_student_confirmation_code(studentEmail)) {
                        operation.confirmed_new_student(studentEmail);

                        LoginPage reLogin = new LoginPage(frame, operation);
                        frame.getContentPane().removeAll();
                        frame.repaint();

                        frame.getContentPane().add(reLogin.getMainPanel());
                        frame.revalidate();
                    } else {
                        code.setText("");
                        errorCounter++;

                        JOptionPane.showMessageDialog(null, "Incorrect Code", "Error", JOptionPane.ERROR_MESSAGE);

                        if(errorCounter == 3) {
                            JOptionPane.showMessageDialog(null, "Wrong confirmation code", "Error", JOptionPane.ERROR_MESSAGE);

                            LoginPage reLogin = new LoginPage(frame, operation);
                            frame.getContentPane().removeAll();
                            frame.repaint();

                            frame.getContentPane().add(reLogin.getMainPanel());
                            frame.revalidate();
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                submitButton.setBackground(Color.white);
                submitButton.setForeground(new Color(163, 0, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                submitButton.setBackground(new Color(163, 0, 80));
                submitButton.setForeground(Color.white);
            }
        });
    }

    public JPanel getpanelC() {
        return panelC;
    }
}
