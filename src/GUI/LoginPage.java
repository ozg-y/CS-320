package GUI;

import Model.DatabaseOperation;
import Model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import static GUI.LPanel.scaleFile;

public class LoginPage {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton signUpButton;
    private JPanel MainPanel;
    private JPanel topPanel;
    private JLabel iconLabel;
    private DatabaseOperation operation;


    public LoginPage(JFrame frame, DatabaseOperation operation) {
        this.operation = operation;
        String path = System.getProperty("user.dir");
        String OS = System.getProperty("os.name");

        if (OS.contains("Mac") || OS.contains("Linux")) {
            File ozuLogo = new File(path + "/src/Icons/OzU_logo.png");
            iconLabel.setIcon(new ImageIcon(ozuLogo.getAbsolutePath()));
        } else {
            ImageIcon ozuLogo = scaleFile(400,200,"OzU_logo.png");
            iconLabel.setIcon(ozuLogo);
        }


        frame.getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*if(true){
                    permitProduct permit = new permitProduct(frame,operation);

                    JOptionPane.showMessageDialog(null, "Logged in.");
                    frame.getContentPane().removeAll();
                    frame.setLayout(new BorderLayout());
                    frame.repaint();


                    frame.getContentPane().add(permit.permitPanel);

                    frame.pack();
                    frame.repaint();
                    frame.revalidate();

                    return;
                }

                 */





                String email = textField1.getText();
                String password = passwordField1.getText();
                Object obj = e.getSource();

                if (operation.checkForLogin(email, password)) {
                    JOptionPane.showMessageDialog(null, "Logged in.");
                    frame.getContentPane().removeAll();
                    frame.setLayout(new BorderLayout());
                    frame.repaint();


                    Student student = operation.pull_student(email);
                    Garage garage = new Garage(frame, operation, student);
                    LPanel lPanel = new LPanel(frame, operation, garage, student);
                    frame.getContentPane().add(garage.productPanel, BorderLayout.CENTER);
                    frame.getContentPane().add(lPanel.lPanel, BorderLayout.WEST);
                    frame.pack();
                    frame.repaint();
                    frame.revalidate();
                } else if (email.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Missing data", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
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
                loginButton.setForeground(new Color(163, 0, 80));
            }
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                loginButton.setBackground(new Color(163, 0, 80));
                loginButton.setForeground(Color.white);
            }
        });
        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                signUpButton.setBackground(Color.white);
                signUpButton.setForeground(new Color(163, 0, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                signUpButton.setBackground(new Color(163, 0, 80));
                signUpButton.setForeground(Color.white);
            }
        });
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }


}
