package PokerHandProbability;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PaintPanel extends JPanel implements ActionListener {
    MenuUI ui;
    BufferedImage bg_img;

    public PaintPanel(MenuUI ui, String image_name){
        this.ui = ui;
        
        try {
            String image_path = "PokerHandProbability/Background/" + image_name;
            bg_img = ImageIO.read(new File(image_path));
//            bg_img = ImageIO.read(getClass().getClassLoader().getResource(image_path));
        } 
        catch (IOException e){
            // throw new IllegalArgumentException(file_path);
            e.getMessage();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);	
		g.drawImage(bg_img,0,0,644*2 - 20, 411*2 - 58,null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
}
