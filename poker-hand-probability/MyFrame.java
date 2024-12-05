package PokerHandProbability;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

class MyFrame extends JFrame {
    public MyFrame() {
        new JFrame();

        this.setTitle("Poker hand");
        this.setSize(1280, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        ImageIcon icon_image = new ImageIcon("PokerHandProbability/Background/icon.png"); // create an imageicon
        this.setIconImage(icon_image.getImage()); // change the default icon into new icon

        this.setVisible(true);
    }
}