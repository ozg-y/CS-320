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

    public SignUpConfirmPage(JFrame frame, DatabaseOperation operation,String mail) {
        this.operation = operation;

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = code.getText();
                int confirmation_code = Integer.parseInt(text);

                try {
                    if(confirmation_code == operation.pull_student_confirmation_code(mail)){
                        System.out.println("EŞLEŞTİ...");
                    }else{
                        System.out.println("EŞLEŞMEDİ...");
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
