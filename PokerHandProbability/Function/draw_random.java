package PokerHandProbability.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class draw_random {
    /** The rank symbols. */
    public static final String[] RANK_SYMBOLS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        
    /** The suit symbols. */
    public static final String[] SUIT_SYMBOLS = { "D", "C", "H", "S" };
    public String[] card_1;
    public String[] middle_list;
    public String[] card_2;

    public void getRandomCards() {
        List<String> deck = new ArrayList<>();
        for (String suit : SUIT_SYMBOLS) {
            for (String rank : RANK_SYMBOLS) {
                deck.add(rank + suit);
            }
        }
        Collections.shuffle(deck);
        card_1 = deck.subList(0, 2).toArray(new String[0]);
        card_2 = deck.subList(2, 4).toArray(new String[0]);
        middle_list = deck.subList(4, 9).toArray(new String[0]);
    }
}
