package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Model.DatabaseOperation;

public class LoginPage {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton signUpButton;
    private JPanel MainPanel;
    private DatabaseOperation operation;


    public JPanel getMainPanel(){
        return MainPanel;
    }

    public LoginPage(JFrame frame, DatabaseOperation operation) {
        this.operation = operation;

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo-Burası size emanet controller team
                String email=textField1.getText();
                String password=passwordField1.getText();
                Object obj=e.getSource();
                if(operation.checkForLogin(email,password)){
                    JOptionPane.showMessageDialog(null, "Logged in.");

                    frame.getContentPane().removeAll();
                    frame.repaint();

                    LPanel lPanel = new LPanel(frame,operation);
                    Garage garage = new Garage(operation);
                    frame.getContentPane().add(lPanel.lpanel);
                    frame.getContentPane().add(garage.productPanel);
                    frame.revalidate();
                } else{
                    JOptionPane.showMessageDialog(null, "Wrong data entered.");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();

                SignUpPage signup = new SignUpPage(frame, operation);
                frame.getContentPane().add(signup.signUpPanel);
                frame.revalidate();
            }
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                loginButton.setBackground(Color.white);
                loginButton.setForeground(new Color(163,0,80));
            }
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                loginButton.setBackground(new Color(163,0,80));
                loginButton.setForeground(Color.white);
            }
        });
        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                signUpButton.setBackground(Color.white);
                signUpButton.setForeground(new Color(163,0,80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                signUpButton.setBackground(new Color(163,0,80));
                signUpButton.setForeground(Color.white);
            }
        });
    }


}
