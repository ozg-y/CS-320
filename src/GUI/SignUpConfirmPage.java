package GUI;

import Model.DatabaseOperation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class SignUpConfirmPage {
    int errorCounter = 0;
    private final DatabaseOperation operation;
    private JPanel panelC;
    private JButton submitButton;
    private JTextField code;

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

                        if (errorCounter == 3) {
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

        submitButton.addMouseListener(new ButtonColorListener());
    }

    public JPanel getpanelC() {
        return panelC;
    }
}
