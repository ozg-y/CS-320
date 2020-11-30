package Model;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class Mail {

    public String to;
    public String from;
    public String subject;
    public String content;
    public DatabaseOperation operation;

    public Mail(String to_,String from_,String subject_,String content_,DatabaseOperation operation_){
        to = to_;
        from = from_;
        subject = subject_;
        content = content_;
        operation = operation_;
    }

    public void sendEmail(){

        // Creating properties for email that we will send
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Ozyegin University Confirmation Email Code");

            // Creating confirmation code for the new user
            int confirmation_code = ThreadLocalRandom.current().nextInt(100000, 999998 + 1);

            // Now set the actual message
            message.setText("Your Confirmation Code Is  :" + confirmation_code);

            // Pushing the new user's confirmation code and email to the database
            operation.push_student_confirmation(to,confirmation_code);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) { // Email Error
            JOptionPane.showMessageDialog(null,"Email Couldn't Send It In Properly.","Error",JOptionPane.ERROR_MESSAGE);
            mex.printStackTrace();
        } catch (SQLException throwables) { // Database Error
            JOptionPane.showMessageDialog(null,"The System Couldn't Save The Confirmation Code And Email Properly","Error",JOptionPane.ERROR_MESSAGE);
            throwables.printStackTrace();
        }
    }
}
