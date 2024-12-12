package PokerHandProbability.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CardCombinationUtil {

    /**
     * This method generates all possible combinations of cards of size `r` from the given array of cards.
     *
     * @param cards Array of cards to choose from.
     * @param r The number of cards to select in each combination.
     * @return A list of string arrays representing all possible combinations of `r` cards.
     */
    public static List<String[]> getCombinations(String[] cards, int r) {
        List<String[]> combinations = new ArrayList<>();
        // Start the recursive combination generation
        combinationHelper(cards, new String[r], 0, 0, r, combinations);
        return combinations;
    }

    /**
     * A recursive helper function that generates combinations of `r` cards from the given array.
     *
     * @param cards The array of cards.
     * @param temp Temporary array to store the current combination.
     * @param start The index from which to start the combination.
     * @param index The current index in the temporary array.
     * @param r The number of cards to select in each combination.
     * @param combinations The list that stores the valid combinations.
     */
    private static void combinationHelper(String[] cards, String[] temp, int start, int index, int r, List<String[]> combinations) {
        // If we've selected 'r' cards, add the combination to the list
        if (index == r) {
            combinations.add(temp.clone());
            return;
        }

        // Generate combinations by iterating through the available cards
        for (int i = start; i < cards.length; i++) {
            temp[index] = cards[i]; // Set the current card
            // Recurse to the next index
            combinationHelper(cards, temp, i + 1, index + 1, r, combinations);
        }
    }

    /**
     * This method finds the best combination of 5 cards from a hand of 2 cards and 5 community cards.
     *
     * @param twoCards The player's two cards.
     * @param fiveCards The five community cards.
     * @return An array of 5 cards that form the best combination.
     */
    public static String[] bestCombinationCards(String[] twoCards, String[] fiveCards) {
        // Combine the player's two cards and the five community cards into one array
        String[] allCards = new String[7];
        System.arraycopy(twoCards, 0, allCards, 0, 2); // Copy twoCards into allCards
        System.arraycopy(fiveCards, 0, allCards, 2, 5); // Copy fiveCards into allCards

        // Initialize the bestCards array to store the best combination
        String[] bestCards = new String[5];
        // Get all possible 5-card combinations from the 7 cards
        List<String[]> combinations = getCombinations(allCards, 5);

        get_card_infor bestCardInfo = null;

        // Loop through all combinations to find the best one
        for (String[] combination : combinations) {
            get_card_infor currentCardInfo = new get_card_infor(combination); // Evaluate the current combination

            // Compare the current combination with the best combination found so far
            if (bestCardInfo == null || bestCardInfo.compare_to(currentCardInfo) < 0) {
                bestCardInfo = currentCardInfo;
                // Update bestCards with the current best combination
                System.arraycopy(combination, 0, bestCards, 0, 5);
            }
        }

        return bestCards; // Return the best combination of 5 cards
    }

}
