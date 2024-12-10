package PokerHandProbability.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerGenerator {
    private static final String[] SUIT = { "H", "D", "C", "S" };
    private static final String[] RANK = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };

    public static String[] generatePokerHand(int k, String[] hand_on_deck, String[] hand_on_hand) {
        if (k > 52) {
            throw new IllegalArgumentException("Cannot generate more than 52 unique cards.");
        }

        // Tạo danh sách các quân bài
        List<String> deck = new ArrayList<>();
        for (String rank : RANK) {
            for (String suit : SUIT) {
                deck.add(rank + suit);
            }
        }

        // Loại bỏ các quân bài đã có trên bộ bài (hand_on_deck) và trên tay người chơi
        // (hand_on_hand)
        List<String> cardsInUse = new ArrayList<>();
        Collections.addAll(cardsInUse, hand_on_deck);
        Collections.addAll(cardsInUse, hand_on_hand);

        // Loại bỏ các quân bài đã có
        deck.removeAll(cardsInUse);

        // Trộn bài
        Collections.shuffle(deck);

        // Lấy k phần tử đầu tiên
        return deck.subList(0, k).toArray(new String[0]);
    }

    public static void main(String[] args) {
        String[] handOnDeck = { "2H", "3D", "KC" }; // Các quân bài đã có trên bộ bài
        String[] handOnHand = { "10S", "JH" }; // Các quân bài đã có trên tay người chơi

        int k = 5; // Số quân bài muốn tạo
        String[] pokerHand = generatePokerHand(k, handOnDeck, handOnHand);

        // In kết quả
        for (String card : pokerHand) {
            System.out.print(card + " ");
        }
    }
}
