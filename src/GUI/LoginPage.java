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



    public LoginPage() {
        HomePage homepage = new HomePage();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo-Burası size emanet controller team
                String email=textField1.getText();
                String password=passwordField1.getText();

                if(operation.checkForLogin(email,password)){
                    JOptionPane.showMessageDialog(null, "Logged in.");
                    //go to homepage
                    MainPanel.removeAll();
                    MainPanel.add(homepage.getPanel1());


                } else{
                    JOptionPane.showMessageDialog(null, "Wrong data entered.");
                    textField1.setText("");
                    passwordField1.setText("");

                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo-controller
                //todo-Should go SignUpPage
                SignUpPage signup=new SignUpPage();

                MainPanel.removeAll();
                MainPanel.add(signup.getSignUpPage());
            }
        });
    }
}
