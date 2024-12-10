package PokerHandProbability.Function;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;


public class PokerProbabilityCalculator {
    // Cập nhật xác suất khi có kết quả mới
    public static void update_probability(String result, Map<String, Double> probabilities) {
        switch (result) {
            case "high_card":
                probabilities.put("high_card", probabilities.get("high_card") + 1);
                break;
            case "pair":
                probabilities.put("pair", probabilities.get("pair") + 1);
                probabilities.put("two pair", probabilities.get("two pair") + 1);
                break;
            case "two pair":
                probabilities.put("two pair", probabilities.get("two pair") + 1);
                probabilities.put("pair", probabilities.get("pair") + 1);
                break;
            case "three_of_a_kind":
                probabilities.put("three_of_a_kind", probabilities.get("three_of_a_kind") + 1);
                probabilities.put("two pair", probabilities.get("two pair") + 1);
                probabilities.put("pair", probabilities.get("pair") + 1);
                break;
            case "four_of_a_kind":
                probabilities.put("four_of_a_kind", probabilities.get("four_of_a_kind") + 1);
                probabilities.put("three_of_a_kind", probabilities.get("three_of_a_kind") + 1);
                probabilities.put("two pair", probabilities.get("two pair") + 1);
                probabilities.put("pair", probabilities.get("pair") + 1);
                break;
            case "flush":
                probabilities.put("flush", probabilities.get("flush") + 1);
                break;
            case "full_house":
                probabilities.put("full_house", probabilities.get("full_house") + 1);
                probabilities.put("three_of_a_kind", probabilities.get("three_of_a_kind") + 1);
                probabilities.put("pair", probabilities.get("pair") + 1);
                break;
            case "straight":
                probabilities.put("straight", probabilities.get("straight") + 1);
                break;
            case "straight_flush":
                probabilities.put("straight_flush", probabilities.get("straight_flush") + 1);
                probabilities.put("flush", probabilities.get("flush") + 1);
                probabilities.put("straight", probabilities.get("straight") + 1);
                break;
            case "royal_flush":
                probabilities.put("royal_flush", probabilities.get("royal_flush") + 1);
                probabilities.put("straight_flush", probabilities.get("straight_flush") + 1);
                probabilities.put("flush", probabilities.get("flush") + 1);
                probabilities.put("straight", probabilities.get("straight") + 1);
                break;
            default:
                System.out.println("Unknown result: " + result);
                break;
        }
    }

    // Hàm làm simulation
    public static void make_simulation(int num_of_random, int number_of_cases_simulation, String[] card_on_desk, String[] card_on_hand, Map<String, Double> probabilities) {
        PokerGenerator generator = new PokerGenerator(); // Khởi tạo đối tượng PokerGenerator

        for (int i = 0; i < number_of_cases_simulation; i++) {
            // Gọi hàm generatePokerHand để lấy bộ bài mới
            String[] new_card = generator.generatePokerHand(num_of_random, card_on_desk, card_on_hand);

            // Tạo bộ bài kết hợp từ bài đã có trên bàn và bài mới (không bao gồm bài trên tay người chơi)
            String[] combined_cards = new String[5];

            // Đưa bài trên bàn vào mảng combined_cards
            System.arraycopy(card_on_desk, 0, combined_cards, 0, 5- num_of_random);

            // Đưa các quân bài mới vào mảng combined_cards
            System.arraycopy(new_card, 0, combined_cards, 5 - num_of_random, num_of_random);

            // Tính toán sự kết hợp tốt nhất từ bộ bài kết hợp và bài trên tay người chơi
            String[] best_combination = CardCombinationUtil.bestCombinationCards(card_on_hand, combined_cards);

            // In ra kết quả (nếu cần)
            System.out.println("Best combination: " + Arrays.toString(best_combination));


            // Tạo đối tượng GetCardInfo và lấy ket qua
            get_card_infor newCardInfo = new get_card_infor(best_combination);
            String result = newCardInfo.get_category_String();

            // Cập nhật xác suất
            update_probability(result, probabilities);
        }
    }

    // Tính xác suất trên chuỗi kết quả
    public static Map<String, Double> calculateProbabilityOnString(int num_of_random, int number_of_cases_simulation, String[] card_on_desk, String[] card_on_hand) {
        Map<String, Double> probabilities = new HashMap<>();
        probabilities.put("pair", 0.0);
        probabilities.put("two pair", 0.0);
        probabilities.put("three_of_a_kind", 0.0);
        probabilities.put("four_of_a_kind", 0.0);
        probabilities.put("flush", 0.0);
        probabilities.put("full_house", 0.0);
        probabilities.put("straight", 0.0);
        probabilities.put("straight_flush", 0.0);
        probabilities.put("royal_flush", 0.0);
        probabilities.put("high_card", 0.0);

        // Chạy simulation để cập nhật xác suất
        make_simulation(num_of_random, number_of_cases_simulation, card_on_desk, card_on_hand, probabilities);

        // Tính toán xác suất từ số lần xuất hiện
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            probabilities.put(entry.getKey(), entry.getValue() / number_of_cases_simulation);
        }

        return probabilities;
    }

    // In ra các xác suất
    public void printProbabilities(Map<String, Double> probabilities) {
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        PokerProbabilityCalculator calculator = new PokerProbabilityCalculator();

        // Các quân bài đã có trên bộ bài và trên tay người chơi
        String[] cardOnDesk = { "2H", "3D", "KC" };
        String[] cardOnHand = { "10S", "JH" };

        // Thực hiện simulation 100 lần
        Map<String, Double> probabilities = calculator.calculateProbabilityOnString(5, 100, cardOnDesk, cardOnHand);

        // In ra các xác suất sau khi simulation
        calculator.printProbabilities(probabilities);
    }
}
