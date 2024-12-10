package PokerHandProbability.Function;
import java.text.DecimalFormat;
import java.util.Map;

import java.util.*;

public class PokerProbabilityCalculator {

    public static void update_probability(String result, Map<String, Double> probabilities) {
        probabilities.put(result, probabilities.getOrDefault(result, 0.0) + 1);
    }

    public static void make_simulation(int num_of_random, int number_of_cases_simulation, String[] card_on_desk, String[] card_on_hand, Map<String, Double> probabilities) {
        PokerGenerator generator = new PokerGenerator(); // Assuming this exists

        for (int i = 0; i < number_of_cases_simulation; i++) {
            String[] new_card = generator.generatePokerHand(num_of_random, card_on_desk, card_on_hand);

            String[] combined_cards = new String[5];
            if (card_on_desk != null && card_on_desk.length > 0) {
                System.arraycopy(card_on_desk, 0, combined_cards, 0, 5 - num_of_random);
            }

            System.arraycopy(new_card, 0, combined_cards, 5 - num_of_random, num_of_random);

            String[] best_combination = CardCombinationUtil.bestCombinationCards(card_on_hand, combined_cards);

            // take result of the recent simulation
            get_card_infor newCardInfo = new get_card_infor(best_combination);
            String result = newCardInfo.get_category_String();

            update_probability(result, probabilities);
        }
    }

    public static Map<String, Double> calculateProbabilityOnString(int num_of_random, int number_of_cases_simulation, String[] card_on_desk, String[] card_on_hand) {
        DecimalFormat decimalFormat = new DecimalFormat("0.####"); // Làm tròn tối đa 4 chữ số thập phân
        Map<String, Double> probabilities = new HashMap<>();
        List<String> categories = Arrays.asList("pair", "two_pair", "three_of_a_kind", "four_of_a_kind", "flush", "full_house", "straight", "straight_flush", "royal_flush", "high_card");

        for (String category : categories) {
            probabilities.put(category, 0.0);
        }

        // Giả lập và cập nhật xác suất cho từng danh mục
        make_simulation(num_of_random, number_of_cases_simulation, card_on_desk, card_on_hand, probabilities);

        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            double probability = entry.getValue() / number_of_cases_simulation;

            // Làm tròn số, không chuyển đổi sang String
            if (probability < 0.0001) {
                probability = Double.parseDouble(String.format("%.4e", probability));
            } else {
                probability = Double.parseDouble(decimalFormat.format(probability));
            }

            System.out.println("Key: " + entry.getKey() + ", Probability: " + probability);
            probabilities.put(entry.getKey(), probability);
        }

        if (checkForPair(card_on_desk, card_on_hand)) {
            System.out.println("Run check for pair");
            probabilities.put("pair", 1.0);
        }

        return probabilities;
    }


    private static boolean checkForPair(String[] card_on_desk, String[] card_on_hand) {
        List<String> allCards = new ArrayList<>();
        Collections.addAll(allCards, card_on_desk);
        Collections.addAll(allCards, card_on_hand);

        // Lấy phần "rank" (tên quân bài) từ tất cả các quân bài
        List<String> ranks = new ArrayList<>();
        for (String card : allCards) {
            ranks.add(card.substring(0, card.length() - 1));
        }

        // In ra danh sách ranks để kiểm tra
        System.out.println("Ranks: " + ranks);

        // Kiểm tra xem có ít nhất một cặp bài nào đó
        for (String rank : ranks) {
            if (Collections.frequency(ranks, rank) == 2) {
                return true; // Nếu có một cặp bài, trả về true
            }
        }

        return false; // Không có cặp bài nào
    }


    public void printProbabilities(Map<String, Double> probabilities) {
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        PokerProbabilityCalculator calculator = new PokerProbabilityCalculator();

        String[] cardOnDesk = {"2H", "3D", "KC"};
        String[] cardOnHand = {"10S", "JH"};

        Map<String, Double> probabilities = calculator.calculateProbabilityOnString(5, 100000, cardOnDesk, cardOnHand);

        calculator.printProbabilities(probabilities);
    }
}
