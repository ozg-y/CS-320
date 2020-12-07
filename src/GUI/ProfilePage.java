package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.LPanel;
public class ProfilePage {
    private JButton profilePhotoButton;
    private JButton changePasswordButton;
    private JPanel profileppanel;

    public JPanel getProfileppanel(){
        return profileppanel;
    }

    public ProfilePage() {
        profilePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }


}
