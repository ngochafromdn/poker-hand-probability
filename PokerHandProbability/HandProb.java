package PokerHandProbability;

import javax.swing.*;
import java.awt.*;

public class HandProb extends JFrame {
    public HandProb() {
        new JFrame();

        this.getContentPane().setBackground(Color.WHITE);
        this.setTitle("Hands Probabilities");
        this.setSize(300, 400);
//        this.setLocationRelativeTo(null);
        this.setLocation(6, 250);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setAlwaysOnTop(true);

        ImageIcon icon_image = new ImageIcon("PokerHandProbability/Background/icon.png"); // create an imageicon
        this.setIconImage(icon_image.getImage()); // change the default icon into new icon
        this.setVisible(true);
    }
}
