import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Product {
    private String productName;
    private String productCategory;
    private ArrayList<BufferedImage> productPhotos;
    private double productPrice;
    private ArrayList<String> productComments;
    private Student productSeller;
    private String productDescription;
    private DatabaseOperation databaseOperation;

    public Product(String productName, String productCategory,
                   ArrayList<BufferedImage> productPhotos, double productPrice, ArrayList<String> productComments,
                   Student productSeller, String productDescription, DatabaseOperation databaseOperation) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPhotos = productPhotos;
        this.productPrice = productPrice;
        this.productComments = productComments;
        this.productSeller = productSeller;
        this.productDescription = productDescription;
        this.databaseOperation = databaseOperation;
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

    public ArrayList<BufferedImage> getProductPhotos() {
        return productPhotos;
    }

    public void setProductPhotos(ArrayList<BufferedImage> productPhotos) {
        this.productPhotos = productPhotos;
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
