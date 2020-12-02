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
    private DatabaseOperation op;
    private JPanel panelC;
    public JPanel getpanelC(){
        return panelC;
    }
    private JTextField textField1;
    private JButton button1;
    private DatabaseOperation operation;
    private JTextField email;
    private JTextField code;

    public SignUpConfirmPage() {

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mail = email.getText();
                String text = code.getText();
                int confirmation_code = Integer.parseInt(text);

                try {
                    if(confirmation_code == op.pull_student_confirmation_code(mail)){
                        LoginPage login = new LoginPage();
                        panelC.removeAll();
                        panelC.add(login.getMainPanel());
                    }else{
                        textField1.setText("");
                        code.setText("");
                        JOptionPane.showMessageDialog(null, "Incorrect Code");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }
}
