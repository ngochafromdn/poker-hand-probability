package PokerHandProbability;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class MyButton extends JButton {
    String text;
    int x, y;
    Menu game;
    Font customFont;

    public MyButton(String text, int x, int y) {
        this.setText(text);
        this.setBounds(x, y, 200, 50);
        this.setBorder(null);
        this.setBackground(new Color(159, 0, 0));
        this.setFocusPainted(false);
        this.setForeground(Color.black);
        this.setVisible(false);
        this.setOpaque(true);
        
        this.setActionCommand(text);

        // add new font
        try {
            //create the font to use. Specify the size!
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("PokerHandProbability/Fonts/horizon.otf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // register the font
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // set font to the text
        this.setFont(customFont);
    }
}