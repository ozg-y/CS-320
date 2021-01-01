package GUI;

import Model.DatabaseOperation;
import Model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static GUI.LPanel.scaleFile;

public class LoginPage {
    private final DatabaseOperation operation;
    JFrame frame;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton signUpButton;
    private JPanel MainPanel;
    private JLabel iconLabel;


    public LoginPage(JFrame frame, DatabaseOperation operation) {
        this.operation = operation;
        this.frame = frame;

        ImageIcon ozuLogo = scaleFile(400, 200, "OzU_logo.png");
        iconLabel.setIcon(ozuLogo);

        frame.getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(new LoginListener());
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

        loginButton.addMouseListener(new ButtonColorListener());
        signUpButton.addMouseListener(new ButtonColorListener());
    }

    public boolean checkLogin(String email, String password) {

        boolean loginCheck = operation.checkForLogin(email, password);

        if (email.equals("ozyegingarage@gmail.com") && loginCheck) {
            PermitProduct permit = new PermitProduct(frame, operation, this);

            if (permit.isOK == 1) {
                frame.getContentPane().removeAll();
                frame.add(permit.permitPanel);

                frame.pack();
                frame.repaint();
                frame.revalidate();

                return true;
            } else if (permit.isOK == -1) {
                return true;
            }
        }
        if (loginCheck && !email.equals("ozyegingarage@gmail.com")) {
            JOptionPane.showMessageDialog(null, "Logged in.");
            frame.getContentPane().removeAll();
            frame.setLayout(new BorderLayout());

            Student student = operation.pull_student(email);
            Garage garage = new Garage(frame, operation, student);
            LPanel lPanel = new LPanel(frame, operation, garage, student);
            frame.getContentPane().add(garage.productPanel, BorderLayout.CENTER);
            frame.getContentPane().add(lPanel.lPanel, BorderLayout.WEST);
            frame.pack();
            frame.repaint();
            frame.revalidate();

            return true;

        } else if (email.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "Missing data", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!loginCheck) {
            JOptionPane.showMessageDialog(null, "Wrong data entered.");
            return false;
        }

        return false;
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    public class LoginListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            checkLogin(textField1.getText(), passwordField1.getText());

        }
    }


}
