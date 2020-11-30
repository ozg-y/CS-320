package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpConfirmPage {
    private JTextField textField1;
    private JButton button1;

    public SignUpConfirmPage() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo-If code is correct go to LoginPage
                //todo-If not stay
            }
        });
    }
}
