package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonColorListener extends MouseAdapter {

    @Override
    public void mouseEntered(MouseEvent e) {

        JButton button = ((JButton)e.getSource());
        Color tempBg =  button.getBackground();

        button.setBackground(button.getForeground());
        button.setForeground(tempBg);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = ((JButton)e.getSource());
        Color tempBg =  button.getBackground();

        button.setBackground(button.getForeground());
        button.setForeground(tempBg);
    }

}
