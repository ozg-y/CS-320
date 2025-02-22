package GUI;

import Model.Comment;
import Model.DatabaseOperation;
import Model.Product;
import Model.Student;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProductPage {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Student student;
    private final DatabaseOperation operation;
    private final int productID;
    String finishedComment = "";
    int size = 0;
    private JEditorPane productDetails;
    private JPanel productPPanel;
    private JTextArea textArea1;
    private JButton commentButton;
    private JLabel sellerInfoLabel;
    private JLabel productPrice;
    private JLabel productName;
    private JLabel productPhotoLabel;
    private JEditorPane productComments;
    private final Product product;
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Comment> pullComments = new ArrayList<>();


    public ProductPage(int productID, DatabaseOperation operation, Student student) {

        this.student = student;
        this.operation = operation;
        this.productID = productID;

        product = operation.pull_product(productID);

        productPhotoLabel.setIcon(new ImageIcon(product.getProductPhoto().getImage().getScaledInstance(240, 240, Image.SCALE_DEFAULT)));
        productName.setText(product.getProductName());
        sellerInfoLabel.setText(product.getProductSeller().getStudentEmail());
        productPrice.setText(Double.toString(product.getProductPrice()));
        productDetails.setText(product.getProductDescription());

        comments = operation.pull_comment(productID);

        size = comments.size();

        for (Comment c : comments) {
            finishedComment += c.studentName + " : " + c.comment + "\n \n";
        }
        productComments.setText(finishedComment);

        commentButton.addActionListener(new CommentListener());
        commentButton.addMouseListener(new ButtonColorListener());
        scheduler.scheduleAtFixedRate(() -> {

            pullComments = operation.pull_comment(productID);

            if (!(pullComments.size() == size)) {

                for (int i = size; i < pullComments.size(); i++) {
                    finishedComment += pullComments.get(i).studentName + " : " + pullComments.get(i).comment + "\n \n";
                    size++;
                }
                productComments.setText(finishedComment);
            }

        }, 10, 10, TimeUnit.SECONDS);

    }

    public boolean postComment(String comment) {

        if (comment.equals("") || comment.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot post a blank comment.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        operation.push_comment(productID, comment, student.getStudentEmail());
        size++;
        textArea1.setText("");
        sendNotification(sellerInfoLabel.getText(), "ozyegingarage@gmail.com");
        comments = operation.pull_comment(productID);
        finishedComment = "";

        for (Comment c : comments) {
            finishedComment += c.studentName + " : " + c.comment + "\n \n";
        }

        productComments.setText(finishedComment);

        return true;

    }

    public void sendNotification(String to, String from) {

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
            message.setSubject("OzU-Garage Product Notification");

            // Now set the actual message
            message.setText("Your " + productName.getText() + " is gathering attention :)\n" + student.getStudentName() + " " + student.getStudentSurname() + " commented on your product\nOzU-Garage Team");

            // Send message
            Transport.send(message);

        } catch (MessagingException mex) { // Email Error
            JOptionPane.showMessageDialog(null, "Email Notification Couldn't Send It In Properly", "Error", JOptionPane.ERROR_MESSAGE);
            mex.printStackTrace();
        }
    }

    public JPanel getProductPPanel() {
        return productPPanel;
    }

    public class CommentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            postComment(textArea1.getText());
        }
    }

}



