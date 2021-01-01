package Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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
    public Student(String _studentName, String _studentSurname, ImageIcon _studentProfilePhoto, String _studentEmail, String _studentPassword){
        studentName = _studentName;
        studentSurname = _studentSurname;
        studentEmail = _studentEmail;
        studentPassword = _studentPassword;
        studentProfilePhoto = _studentProfilePhoto;

    }

    public Student(String _studentName, String _studentSurname, String _studentEmail, String _studentPassword){
        studentName = _studentName;
        studentSurname = _studentSurname;
        studentEmail = _studentEmail;
        studentPassword = _studentPassword;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public ImageIcon getStudentProfilePhoto() {
        return studentProfilePhoto;
    }

    public void setStudentProfilePhoto(ImageIcon studentProfilePhoto) { this.studentProfilePhoto = studentProfilePhoto; }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }


}
