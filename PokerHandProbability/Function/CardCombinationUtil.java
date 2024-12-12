package PokerHandProbability.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CardCombinationUtil {

    public static List<String[]> getCombinations(String[] cards, int r) {
        List<String[]> combinations = new ArrayList<>();
        combinationHelper(cards, new String[r], 0, 0, r, combinations);
        return combinations;
    }

    private static void combinationHelper(String[] cards, String[] temp, int start, int index, int r, List<String[]> combinations) {
        if (index == r) {
            combinations.add(temp.clone());
            return;
        }

        for (int i = start; i < cards.length; i++) {
            temp[index] = cards[i];
            combinationHelper(cards, temp, i + 1, index + 1, r, combinations);
        }
    }


    public static String[] bestCombinationCards(String[] twoCards, String[] fiveCards) {
        // Combine twoCards and fiveCards into a single array
        String[] allCards = new String[7];
        System.arraycopy(twoCards, 0, allCards, 0, 2); // Copy twoCards into allCards
        System.arraycopy(fiveCards, 0, allCards, 2, 5); // Copy fiveCards into allCards

        String[] bestCards = new String[5]; // To hold the best combination of 5 cards
        List<String[]> combinations = getCombinations(allCards, 5); // Get all combinations of 5 cards from the 7 cards

        get_card_infor bestCardInfo = null;

        for (String[] combination : combinations) {
            get_card_infor currentCardInfo = new get_card_infor(combination); // Evaluate the current combination

            // Compare the current combination with the best combination so far
            if (bestCardInfo == null || bestCardInfo.compare_to(currentCardInfo) < 0) {
                bestCardInfo = currentCardInfo;
                System.arraycopy(combination, 0, bestCards, 0, 5); // Update bestCards with the current combination
            }
        }

        return bestCards;
    }


}
