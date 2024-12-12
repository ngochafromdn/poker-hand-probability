package PokerHandProbability;

import javax.swing.*;
import java.awt.*;

public class HandProb extends JFrame {
    public HandProb() {
        new JFrame();

        this.setAlwaysOnTop(true);
        this.getContentPane().setBackground(Color.WHITE);

        this.setTitle("Hands Probabilities");
        this.setSize(350, 380);
        this.setLocation(6, 250);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        // Load the background image
        ImageIcon contain_bg = new ImageIcon("PokerHandProbability/Background/prob_bg.png");
        Image scaledImage = contain_bg.getImage().getScaledInstance(this.getWidth() - 10, this.getHeight() - 37, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledIcon);

        // Set the background label as the content pane
        this.setContentPane(backgroundLabel);

        ImageIcon icon_image = new ImageIcon("PokerHandProbability/Background/icon.png"); // create an imageicon
        this.setIconImage(icon_image.getImage()); // change the default icon into new icon
        this.setVisible(true);
    }
}
