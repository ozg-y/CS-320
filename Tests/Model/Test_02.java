import GUI.Garage;
import GUI.LoginPage;
import GUI.ProductPage;
import GUI.SignUpPage;
import Model.DatabaseOperation;
import Model.Student;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;


public class Test_02 {

    JFrame frame = new JFrame();
    DatabaseOperation op = new DatabaseOperation();
    SignUpPage signUp = new SignUpPage(frame,op);

    String photoPath = "C:\\Users\\abdul\\IdeaProjects\\CS-320\\Tests\\Resources\\test.jpg"; // ghosting the PATH

    @Test
    public void test_02_01() { // if email is not in proper format
        String studentEmail = "";
        String photoPath = "";
        String p1 = "";
        String p2 = "";

        String name = "Özge";
        String surname = "Simitcioğlu";

        assertFalse(signUp.signUp(p1,p2,studentEmail,name,surname,photoPath));

        studentEmail = "hugoBossPyjamas";

        assertFalse(signUp.signUp(p1,p2,studentEmail,name,surname,photoPath));

        studentEmail = "emin.sadikhov@mail.ru";

        assertFalse(signUp.signUp(p1,p2,studentEmail,name,surname,photoPath));

    }

    @Test
    public void test_02_02() {  // if the photo path is empty
        String studentEmail = "abdullah.saydemir@ozu.edu.tr";
        String photoPath = "";
        String p1 = "";
        String p2 = "";

        String name = "Özge";
        String surname = "Simitcioğlu";


        assertFalse(signUp.signUp(p1,p2,studentEmail,name,surname,photoPath));

    }

    @Test
    public void test_02_03() { // If the password field is empty
        String studentEmail = "abdullah.saydemir@ozu.edu.tr";
        String p1 = "";
        String p2 = "aleyna123";
        String name = "";
        String surname = "Simitçioğlu";

        assertFalse(signUp.signUp(p1,p2,studentEmail,name,surname,photoPath));

        p1 = "aleyna123";
        p2 = "";

        assertFalse(signUp.signUp(p1,p2,studentEmail,name,surname,photoPath));

        p1 = "aleyna123";
        p2 = "burcubasqan123";

        // if the passwords are not matching
        assertFalse(signUp.signUp(p1,p2,studentEmail,name,surname,photoPath));
    }


    @Test
    public void test_02_04() { // If the name field is empty
        String studentEmail = "abdullah.saydemir@ozu.edu.tr";

        String p1 = "aleyna123";
        String p2 = "aleyna123";

        String name = "";
        String surname = "Simitçioğlu";


        assertFalse(signUp.signUp(p1,p2,studentEmail,name,surname,photoPath));


        name = "Özge";
        surname = "";

        // if the surname is empty
        assertFalse(signUp.signUp(p1,p2,studentEmail,name,surname,photoPath));
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

}
