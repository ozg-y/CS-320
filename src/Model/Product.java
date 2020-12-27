package Model;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Product {
    private int productID;
    private String productName;
    private String productCategory;
    private ImageIcon productPhoto;
    private double productPrice;
    private ArrayList<String> productComments;
    private Student productSeller;
    private String productDescription;

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

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public ImageIcon getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhotos(ImageIcon productPhoto) {
        this.productPhoto = productPhoto;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public ArrayList<String> getProductComments() {
        return productComments;
    }

    public void setProductComments(ArrayList<String> productComments) {
        this.productComments = productComments;
    }

    public Student getProductSeller() {
        return productSeller;
    }

    public void setProductSeller(Student productSeller) {
        this.productSeller = productSeller;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }


}
