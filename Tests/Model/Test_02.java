import GUI.*;
import Model.DatabaseOperation;
import Model.Student;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Test_02 {

    String dir = System.getProperty("user.dir");

    JFrame frame = new JFrame();
    DatabaseOperation op = new DatabaseOperation();
    SignUpPage signUp = new SignUpPage(frame, op);
    LoginPage loginPage = new LoginPage(frame, op);


    String photoPath = dir + "\\Tests\\Resources\\test.jpg"; // shadowing the PATH
    ImageIcon icon = new ImageIcon(photoPath);
    Student student = new Student("Emin", "Arslan", icon, "aleyna.olmezcan@ozu.edu.tr", "aleyna123");

    int id = 2;
    ProductPage productPage = new ProductPage(id, op, student);
    Garage garage = new Garage(frame, op, student);
    LPanel lpa = new LPanel(frame, op, garage, student);

    @Test
    public void test_02_01() { // if email is not in proper format
        String studentEmail = "";
        String photoPath = "";
        String p1 = "";
        String p2 = "";

        String name = "Özge";
        String surname = "Simitcioğlu";

        assertFalse(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));

        studentEmail = "hugoBossPyjamas";

        assertFalse(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));

        studentEmail = "emin.sadikhov@mail.ru";

        assertFalse(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));

    }

    @Test
    public void test_02_02() {  // if the photo path is empty
        String studentEmail = "abdullah.saydemir@ozu.edu.tr";
        String photoPath = "";
        String p1 = "";
        String p2 = "";

        String name = "Özge";
        String surname = "Simitcioğlu";


        assertFalse(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));

    }

    @Test
    public void test_02_03() { // If the password field is empty
        String studentEmail = "abdullah.saydemir@ozu.edu.tr";
        String p1 = "";
        String p2 = "";
        String name = "";
        String surname = "Simitçioğlu";

        assertFalse(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));

        p1 = "aleyna123";
        p2 = "";

        assertFalse(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));

        p1 = "";
        p2 = "burcubasqan123";


        assertFalse(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));

        p1 = "aleyna123";
        p2 = "burcubasqan123";

        // if the passwords are not matching
        assertFalse(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));
    }

    @Test
    public void test_02_04() { // If the name field is empty
        String studentEmail = "abdullah.saydemir@ozu.edu.tr";

        String p1 = "aleyna123";
        String p2 = "aleyna123";

        String name = "";
        String surname = "Simitçioğlu";


        assertFalse(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));


        name = "Özge";
        surname = "";

        // if the surname is empty
        assertFalse(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));
    }

    @Test
    public void test_02_05() { // If all correct
        String studentEmail = "abdullah.saydemir@ozu.edu.tr";

        String p1 = "aleyna123";
        String p2 = "aleyna123";

        String name = "Özge";
        String surname = "Simitçioğlu";

        assertTrue(signUp.signUp(p1, p2, studentEmail, name, surname, photoPath));

    }

    @Test
    public void test_03_01() {
        String studentEmail = "";
        String studentPassword = "";
        assertFalse(loginPage.checkLogin(studentEmail, studentPassword));

        studentEmail = "";
        studentPassword = "123";
        assertFalse(loginPage.checkLogin(studentEmail, studentPassword));

        studentEmail = "burcu.arslan@ozu.edu.tr";
        studentPassword = "";
        assertFalse(loginPage.checkLogin(studentEmail, studentPassword));

        studentEmail = "burcu.arslan@ozu.edu.tr";
        studentPassword = "123";
        assertTrue(loginPage.checkLogin(studentEmail, studentPassword));

        studentEmail = "ozyegingarage@gmail.com";
        studentPassword = "123";
        assertFalse(loginPage.checkLogin(studentEmail, studentPassword));

        studentEmail = "ozyegingarage@gmail.com";
        studentPassword = "admin";
        assertTrue(loginPage.checkLogin(studentEmail, studentPassword));

        studentEmail = "burcu.arslan@ozu.edu.tr";
        studentPassword = "123";
        assertTrue(loginPage.checkLogin(studentEmail, studentPassword));

    }

    @Test
    public void test_06_01() {
        String comment = "";

        assertFalse(productPage.postComment(comment));

        comment = "                     ";

        assertFalse(productPage.postComment(comment));

        comment = "PYJAMAS";

        assertTrue(productPage.postComment(comment));

        comment = "Dollar is 10 you want 30 lira. C\'mooooon";

        assertTrue(productPage.postComment(comment));
    }

    @Test
    public void test_09_01() {

        assertTrue(lpa.goToProfilePage());

    }


}
