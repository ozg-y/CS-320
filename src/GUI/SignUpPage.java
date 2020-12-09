package GUI;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import Model.DatabaseOperation;

public class SignUpPage {
    public JPanel signUpPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton signUpButton;
    private JButton photoButton;
    private JTextField textField2;
    private JTextField textField3;
    private DatabaseOperation operation;
    private String studentName;
    private String studentSurname;
    private String studentProfilePhoto;
    private String studentEmail;
    private File photo;

    public SignUpPage(JFrame frame,DatabaseOperation operation) {

        this.operation = operation;

        signUpButton.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password1 = passwordField1.getText();
                String password2 = passwordField2.getText();

                if(password1.equals(password2)) {
                    studentName = textField2.getText();
                    studentSurname = textField3.getText();
                    studentProfilePhoto = photo.getAbsolutePath();
                    studentEmail = textField1.getText();

                    operation.push_student(studentName,studentSurname,studentProfilePhoto,studentEmail,password1);
                    int confirmationCode = sendEmail(studentEmail,"ozyegingarage@gmail.com");
                    operation.push_student_confirmation(studentEmail,confirmationCode);


                    SignUpConfirmPage confirm = new SignUpConfirmPage(frame,operation,studentEmail);
                    frame.getContentPane().removeAll();
                    frame.repaint();

                    frame.getContentPane().add(confirm.getpanelC());
                    frame.revalidate();
                }
            }


        });
        photoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                j.showSaveDialog(null);
                photo = j.getSelectedFile();
                ImageIcon icon = new ImageIcon(photo.getAbsolutePath());
                photoButton.setText(null);
                photoButton.setBackground(new java.awt.Color(187,187,187));
                photoButton.setOpaque(true);
                photoButton.setBorderPainted(false);
                photoButton.setIcon(icon);
            }
        });
    }

    public int sendEmail(String to,String from){

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

            JOptionPane.showMessageDialog(null,"Email Sent Correctly");

            return confirmationCode;

        } catch (MessagingException mex) { // Email Error
            JOptionPane.showMessageDialog(null,"Email Couldn't Send It In Properly","Error",JOptionPane.ERROR_MESSAGE);
            mex.printStackTrace();
        }
        return 0;
    }
}
