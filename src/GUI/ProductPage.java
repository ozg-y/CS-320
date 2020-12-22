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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProductPage {
    private JEditorPane productDetails;
    private String comment;
    private JPanel productPPanel;
    private JTextArea textArea1;
    private JButton commentButton;
    private JLabel sellerInfoLabel;
    private JLabel productPrice;
    private JLabel productName;
    private JEditorPane productComments;
    private JLabel productPhotoLabel;
    private Product product;
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Comment> pullComments = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    String finishedComment="";
    int size = 0;

    public ProductPage(){}

    public ProductPage(int productID, DatabaseOperation operation, Student student) {





        product=operation.pull_product(productID);

        productPhotoLabel.setIcon(product.getProductPhotos().get(0));
        productName.setText(product.getProductName());
        sellerInfoLabel.setText(product.getProductSeller().getStudentEmail());
        productPrice.setText(Double.toString(product.getProductPrice()));
        productDetails.setText(product.getProductDescription());

        comments=operation.pull_comment(productID);

        size = comments.size();



        for(Comment c : comments ){
            finishedComment+=c.studentName + " : " + c.comment + "\n \n";
        }
        productComments.setText(finishedComment);

        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comment = textArea1.getText();
                operation.push_comment(productID, comment, student.getStudentEmail());

                comments = operation.pull_comment(productID);

                for(Comment c : comments ){
                    finishedComment+=c.studentName + " : " + c.comment + "\n \n";
                }
                productComments.setText(finishedComment);
            }
        });

        scheduler.scheduleAtFixedRate(()-> {

            pullComments = operation.pull_comment(productID);

            System.out.println("Pull Comments size : " + pullComments.size());
            System.out.println(" size : " + size);

            if(!(pullComments.size() == size)){
                for(int i = size;i<pullComments.size();i++)
                    finishedComment += pullComments.get(i).studentName + " : " + pullComments.get(i).comment + "\n \n";

                productComments.setText(finishedComment);
                size++;
            }




        }, 5, 10, TimeUnit.SECONDS);

    }



    public JPanel getProductPPanel(){
        return productPPanel;
    }


}
