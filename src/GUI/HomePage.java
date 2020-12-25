package GUI;

import Model.DatabaseOperation;

import javax.swing.*;

import static GUI.LPanel.scaleFile;


public class HomePage {

    private JPanel homePanel;
    public JFrame frame;
    public LoginPage login;

    ImageIcon icon = scaleFile(200,200,"OzU.png");

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

    public JPanel getHomePanel() {
        return homePanel;
    }
}



