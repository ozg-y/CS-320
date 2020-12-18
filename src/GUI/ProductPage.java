package GUI;
import Model.Comment;
import Model.DatabaseOperation;
import Model.Product;
import Model.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class ProductPage {
    private JButton productPhotoButton;
    private JEditorPane productDetails;
    private String comment;
    private JPanel productPPanel;
    private JTextArea textArea1;
    private JButton commentButton;
    private JLabel sellerInfoLabel;
    private JLabel productPrice;
    private JLabel productName;
    private JEditorPane productComments;
    private File photo;
    private Product product;
    private ArrayList<Comment> comments = new ArrayList<>();

    public ProductPage(){}

    public ProductPage(int productID, DatabaseOperation operation, Student student) {

        String finishedComment="";
        product=operation.pull_product(productID);

        productName.setText(product.getProductName());
        sellerInfoLabel.setText(product.getProductSeller().getStudentEmail());
        productPrice.setText(Double.toString(product.getProductPrice()));
        productDetails.setText(product.getProductDescription());

        comments=operation.pull_comment(productID);
        for(Comment c : comments ){
            finishedComment+=c.studentName + " : " + c.comment + "\n \n";
        }
        productComments.setText(finishedComment);

        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comment = textArea1.getText();
                operation.push_comment(productID, comment, student.getStudentEmail());
            }
        });

        // TODO delete this actionlistener
        productPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                j.showSaveDialog(null);
                photo = j.getSelectedFile();
                ImageIcon icon = new ImageIcon(photo.getAbsolutePath());
                productPhotoButton.setText(null);
                productPhotoButton.setBackground(new java.awt.Color(187,187,187));
                productPhotoButton.setOpaque(true);
                productPhotoButton.setBorderPainted(false);
                productPhotoButton.setIcon(icon);
            }
        });
    }

    public JPanel getProductPPanel(){
        return productPPanel;
    }


}
