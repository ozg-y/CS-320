package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        changePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                changePasswordButton.setBackground(Color.white);
                changePasswordButton.setForeground(new Color(163,0,80));
            }
        });

        changePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                changePasswordButton.setBackground(new Color(163,0,80));
                changePasswordButton.setForeground(Color.white);
            }
        });


    }


}
