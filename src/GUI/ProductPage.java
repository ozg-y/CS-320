package GUI;
import Model.DatabaseOperation;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProductPage {
    private JButton productPhotoButton;
    private JEditorPane productDetails;
    private JEditorPane productComments;
    private String comment;
    private JPanel productPPanel;
    private JTextArea textArea1;
    private JButton commentButton;
    private File photo;

    public ProductPage(){}

    public ProductPage(int productID, DatabaseOperation operation) {
        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comment = textArea1.getText();
                operation.push_comment(productID, comment);
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
