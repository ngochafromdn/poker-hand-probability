package PokerHandProbability;

import javax.swing.*;
import java.awt.*;

public class HandProb extends JFrame {
    public HandProb() {
        new JFrame();

        this.setAlwaysOnTop(true);
        this.getContentPane().setBackground(Color.WHITE);
        this.setTitle("Hands Probabilities");
        this.setSize(300, 600);
        this.setLocation(6, 125);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        ImageIcon icon_image = new ImageIcon("PokerHandProbability/Background/icon.png"); // create an imageicon
        this.setIconImage(icon_image.getImage()); // change the default icon into new icon
        this.setVisible(true);
    }
}
