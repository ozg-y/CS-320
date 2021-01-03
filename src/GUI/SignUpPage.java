package GUI;


import Model.DatabaseOperation;
import org.apache.commons.validator.routines.EmailValidator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class SignUpPage {
    public JPanel signUpPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton signUpButton;
    private JButton photoButton;
    private JTextField textField2;
    private JTextField textField3;
    private final DatabaseOperation operation;
    private File photo;
    private String photoPath;
    private final JFrame frame;

    public SignUpPage(JFrame frame, DatabaseOperation operation) {

        this.operation = operation;
        this.frame = frame;

        signUpButton.addActionListener(new SignUpListener());
        photoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                int fileSelected = j.showSaveDialog(null);

                if (fileSelected == JFileChooser.APPROVE_OPTION) {
                    photo = j.getSelectedFile();
                    photoPath = photo.getAbsolutePath();
                    ImageIcon icon = new ImageIcon(photo.getAbsolutePath());
                    photoButton.setText(null);
                    photoButton.setBackground(new java.awt.Color(187, 187, 187));
                    photoButton.setOpaque(true);
                    photoButton.setBorderPainted(false);
                    photoButton.setIcon(icon);
                }
            }
        });
        signUpButton.addMouseListener(new ButtonColorListener());
    }

    public static boolean isValidEmail(String email) {
        // create the EmailValidator instance
        EmailValidator validator = EmailValidator.getInstance();

        // check for valid email addresses using isValid method
        return validator.isValid(email);
    }

    public boolean signUp(String password1, String password2, String email, String name, String surname, String photoPath) {

        boolean b = isValidEmail(email);


        if (!b) {
            JOptionPane.showMessageDialog(null, "Sign up with your OzU email");
            return false;
        } else if (email.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter your e-mail", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!email.contains("ozu.edu.tr") && !email.contains("ozyegin.edu.tr")) {
            JOptionPane.showMessageDialog(null, "Sign up with your OzU Email", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (photoPath == null || photoPath.equals("")) {
            JOptionPane.showMessageDialog(null, "Select a photo");
            return false;
        } else if (password1.equals("") || password2.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter your password", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (name.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter your name", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (surname.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter your surname", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (password1.equals(password2)) {

            boolean success = operation.push_student(name, surname, photoPath, email, password1);
            if (success) {
                int confirmationCode = sendEmail(email, "ozyegingarage@gmail.com");
                operation.push_student_confirmation(email, confirmationCode);

                SignUpConfirmPage confirm = new SignUpConfirmPage(frame, operation, email);
                frame.getContentPane().removeAll();
                frame.repaint();

                frame.getContentPane().add(confirm.getpanelC());
                frame.revalidate();
                return true;
            }
            else {
                JOptionPane.showMessageDialog(null, "You have already created an account", "Error", JOptionPane.ERROR_MESSAGE);
                LoginPage reLogin = new LoginPage(frame, operation);
                frame.getContentPane().removeAll();
                frame.repaint();

                frame.getContentPane().add(reLogin.getMainPanel());
                frame.revalidate();
                return false;
            }


        } else {
            passwordField1.setText("");
            passwordField2.setText("");
            JOptionPane.showMessageDialog(null, "Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public int sendEmail(String to, String from) {

        // Creating properties for email that we will send
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        // Get the Session object.
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "5TjTNSE3TQ32Pds");
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("OzU-Garage Confirmation Email Code");

            // Creating confirmation code for the new user
            int confirmationCode = ThreadLocalRandom.current().nextInt(100000, 999998 + 1);

            // Now set the actual message
            message.setText("Your confirmation code is  :" + confirmationCode + "\nOzU-G Team");

            // Send message
            Transport.send(message);

            JOptionPane.showMessageDialog(null, "Email Sent Correctly");

            return confirmationCode;

        } catch (MessagingException mex) { // Email Error
            JOptionPane.showMessageDialog(null, "Email Couldn't Send It In Properly", "Error", JOptionPane.ERROR_MESSAGE);
            mex.printStackTrace();
        }
        return 0;
    }

    public class SignUpListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            signUp(passwordField1.getText(), passwordField2.getText(), textField1.getText(), textField2.getText(), textField3.getText(), photoPath);
        }
    }
}
