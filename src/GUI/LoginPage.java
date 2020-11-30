package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton signUpButton;

    public LoginPage() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo-Burası size emanet controller team
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo-controller
                //todo-Should go SignUpPage
            }
        });
    }
}
