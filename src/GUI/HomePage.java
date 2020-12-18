package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicReference;import GUI.LPanel;
import Model.DatabaseOperation;
import javax.swing.*;
import java.util.concurrent.atomic.AtomicReference;


public class HomePage {

    private JPanel homepanel;

    public JPanel getHomepanel() {
        return homepanel;
    }


    public JFrame frame;
    public LoginPage login;



    public HomePage(DatabaseOperation operation) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {

            frame = new JFrame("Program");
            frame.setSize(1600, 900);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            login = new LoginPage(frame, operation);
            frame.add(login.getMainPanel());
        });

    }
}



