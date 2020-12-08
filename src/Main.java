import GUI.HomePage;
import Model.DatabaseOperation;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        DatabaseOperation operation = new DatabaseOperation();
        HomePage homeScreen = new HomePage(operation);

    }
}
