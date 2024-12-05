package PokerHandProbability.Function;

import java.util.*;
import java.util.stream.Collectors;

public class PokerProbabilityCalculator {

    private static final Map<String, Integer> RANK_VALUES = Map.ofEntries(
            Map.entry("2", 2), Map.entry("3", 3), Map.entry("4", 4), Map.entry("5", 5),
            Map.entry("6", 6), Map.entry("7", 7), Map.entry("8", 8), Map.entry("9", 9),
            Map.entry("10", 10), Map.entry("J", 11), Map.entry("Q", 12), Map.entry("K", 13),
            Map.entry("A", 14)
    );

    // Kiểm tra Straight
    public static boolean checkStraight(List<String> ranksInHand) {
        try {
            List<Integer> sortedRanks = new ArrayList<>();
            for (String rank : ranksInHand) {
                sortedRanks.add(RANK_VALUES.get(rank));
            }
            Collections.sort(sortedRanks);
            for (int i = 0; i < sortedRanks.size() - 1; i++) {
                if (sortedRanks.get(i) + 1 != sortedRanks.get(i + 1)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Kiểm tra Flush (5 lá bài cùng chất)
    public static boolean checkFlush(List<String> suitsInHand) {
        Set<String> uniqueSuits = new HashSet<>(suitsInHand);
        return uniqueSuits.size() == 1;
    }

    // Kiểm tra Royal Flush (10, J, Q, K, A tất cả cùng một chất)
    public static boolean checkRoyalFlush(List<String> ranksInHand, List<String> suitsInHand) {
        List<String> royalRanks = Arrays.asList("10", "J", "Q", "K", "A");
        return checkFlush(suitsInHand) && ranksInHand.containsAll(royalRanks);
    }

    // Kiểm tra Straight Flush
    public static boolean checkStraightFlush(List<String> ranksInHand, List<String> suitsInHand) {
        return checkStraight(ranksInHand) && checkFlush(suitsInHand);
    }

    public static Map<String, Double> calculateProbability(
            List<String[]> knownCardsOnDeck, List<String[]> myFiveCards,
            int numRandomCases, int decimalPlaces
    ) {
        List<String> ranks = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
        List<String> suits = Arrays.asList("H", "D", "C", "S");

        Set<String[]> allCards = new HashSet<>();
        for (String rank : ranks) {
            for (String suit : suits) {
                allCards.add(new String[]{rank, suit});
            }
        }

        Set<String[]> knownCardsSet = new HashSet<>(knownCardsOnDeck);
        knownCardsSet.addAll(myFiveCards);
        Set<String[]> remainingCards = new HashSet<>(allCards);
        remainingCards.removeAll(knownCardsSet);

        Map<String, Double> probabilities = new HashMap<>();
        probabilities.put("pair", 0.0);
        probabilities.put("three_of_a_kind", 0.0);
        probabilities.put("four_of_a_kind", 0.0);
        probabilities.put("flush", 0.0);
        probabilities.put("full_house", 0.0);
        probabilities.put("straight", 0.0);
        probabilities.put("straight_flush", 0.0);
        probabilities.put("royal_flush", 0.0);

        List<String[]> remainingCardsList = new ArrayList<>(remainingCards);
        Random random = new Random();

        for (int i = 0; i < numRandomCases; i++) {
            Collections.shuffle(remainingCardsList, random);
            List<String[]> hand = remainingCardsList.subList(0, 5);
            List<String[]> totalHand = new ArrayList<>(myFiveCards);
            totalHand.addAll(hand);

            List<String> ranksInHand = new ArrayList<>();
            List<String> suitsInHand = new ArrayList<>();
            for (String[] card : totalHand) {
                ranksInHand.add(card[0]);
                suitsInHand.add(card[1]);
            }

            Map<String, Long> rankCounts = ranksInHand.stream()
                    .collect(Collectors.groupingBy(r -> r, Collectors.counting()));
            Map<String, Long> suitCounts = suitsInHand.stream()
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

            // Kiểm tra Royal Flush, Straight Flush, và các sự kiện khác
            if (checkRoyalFlush(ranksInHand, suitsInHand)) {
                probabilities.put("royal_flush", probabilities.get("royal_flush") + 1);
            } else if (checkStraightFlush(ranksInHand, suitsInHand)) {
                probabilities.put("straight_flush", probabilities.get("straight_flush") + 1);
            }

            if (checkStraight(ranksInHand)) {
                probabilities.put("straight", probabilities.get("straight") + 1);
            }
            if (checkFlush(suitsInHand)) {
                probabilities.put("flush", probabilities.get("flush") + 1);
            }
            if (rankCounts.containsValue(4L)) {
                probabilities.put("four_of_a_kind", probabilities.get("four_of_a_kind") + 1);
            }
            if (rankCounts.containsValue(3L) && rankCounts.containsValue(2L)) {
                probabilities.put("full_house", probabilities.get("full_house") + 1);
            }
            if (rankCounts.containsValue(3L)) {
                probabilities.put("three_of_a_kind", probabilities.get("three_of_a_kind") + 1);
            }
            if (rankCounts.containsValue(2L)) {
                probabilities.put("pair", probabilities.get("pair") + 1);
            }
        }

        // Tính xác suất
        for (String key : probabilities.keySet()) {
            probabilities.put(key, Math.round((probabilities.get(key) / numRandomCases) * Math.pow(1000, decimalPlaces)) / Math.pow(1000, decimalPlaces));
        }

        return probabilities;
    }

    // Hàm tính xác suất từ chuỗi các lá bài
    public static Map<String, Double> calculateProbabilityOnString(
            String[] knownCardsOnDeck, String[] myFiveCards,
            int numRandomCases, int decimalPlaces
    ) {
        // Dùng parseCards để chuyển đổi chuỗi các lá bài thành các cặp số và chất
        List<String[]> knownCardsParsed = CardParser.parseCards(knownCardsOnDeck);
        List<String[]> myFiveCardsParsed = CardParser.parseCards(myFiveCards);

        return calculateProbability(knownCardsParsed, myFiveCardsParsed, numRandomCases, decimalPlaces);
    }

    public static void main(String[] args) {
        List<String[]> knownCardsOnDeck = List.of(
                new String[]{"A", "hearts"},
                new String[]{"K", "spades"},
                new String[]{"Q", "diamonds"}
        );

        List<String[]> myFiveCards = List.of(
                new String[]{"9", "S"},
                new String[]{"A", "S"},
                new String[]{"10", "S"},
                new String[]{"9", "S"},
                new String[]{"8", "S"}
        );

        Map<String, Double> probabilities = calculateProbability(knownCardsOnDeck, myFiveCards, 3000000, 2);
        System.out.println(probabilities);
    }
}
