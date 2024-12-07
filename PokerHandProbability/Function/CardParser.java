package PokerHandProbability.Function;
import java.util.List;
import java.util.ArrayList;

public class CardParser {

    public static List<String[]> parseCards(String[] cards) {
        List<String[]> parsedCards = new ArrayList<>();

        for (String card : cards) {
            // Tách số và chất của lá bài
            String number = card.substring(0, card.length() - 1); // Lấy phần số (chữ cái cuối không tính)
            String suit = card.substring(card.length() - 1); // Lấy phần chất (chữ cái cuối cùng)

            // Thêm vào danh sách với dạng new String[]{number, suit}
            parsedCards.add(new String[]{number, suit});
        }

        return parsedCards;
    }

    public static void main(String[] args) {
        String[] cards = {"10S", "JS", "2H"};
        List<String[]> result = parseCards(cards);

        // In kết quả để kiểm tra
        for (String[] card : result) {
            System.out.println("[" + card[0] + ", " + card[1] + "]");
        }
    }
}
