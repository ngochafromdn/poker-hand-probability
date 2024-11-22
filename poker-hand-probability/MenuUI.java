package A_Card_Game;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MenuUI extends JFrame {
    // Menu screen
    MyButton startButton, introButton, backIntroButton, backGuideButton, guideButton;
    PaintPanel menu_canvas, intro_canvas, guide_canvas;

    Menu game;

    public MenuUI(Menu game) {
        this.game = game;

        this.setTitle("Poker hand");
        this.setSize(1280, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.white);

        ImageIcon icon_image = new ImageIcon("A_Card_Game/Background/icon.png"); // create an imageicon
        this.setIconImage(icon_image.getImage()); // change the default icon into new icon

        createMenu();
        createIntro();
        createGuide();
        
        this.setVisible(true);
    }

    private void createMenu() {
        menu_canvas = new PaintPanel(this, "menu.png");
        menu_canvas.setBounds(0,0,1280,800);
        menu_canvas.setOpaque(false); // allow orride the background image
        

        startButton = new MyButton("START", 520, 450);
        startButton.addActionListener(game.aHandler);

        introButton = new MyButton("INTRO", 520, 500 + 20);
        introButton.addActionListener(game.aHandler);

        guideButton = new MyButton("GUIDE", 520, 550 + 40);
        guideButton.addActionListener(game.aHandler);

        startButton.setVisible(true);
        introButton.setVisible(true);
        guideButton.setVisible(true);

        this.add(startButton);
        this.add(introButton);
        this.add(guideButton);
        this.add(menu_canvas);
    }

    private void createIntro() {
        intro_canvas = new PaintPanel(this, "intro.png");
        intro_canvas.setBounds(0,0,1280,800);
        intro_canvas.setOpaque(false); // allow orride the background image

        backIntroButton = new MyButton("BACK", 70, 34 * 2 - 8);
        backIntroButton.setForeground(Color.WHITE);
        backIntroButton.addActionListener(game.aHandler);
        this.add(backIntroButton);
        this.add(intro_canvas);
    }

    private void createGuide() {
        guide_canvas = new PaintPanel(this, "guide.png");
        guide_canvas.setBounds(0,0,1280,800);
        guide_canvas.setOpaque(true); // allow orride the background image
        

        backGuideButton = new MyButton("BACK", 70, 34 * 2 - 8);
        backGuideButton.setForeground(Color.WHITE);
        backGuideButton.addActionListener(game.aHandler);
        this.add(backGuideButton);
        this.add(guide_canvas);
    }
}
