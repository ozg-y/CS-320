package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import Model.DatabaseOperation;
import GUI.LoginPage;



public class SignUpConfirmPage {
    private DatabaseOperation operation;
    private JPanel panelC;
    public JPanel getpanelC(){
        return panelC;
    }
    private JButton button1;
    private JTextField code;

    public SignUpConfirmPage(JFrame frame, DatabaseOperation operation,String studentEmail) {
        this.operation = operation;

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = code.getText();
                int confirmation_code = Integer.parseInt(text);

                try {
                    if(confirmation_code == operation.pull_student_confirmation_code(studentEmail)){
                        operation.confirmed_new_stundet(studentEmail);

                        LoginPage reLogin = new LoginPage(frame,operation);
                        frame.getContentPane().removeAll();
                        frame.repaint();

                        frame.getContentPane().add(reLogin.getMainPanel());
                        frame.revalidate();
                    }
                    else{
                        code.setText("");
                        JOptionPane.showMessageDialog(null, "Incorrect Code","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
