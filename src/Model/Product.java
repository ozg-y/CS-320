package Model;

import javax.swing.*;
import java.util.ArrayList;

public class Product {
    private int productID;
    private final String productName;
    private final String productCategory;
    private final ImageIcon productPhoto;
    private final double productPrice;
    private final ArrayList<String> productComments;
    private final Student productSeller;
    private final String productDescription;

    public Product(String productName, String productCategory,
                   ImageIcon productPhoto, double productPrice, ArrayList<String> productComments,
                   Student productSeller, String productDescription) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPhoto = productPhoto;
        this.productPrice = productPrice;
        this.productComments = productComments;
        this.productSeller = productSeller;
        this.productDescription = productDescription;
    }

    public Product(int productID, String productName, String productCategory, ImageIcon productPhoto,
                   double productPrice, ArrayList<String> productComments, Student productSeller, String productDescription) {
        this.productID = productID;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPhoto = productPhoto;
        this.productPrice = productPrice;
        this.productComments = productComments;
        this.productSeller = productSeller;
        this.productDescription = productDescription;
    }


    public String getProductName() {
        return productName;
    }

    public ImageIcon getProductPhoto() {
        return productPhoto;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public Student getProductSeller() {
        return productSeller;
    }

    public String getProductDescription() {
        return productDescription;
    }


}
