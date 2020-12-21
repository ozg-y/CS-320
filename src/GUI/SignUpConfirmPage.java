package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import Model.DatabaseOperation;


public class SignUpConfirmPage {
    private DatabaseOperation operation;
    private JPanel panelC;
    public JPanel getpanelC(){
        return panelC;
    }
    private JButton submitButton;
    private JTextField code;

    public SignUpConfirmPage(JFrame frame, DatabaseOperation operation,String studentEmail) {
        this.operation = operation;

        submitButton.addActionListener(new ActionListener() {
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
                submitButton.setForeground(new Color(163,0,80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                submitButton.setBackground(new Color(163,0,80));
                submitButton.setForeground(Color.white);
            }
        });
    }
}
