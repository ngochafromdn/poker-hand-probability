package PokerHandProbability.Function;
import java.text.DecimalFormat;
import java.util.Map;

import java.util.*;

public class PokerProbabilityCalculator {

    // Thực chất chỗ này là update số
    public static void update_probability(String result, Map<String, Double> probabilities) {
        probabilities.put(result, probabilities.getOrDefault(result, 0.0) + 1);
    }

    public static void make_simulation(
            int num_of_random,
            int number_of_cases_simulation,
            String[] card_on_desk,
            String[] card_on_hand,
            Map<String, Double> probabilities1,
            Map<String, Double> probabilities2,
            Double[] number_of_wins) { // Sử dụng mảng để truyền tham chiếu
        PokerGenerator generator = new PokerGenerator(); // Assuming this exists
        int total_new_card = num_of_random + 2;

        for (int i = 0; i < number_of_cases_simulation; i++) {
            String[] new_card = generator.generatePokerHand(total_new_card, card_on_desk, card_on_hand);
            String[] new_card_1 = Arrays.copyOf(new_card, num_of_random);
            String[] lastTwoElements = Arrays.copyOfRange(new_card, new_card.length - 2, new_card.length);

            String[] combined_cards_on_desk = new String[5];
            if (card_on_desk != null && card_on_desk.length > 0) {
                System.arraycopy(card_on_desk, 0, combined_cards_on_desk, 0, 5 - num_of_random);
            }

            System.arraycopy(new_card_1, 0, combined_cards_on_desk, 5 - num_of_random, num_of_random);

            String[] best_combination_1 = CardCombinationUtil.bestCombinationCards(card_on_hand, combined_cards_on_desk);
            String[] best_combination_2 = CardCombinationUtil.bestCombinationCards(lastTwoElements, combined_cards_on_desk);

            // Take result of the recent simulation
            get_card_infor newCardInfo1 = new get_card_infor(best_combination_1);
            String result1 = newCardInfo1.get_category_String();

            get_card_infor newCardInfo2 = new get_card_infor(best_combination_2);
            String result2 = newCardInfo2.get_category_String();

            int winning_index = newCardInfo1.compare_to(newCardInfo2);

            update_probability(result1, probabilities1);
            update_probability(result2, probabilities2);

            // Update winning probability
            if ((winning_index == 1)) {
                number_of_wins[0] += 1; // Cập nhật giá trị trong mảng
            }
        }
    }


    public static Map<String, Double>[] calculateProbabilityOnString(
            int num_of_random,
            int number_of_cases_simulation,
            String[] card_on_desk,
            String[] card_on_hand) {

        DecimalFormat decimalFormat = new DecimalFormat("0.####"); // Round to 4 decimal places
        Map<String, Double> probabilities1 = new HashMap<>();
        Map<String, Double> probabilities2 = new HashMap<>();
        Double[] totalWinningProbability = {0.0}; // Sử dụng mảng để lưu giá trị

        List<String> categories = Arrays.asList("pair", "two_pair", "three_of_a_kind", "four_of_a_kind",
                "flush", "full_house", "straight", "straight_flush",
                "royal_flush", "high_card", "winning_probability");

        for (String category : categories) {
            probabilities1.put(category, 0.0);
            probabilities2.put(category, 0.0);
        }

        // Simulate and update probabilities
        make_simulation(num_of_random, number_of_cases_simulation, card_on_desk, card_on_hand,
                probabilities1, probabilities2, totalWinningProbability);

        for (Map.Entry<String, Double> entry : probabilities1.entrySet()) {
            double probability = entry.getValue() / number_of_cases_simulation;
            probability = probability < 0.0001
                    ? Double.parseDouble(String.format("%.4e", probability))
                    : Double.parseDouble(decimalFormat.format(probability));
            probabilities1.put(entry.getKey(), probability);
        }

        for (Map.Entry<String, Double> entry : probabilities2.entrySet()) {
            double probability = entry.getValue() / number_of_cases_simulation;
            probability = probability < 0.0001
                    ? Double.parseDouble(String.format("%.4e", probability))
                    : Double.parseDouble(decimalFormat.format(probability));
            probabilities2.put(entry.getKey(), probability);
        }

        double winningProbability = totalWinningProbability[0] / number_of_cases_simulation;
        probabilities1.put("winning_probability",
                Double.parseDouble(decimalFormat.format(winningProbability)));


        return new Map[]{probabilities1, probabilities2};
    }


//    private static boolean checkForPair(String[] card_on_desk, String[] card_on_hand) {
//        // Kết hợp hai mảng thành một
//        String[] mergedArray = new String[card_on_desk.length + card_on_hand.length];
//        System.arraycopy(card_on_desk, 0, mergedArray, 0, card_on_desk.length);
//        System.arraycopy(card_on_hand, 0, mergedArray, card_on_desk.length, card_on_hand.length);
//
//        // Lấy phần "rank" (tên quân bài) từ tất cả các quân bài
//        List<String> ranks = new ArrayList<>();
//        for (String card : mergedArray) {
//            ranks.add(card.substring(0, card.length() - 1)); // Lấy phần rank
//        }
//
//        // Sử dụng một Map để đếm số lần xuất hiện của mỗi rank
//        Map<String, Integer> rankCounts = new HashMap<>();
//        for (String rank : ranks) {
//            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
//        }
//
//        // Kiểm tra xem có rank nào xuất hiện ít nhất 2 lần
//        for (int count : rankCounts.values()) {
//            if (count >= 2) {
//                return true; // Có ít nhất một cặp bài
//            }
//        }
//        return false; // Không có cặp bài nào
//    }
//
//    private static boolean checkForTwoPair(String[] card_on_desk, String[] card_on_hand) {
//        if ((card_on_desk.length) < 2)
//        {
//            return false;
//        }
//        // Kết hợp hai mảng thành một
//        String[] mergedArray = new String[card_on_desk.length + card_on_hand.length];
//        System.arraycopy(card_on_desk, 0, mergedArray, 0, card_on_desk.length);
//        System.arraycopy(card_on_hand, 0, mergedArray, card_on_desk.length, card_on_hand.length);
//
//        // Lấy phần "rank" (tên quân bài) từ tất cả các quân bài
//        List<String> ranks = new ArrayList<>();
//        for (String card : mergedArray) {
//            ranks.add(card.substring(0, card.length() - 1));
//        }
//
//        // Đếm số lần xuất hiện của từng rank
//        Map<String, Integer> rankCounts = new HashMap<>();
//        for (String rank : ranks) {
//            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
//        }
//
//        // Kiểm tra xem có ít nhất hai cặp (hai rank xuất hiện đúng 2 lần)
//        int pairs = 0;
//        for (int count : rankCounts.values()) {
//            if (count == 2) {
//                pairs++;
//            }
//        }
//
//        return pairs >= 2; // Có ít nhất hai cặp
//    }
//
//    private static boolean checkForThreeOfAKind(String[] card_on_desk, String[] card_on_hand) {
//
//        if ((card_on_desk.length) < 1)
//        {
//            return false;
//        }
//
//        // Kết hợp hai mảng thành một
//        String[] mergedArray = new String[card_on_desk.length + card_on_hand.length];
//        System.arraycopy(card_on_desk, 0, mergedArray, 0, card_on_desk.length);
//        System.arraycopy(card_on_hand, 0, mergedArray, card_on_desk.length, card_on_hand.length);
//
//        // Lấy phần "rank" (tên quân bài) từ tất cả các quân bài
//        List<String> ranks = new ArrayList<>();
//        for (String card : mergedArray) {
//            ranks.add(card.substring(0, card.length() - 1));
//        }
//
//        // Đếm số lần xuất hiện của từng rank
//        Map<String, Integer> rankCounts = new HashMap<>();
//        for (String rank : ranks) {
//            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
//        }
//
//        // Kiểm tra xem có rank nào xuất hiện ít nhất 3 lần
//        for (int count : rankCounts.values()) {
//            if (count >= 3) {
//                return true; // Có một bộ ba
//            }
//        }
//
//        return false; // Không có bộ ba
//    }
//


    public void printProbabilities(Map<String, Double> probabilities) {
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {

    }
}
