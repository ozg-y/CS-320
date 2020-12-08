package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }
}
