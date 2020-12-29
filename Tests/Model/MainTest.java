package Model;

import GUI.*;
import Model.DatabaseOperation;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    String studentEmail = "esad.simitcioglu@ozu.edu.tr";
    String studentPassword = "123";



    JFrame frame = new JFrame();
    DatabaseOperation op = new DatabaseOperation();
    SignUpPage signUp = new SignUpPage(frame,op);
    LoginPage login = new LoginPage(frame,op);
    Student student = new Student("Esad","Simitcioglu",studentEmail,studentPassword);
    int productID = 2;
    ProductPage productPage = new ProductPage(productID,op,student);
    Garage garage = new Garage(frame,op,student);




    @Test
    public void Test_001(){
        assertTrue(op.checkForConnection());
    }

    /*@Test
    public void Test_002(){
        assertTrue(signUp.login("123","123",studentEmail,studentEmail,"Esad","Simitcioglu",false,"D:\\CS-320\\src\\Icons\\OzU.png",frame,op));
    }

    @Test
    public void Test_003(){
        assertTrue(login.checkLogin(frame,op,login,studentEmail,studentPassword));
    }

    @Test
    public void Test_005(){
        assertTrue(login.checkLogin(frame,op,login,studentEmail,studentPassword));
    }

    @Test
    public void Test_006(){

        JTextArea area = new JTextArea();
        JEditorPane editorPane = new JEditorPane();
        JLabel label = new JLabel();
        label.setText(studentEmail);
        ArrayList<Comment> comments = new ArrayList<>();

        assertTrue(productPage.checkForComment("Ürününüz çok güzel gözüküyor",op,student,area,productID,0,label,comments,"",editorPane));
    }

     */

    @Test
    public void Test_009(){

        student.changeStudentProfilePhoto("D:\\CS-320\\src\\Icons\\OzU.png");
        LPanel lpa = new LPanel(frame,op,garage,student);



        assertTrue(lpa.checkForLoginPage(lpa.category1IconButton,lpa.category2IconButton,lpa.category3IconButton,frame,op,student,lpa.getLPanel()));
    }

    @Test
    public void Test_010(){


    }




}