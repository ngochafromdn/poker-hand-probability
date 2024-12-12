package PokerHandProbability;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import PokerHandProbability.Function.draw_random;
import PokerHandProbability.Function.get_card_infor;
import PokerHandProbability.Function.CardCombinationUtil;
import PokerHandProbability.Function.PokerProbabilityCalculator;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

// import button and win screen
public class PlayPage {
    static Font customFont;
    static JPanel round;
    static JLabel roundText;

    static JLabel p1_first_card = new JLabel();
    static JLabel p1_second_card = new JLabel();

    static JLabel p2_first_card = new JLabel();
    static JLabel p2_second_card = new JLabel();

    static JLabel first_card = new JLabel();
    static JLabel second_card = new JLabel();
    static JLabel third_card = new JLabel();
    static JLabel fourth_card = new JLabel();
    static JLabel fifth_card = new JLabel();

    static MyFrame myFrame;
    static HandProb handProbFrame;

    // probability table
    static JTable table;

    // call random funtion
    static draw_random randomFunction;
    static String suffleMusic = "PokerHandProbability/Music/shuffle-cards.wav";
    static String endingMusic = "PokerHandProbability/Music/ending.wav";

    // winning probability
    static JLabel winning_prob;
    static Map<String, Double> propRound1;

    public static ImageIcon add_image(String file_name) {
        ImageIcon image = new ImageIcon("PokerHandProbability/Card Image/" + file_name + ".png");
        Image img = image.getImage();
        Image scaledImage = img.getScaledInstance(119, 159, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        return scaledIcon;
    }

    public static void startGame() throws Exception {
        // define random function
        randomFunction.getRandomCards();

        JPanel hand_prob = createPopUpPanel();
        handProbFrame.add(hand_prob);

        JPanel round = createRoundPanel();
        myFrame.add(round);

        JPanel player2 = createPlayer2Panel();
        myFrame.add(player2);

        JPanel middleFrame = createMiddleFrame();
        myFrame.add(middleFrame);

        JPanel player1 = createPlayer1Panel();
        myFrame.add(player1);

        JPanel fiveCards = createFiveCards();
        myFrame.add(fiveCards);

        JPanel buttonFrame = createButtonFrame();
        myFrame.add(buttonFrame);
    }

    public static void resetStaticVariables() {
        // Reset each static variable to its initial state
        myFrame = new MyFrame();
        handProbFrame = new HandProb();
        round = null;
        roundText = null;
        p1_first_card = new JLabel();
        p1_second_card = new JLabel();
        p2_first_card = new JLabel();
        p2_second_card = new JLabel();
        first_card = new JLabel();
        second_card = new JLabel();
        third_card = new JLabel();
        fourth_card = new JLabel();
        fifth_card = new JLabel();
        randomFunction = new draw_random();
        customFont = new Font("Tahoma", Font.BOLD, 42);

        // Reset table
        table = new JTable();

        // Reset winning probability
        winning_prob = new JLabel();
    }

    private static JPanel createPopUpPanel() throws Exception {
        JPanel popup = new JPanel(new BorderLayout());
        popup.setBackground(null);
        String[] cards_1 = randomFunction.card_1;

        // First round: empty known cards
        propRound1 = PokerProbabilityCalculator.calculateProbabilityOnString(
                5,30000, new String[]{}, cards_1)[0];
        // Output the probabilities for each round of player 1
        System.out.println("Player 1 - Round 1 Probability: " + propRound1);

        // Player 2
        Map<String, Double> propRound1_player2 = PokerProbabilityCalculator.calculateProbabilityOnString(
                5,30000, new String[]{}, cards_1)[1];
        // Output the probabilities for each round of player 2
        System.out.println("Player 2 - Round 1 Probability: " + propRound1_player2);

        // Define table columns
        String[] columnNames = {"Hand", "Player 1", "Player 2"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Add rows to table model
        tableModel.addRow(new Object[]{"Royal Flush", propRound1.get("royal_flush"), propRound1_player2.get("royal_flush")});
        tableModel.addRow(new Object[]{"Straight Flush", propRound1.get("straight_flush"), propRound1_player2.get("straight_flush")});
        tableModel.addRow(new Object[]{"Four of A Kind", propRound1.get("four_of_a_kind"), propRound1_player2.get("four_of_a_kind")});
        tableModel.addRow(new Object[]{"Full House", propRound1.get("full_house"), propRound1_player2.get("full_house")});
        tableModel.addRow(new Object[]{"Flush", propRound1.get("flush"), propRound1_player2.get("flush")});
        tableModel.addRow(new Object[]{"Straight", propRound1.get("straight"), propRound1_player2.get("straight")});
        tableModel.addRow(new Object[]{"Three of A Kind", propRound1.get("three_of_a_kind"), propRound1_player2.get("three_of_a_kind")});
        tableModel.addRow(new Object[]{"Two Pair", propRound1.get("two_pair"), propRound1_player2.get("two_pair")});
        tableModel.addRow(new Object[]{"One Pair", propRound1.get("pair"), propRound1_player2.get("pair")});
        tableModel.addRow(new Object[]{"High Card", propRound1.get("high_card"), propRound1_player2.get("high_card")});

        // Create JTable and set its model
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(350, 300)); // Adjust dimensions as needed

        // Customize font and colors
        table.setFont(new Font("Tahoma", Font.PLAIN, 13));
        table.setBorder(new LineBorder(Color.WHITE));
        table.setBackground(Color.WHITE);

        // Align text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        // Add table to popup panel
        popup.add(table, BorderLayout.NORTH);
        popup.setBounds(18, 107, 300, 160);

        return popup;
    }

    private static JPanel createRoundPanel() throws Exception {
        round = new JPanel(new BorderLayout());
        round.setBackground(null);

        roundText = new JLabel("Round  1", JLabel.LEFT);
        roundText.setForeground(Color.WHITE);

        // add new font
        try {
            // create the font to use. Specify the size!
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("PokerHandProbability/Fonts/Moul-Regular.ttf"))
                    .deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // register the font
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        roundText.setFont(customFont);

        round.add(roundText, BorderLayout.CENTER);
        round.setBounds(40, 8, 350, 87);
        return round;
    }

    private static JPanel createPlayer2Panel() throws Exception {
        ImageIcon red_card = new ImageIcon("PokerHandProbability/Img/red_card.png");

        JPanel player2 = new JPanel(new FlowLayout(FlowLayout.LEADING, 50, 0));
        player2.setBounds(450, 30, 1000, 170);
        player2.setBackground(new Color(26, 145, 85));

        p2_first_card.setIcon(red_card);
        p2_second_card.setIcon(red_card);

        player2.add(p2_first_card);
        player2.add(p2_second_card);

        JLabel p2_name = new JLabel("Player 2", JLabel.CENTER);
        p2_name.setForeground(Color.WHITE);

        p2_name.setFont(new Font("Tahoma", Font.BOLD, 20));

        JPanel p2_info = new JPanel(new GridLayout(2, 1));
        p2_info.setBackground(new Color(0, 0, 0, 0));
        p2_info.setBounds(0, 0, 500, 170);
        p2_info.add(p2_name);

        player2.add(p2_info);
        return player2;
    }

    private static JPanel createPlayer1Panel() throws Exception {
        JPanel player1 = new JPanel(new FlowLayout(FlowLayout.LEADING, 50, 0));
        player1.setBounds(450, 590, 1000, 170);
        player1.setBackground(new Color(26, 145, 85));

        // Get the first two cards of You - Player 1
        String player1_card1 = randomFunction.card_1[0];
        String player1_card2 = randomFunction.card_1[1];

        p1_first_card.setIcon(add_image(player1_card1));
        p1_second_card.setIcon(add_image(player1_card2));

        player1.add(p1_first_card);
        player1.add(p1_second_card);

        JLabel p1_name = new JLabel("You - Player 1", JLabel.CENTER);
        p1_name.setForeground(Color.WHITE);

        Font nameFont = new Font("Tahoma", Font.BOLD, 20);
        p1_name.setFont(nameFont);

        winning_prob = new JLabel(String.valueOf(propRound1.get("winning_probability")), JLabel.CENTER);
        winning_prob.setFont(new Font("Tahoma", Font.BOLD, 40));
        winning_prob.setForeground(new Color(243, 184, 184));

        JPanel p1_info = new JPanel(new GridLayout(2, 1));
        p1_info.setBackground(new Color(0, 0, 0, 0));
        p1_info.add(p1_name);
        p1_info.add(winning_prob);

        player1.add(p1_info);
        return player1;
    }

    private static JPanel createFiveCards() throws Exception {
        ImageIcon red_card = new ImageIcon("A_Card_Game/Img/red_card.png");
        JPanel fiveCards = new JPanel();
        fiveCards.setLayout(new FlowLayout(FlowLayout.LEADING, 25, 0));
        fiveCards.setBounds(260, 280, 750, 159);
        fiveCards.setBackground(new Color(26, 145, 85));

        first_card.setIcon(red_card);
        second_card.setIcon(red_card);
        third_card.setIcon(red_card);
        fourth_card.setIcon(red_card);
        fifth_card.setIcon(red_card);

        fiveCards.add(first_card);
        fiveCards.add(second_card);
        fiveCards.add(third_card);
        fiveCards.add(fourth_card);
        fiveCards.add(fifth_card);

        return fiveCards;
    }

    private static JPanel createButtonFrame() throws Exception {
        JPanel buttonFrame = new JPanel(new FlowLayout(FlowLayout.LEADING, 55, 0));
        buttonFrame.setBounds(460, 480, 500, 100);
        buttonFrame.setBackground(new Color(26, 145, 85));

        // Add BET button
        JButton bet = new JButton("BET");
        bet.setFont(new Font("Tahoma", Font.BOLD, 30));
        bet.setBorder(null);
        bet.setBackground(new Color(159, 0, 0));
        bet.setPreferredSize(new Dimension(100, 50));
        bet.setForeground(Color.WHITE);
        bet.setFocusPainted(false);
        bet.setEnabled(true);
        bet.setOpaque(true);

        // Add FOLD button
        JButton fold = new JButton("FOLD");
        fold.setFont(new Font("Tahoma", Font.BOLD, 30));
        fold.setBorder(null);
        fold.setBackground(new Color(159, 0, 0));
        fold.setPreferredSize(new Dimension(100, 50));
        fold.setForeground(Color.WHITE);
        fold.setFocusPainted(false);
        fold.setEnabled(true);
        fold.setOpaque(true);

        // Initialize a counter for the number of clicks
        final int[] clickCount = { 0 };

        // Define the function for BET button
        // Get the cards of Computer
        String[] cards_1 = randomFunction.card_1;
        String[] cards_2 = randomFunction.card_2;
        // Get the middle list
        String[] middle_cards = randomFunction.middle_list;

        String[] best_combination_1 = CardCombinationUtil.bestCombinationCards(cards_1, middle_cards);
        String[] best_combination_2 = CardCombinationUtil.bestCombinationCards(cards_2, middle_cards);

        get_card_infor best_hand1_infor = new get_card_infor(best_combination_1);
        String hand1_category = best_hand1_infor.get_category_String();

        get_card_infor best_hand2_infor = new get_card_infor(best_combination_2);
        String hand2_category = best_hand2_infor.get_category_String();

        int result = best_hand1_infor.compare_to(best_hand2_infor);

        String result_;
        // Printing the result
        if (result > 0) {
            result_ = "YOU WIN!";
        } else if (result < 0) {
            result_ = "GAME OVER!";
        } else {
            result_ = "IT IS A TIE!";
        }

        // Define the function for FOLD button
        fold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // dispose the screen
                handProbFrame.dispose();
                myFrame.dispose();
                // show the GAME OVER page
                try {
                    Endscreen.displayResult("GAME OVER");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        bet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check if the click count is less than the number of cards
                if (clickCount[0] < middle_cards.length) {
                    // Use a switch statement to set the icon for the correct card
                    switch (clickCount[0]) {
                        case 0:
                            MusicHandler.playMusic(suffleMusic); // Play music using Music handler function
                            first_card.setIcon(add_image(middle_cards[0]));
                            second_card.setIcon(add_image(middle_cards[1]));
                            third_card.setIcon(add_image(middle_cards[2]));
                            roundText.setText("Round  2");

                            // Second round: first 1 middle card
                            // Player 1
                            Map<String, Double> propRound2 = PokerProbabilityCalculator.calculateProbabilityOnString(4, 10000,
                                    Arrays.copyOfRange(middle_cards, 0, 1), cards_1)[0];
                            System.out.println("Player 1 - Round 2 Probability: " + propRound2);

                            // Update winning probabilities
                            winning_prob.setText(String.valueOf(propRound2.get("winning_probability")));

                            //Player 2
                            Map<String, Double> propRound2_player2 = PokerProbabilityCalculator.calculateProbabilityOnString(4, 10000,
                                    Arrays.copyOfRange(middle_cards, 0, 1), cards_1)[1];
                            System.out.println("Player 2 - Round 2 Probability: " + propRound2_player2);

                            // Update probabilities of player 1
                            table.setValueAt(propRound2.get("royal_flush"), 0, 1);
                            table.setValueAt(propRound2.get("straight_flush"), 1, 1);
                            table.setValueAt(propRound2.get("four_of_a_kind"), 2, 1);
                            table.setValueAt(propRound2.get("full_house"), 3, 1);
                            table.setValueAt(propRound2.get("flush"), 4, 1);
                            table.setValueAt(propRound2.get("straight"), 5, 1);
                            table.setValueAt(propRound2.get("three_of_a_kind"), 6, 1);
                            table.setValueAt(propRound2.get("two_pair"), 7, 1);
                            table.setValueAt(propRound2.get("pair"), 8, 1);
                            table.setValueAt(propRound2.get("high_card"), 9, 1);

                            // Update probabilities of player 2
                            table.setValueAt(propRound2_player2.get("royal_flush"), 0, 2);
                            table.setValueAt(propRound2_player2.get("straight_flush"), 1, 2);
                            table.setValueAt(propRound2_player2.get("four_of_a_kind"), 2, 2);
                            table.setValueAt(propRound2_player2.get("full_house"), 3, 2);
                            table.setValueAt(propRound2_player2.get("flush"), 4, 2);
                            table.setValueAt(propRound2_player2.get("straight"), 5, 2);
                            table.setValueAt(propRound2_player2.get("three_of_a_kind"), 6, 2);
                            table.setValueAt(propRound2_player2.get("two_pair"), 7, 2);
                            table.setValueAt(propRound2_player2.get("pair"), 8, 2);
                            table.setValueAt(propRound2_player2.get("high_card"), 9, 2);
                            break;

                        case 1:
                            MusicHandler.playMusic(suffleMusic);
                            fourth_card.setIcon(add_image(middle_cards[3]));
                            roundText.setText("Round  3");

                            // Third round: first 2 middle cards
                            // Player 1
                            Map<String, Double> propRound3 = PokerProbabilityCalculator.calculateProbabilityOnString(3, 10000,
                                    Arrays.copyOfRange(middle_cards, 0, 2), cards_1)[0];
                            System.out.println("Player 1 - Round 3 Probability: " + propRound3);

                            // Update winning probabilities
                            winning_prob.setText(String.valueOf(propRound3.get("winning_probability")));

                            //Player 2
                            Map<String, Double> propRound3_player2 = PokerProbabilityCalculator.calculateProbabilityOnString(4, 10000,
                                    Arrays.copyOfRange(middle_cards, 0, 1), cards_1)[1];
                            System.out.println("Player 2 - Round 2 Probability: " + propRound3_player2);

                            // Update probabilities of player 1
                            table.setValueAt(propRound3.get("royal_flush"), 0, 1);
                            table.setValueAt(propRound3.get("straight_flush"), 1, 1);
                            table.setValueAt(propRound3.get("four_of_a_kind"), 2, 1);
                            table.setValueAt(propRound3.get("full_house"), 3, 1);
                            table.setValueAt(propRound3.get("flush"), 4, 1);
                            table.setValueAt(propRound3.get("straight"), 5, 1);
                            table.setValueAt(propRound3.get("three_of_a_kind"), 6, 1);
                            table.setValueAt(propRound3.get("two_pair"), 7, 1);
                            table.setValueAt(propRound3.get("pair"), 8, 1);
                            table.setValueAt(propRound3.get("high_card"), 9, 1);

                            // Update probabilities of player 2
                            table.setValueAt(propRound3_player2.get("royal_flush"), 0, 2);
                            table.setValueAt(propRound3_player2.get("straight_flush"), 1, 2);
                            table.setValueAt(propRound3_player2.get("four_of_a_kind"), 2, 2);
                            table.setValueAt(propRound3_player2.get("full_house"), 3, 2);
                            table.setValueAt(propRound3_player2.get("flush"), 4, 2);
                            table.setValueAt(propRound3_player2.get("straight"), 5, 2);
                            table.setValueAt(propRound3_player2.get("three_of_a_kind"), 6, 2);
                            table.setValueAt(propRound3_player2.get("two_pair"), 7, 2);
                            table.setValueAt(propRound3_player2.get("pair"), 8, 2);
                            table.setValueAt(propRound3_player2.get("high_card"), 9, 2);
                            break;

                        case 2:
                            MusicHandler.playMusic(suffleMusic);
                            fifth_card.setIcon(add_image(middle_cards[4]));
                            roundText.setText("Round  4");
                            bet.setVisible(false);
                            fold.setVisible(false);

                            // Fourth round: first 3 middle cards
                            Map<String, Double> propRound4 = PokerProbabilityCalculator.calculateProbabilityOnString(2, 30000,
                                    Arrays.copyOfRange(middle_cards, 0, 3), cards_1)[0];
                            System.out.println("Player 1 - Round 4 Probability: " + propRound4);

                            // Update winning probabilities
                            winning_prob.setText(String.valueOf(propRound4.get("winning_probability")));

                            //Player 2
                            Map<String, Double> propRound4_player2 = PokerProbabilityCalculator.calculateProbabilityOnString(4, 10000,
                                    Arrays.copyOfRange(middle_cards, 0, 1), cards_1)[1];
                            System.out.println("Player 2 - Round 2 Probability: " + propRound4_player2);

                            // Update probabilities of player 1
                            table.setValueAt(propRound4.get("royal_flush"), 0, 1);
                            table.setValueAt(propRound4.get("straight_flush"), 1, 1);
                            table.setValueAt(propRound4.get("four_of_a_kind"), 2, 1);
                            table.setValueAt(propRound4.get("full_house"), 3, 1);
                            table.setValueAt(propRound4.get("flush"), 4, 1);
                            table.setValueAt(propRound4.get("straight"), 5, 1);
                            table.setValueAt(propRound4.get("three_of_a_kind"), 6, 1);
                            table.setValueAt(propRound4.get("two_pair"), 7, 1);
                            table.setValueAt(propRound4.get("pair"), 8, 1);
                            table.setValueAt(propRound4.get("high_card"), 9, 1);

                            // Update probabilities of player 2
                            table.setValueAt(propRound4_player2.get("royal_flush"), 0, 2);
                            table.setValueAt(propRound4_player2.get("straight_flush"), 1, 2);
                            table.setValueAt(propRound4_player2.get("four_of_a_kind"), 2, 2);
                            table.setValueAt(propRound4_player2.get("full_house"), 3, 2);
                            table.setValueAt(propRound4_player2.get("flush"), 4, 2);
                            table.setValueAt(propRound4_player2.get("straight"), 5, 2);
                            table.setValueAt(propRound4_player2.get("three_of_a_kind"), 6, 2);
                            table.setValueAt(propRound4_player2.get("two_pair"), 7, 2);
                            table.setValueAt(propRound4_player2.get("pair"), 8, 2);
                            table.setValueAt(propRound4_player2.get("high_card"), 9, 2);

                            // Show up two cards of Computer
                            Timer timer_showing_Card = new Timer(3000, event -> {
                                p2_first_card.setIcon(add_image(cards_2[0]));
                                p2_second_card.setIcon(add_image(cards_2[1]));
                                MusicHandler.playMusic(endingMusic);
                                handProbFrame.dispose();
                            });

                            timer_showing_Card.setRepeats(false); // Ensure the timer only runs once
                            timer_showing_Card.start(); // Start the timer

                            Timer timer1 = new Timer(9000, event -> {
                                // Showing results
                                // Disappear after
                                fifth_card.setVisible(false);
                                second_card.setVisible(false);
                                third_card.setVisible(false);
                                fourth_card.setVisible(false);
                                first_card.setVisible(false);

                                JPanel showResult1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
                                showResult1.setBounds(400, 220, 500, 170);
                                showResult1.setBackground(null);
                                showResult1.setOpaque(true);

                                JPanel showResult2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
                                showResult2.setBounds(400, 480, 500, 170);
                                showResult2.setBackground(null);
                                showResult2.setOpaque(true);

                                JLabel hand2_result = new JLabel(hand1_category.toUpperCase(), JLabel.CENTER);
                                System.out.println("Player 1: " + hand1_category);
                                hand2_result.setFont(new Font("Tahoma", Font.BOLD, 40));
                                hand2_result.setForeground(new Color(243, 184, 184));

                                JLabel hand1_result = new JLabel(hand2_category.toUpperCase(), JLabel.CENTER);
                                System.out.println("Player 2: "  + hand2_category);
                                hand1_result.setFont(new Font("Tahoma", Font.BOLD, 40));
                                hand1_result.setForeground(new Color(243, 184, 184));

                                showResult1.add(hand1_result);
                                showResult2.add(hand2_result);

                                myFrame.add(showResult1);
                                myFrame.add(showResult2);
                                MusicHandler.playMusic(endingMusic);
                            });

                            timer1.setRepeats(false);
                            timer1.start(); // Start the timer

                            // Frame of the result
                            Timer delayTimer = new Timer(6000, event -> {
                                Timer timer2 = new Timer(10000, event2 -> {
                                    myFrame.dispose();
                                    try {
                                        MusicHandler.playMusic(endingMusic);
                                        System.out.println(result_);
                                        if (result > 0) {
                                            Endscreen.displayResult("YOU WIN");
                                        } else if (result < 0) {
                                            Endscreen.displayResult("GAME OVER");
                                        } else {
                                            Endscreen.displayResult("IT IS A TIE");
                                        }

                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                });

                                timer2.setRepeats(false); // Set to execute only once
                                timer2.start(); // Start the timer
                            });

                            delayTimer.setRepeats(false); // Set to execute only once
                            delayTimer.start(); // Start the delay timer
                    }
                    clickCount[0]++;
                }
            }
        });

        // Add two buttons into panel
        buttonFrame.add(bet);
        buttonFrame.add(fold);
        return buttonFrame;
    }

    private static JPanel createMiddleFrame() throws Exception {
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BorderLayout());
        centralPanel.setBounds(232, 200, 820, 378);
        centralPanel.setBackground(new Color(0, 0, 0, 0));
        centralPanel.setOpaque(false);

        // scale down the image
        ImageIcon originalIcon = new ImageIcon("PokerHandProbability/Img/rounded_edge_rect.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(820, 350, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel centralIMG = new JLabel();
        centralIMG.setIcon(scaledIcon);

        centralPanel.add(centralIMG);

        centralPanel.setVisible(true);

        return centralPanel;
    }
}