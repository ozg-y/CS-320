package Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Student {

    private String studentName;
    private String studentSurname;
    private ImageIcon studentProfilePhoto;
    private String studentEmail;
    private String studentPassword;

    public Student(String _studentName, String _studentSurname, InputStream _studentProfilePhoto, String _studentEmail, String _studentPassword){
        studentName = _studentName;
        studentSurname = _studentSurname;
        studentEmail = _studentEmail;
        studentPassword = _studentPassword;

        try{
            Image image = ImageIO.read(_studentProfilePhoto);
            ImageIcon icon = new ImageIcon(image);
            studentProfilePhoto = icon;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public ImageIcon getStudentProfilePhoto() {
        return studentProfilePhoto;
    }

    public void setStudentProfilePhoto(ImageIcon studentProfilePhoto) { this.studentProfilePhoto = studentProfilePhoto; }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public void postProduct(Product p){

    }

    public void manageProduct(Product p){

    }

}
