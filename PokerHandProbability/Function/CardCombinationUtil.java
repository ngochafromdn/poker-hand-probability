package PokerHandProbability.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CardCombinationUtil {

    // Function to generate combinations of 3 cards from given 5 cards
    private static List<String[]> getCombinations(String[] fiveCards) {
        List<String[]> combinations = new ArrayList<>();
        generateCombinations(fiveCards, 0, new String[3], 0, combinations);
        return combinations;
    }

    // Recursive function to generate combinations
    private static void generateCombinations(String[] fiveCards, int start, String[] combination, int index,
            List<String[]> combinations) {
        if (index == 3) {
            combinations.add(combination.clone());
            return;
        }

        for (int i = start; i <= fiveCards.length - 3 + index; i++) {
            combination[index] = fiveCards[i];
            generateCombinations(fiveCards, i + 1, combination, index + 1, combinations);
        }
    }

    // Function to find the best combination of 5 cards
    public static String[] bestCombinationCards(String[] twoCards, String[] fiveCards) {
        String[] bestCards = new String[5];
        bestCards[0] = twoCards[0];
        bestCards[1] = twoCards[1];
        bestCards[2] = fiveCards[0];
        bestCards[3] = fiveCards[1];
        bestCards[4] = fiveCards[2];

        String[] currentCard = new String[5];
        currentCard[0] = twoCards[0];
        currentCard[1] = twoCards[1];

        List<String[]> combinations = getCombinations(fiveCards);
        for (String[] combination : combinations) {
            for (int i = 0; i < 3; i++) {
                currentCard[i + 2] = combination[i];
            }

            get_card_infor bestCardInfo = new get_card_infor(bestCards);
//            System.out.println(bestCardInfo.get_category());
            get_card_infor currentCardInfo = new get_card_infor(currentCard);
//            System.out.println(currentCardInfo.get_category());
            // Print current card

//            System.out.println("Current card: " + Arrays.toString(currentCard));
            int result = bestCardInfo.compare_to(currentCardInfo);

            if (result < 0) {
                bestCards[0] = currentCard[0];
                bestCards[1] = currentCard[1];
                bestCards[2] = currentCard[2];
                bestCards[3] = currentCard[3];
                bestCards[4] = currentCard[4];
            }
        }
        return bestCards;
    }

}
