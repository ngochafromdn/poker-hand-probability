package PokerHandProbability.Function;

import java.util.Arrays;

public class get_card_infor {

    private static final String[] SUIT = { "H", "D", "C", "S" };
    private static final String[] RANK = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
    private String[] cards;
    private String[] card_rank;
    private String[] card_suit;
    private String isFlush;
    private String[] isFlush_sequence_rank = new String[5] ;
    private String isStraight;
    private String isStraightFlush;
    private String isFourOfAKind;
    private String isFourOfAKind_kicker;
    private String[] isFullHouse = new String[2];
    private String isThreeOfAKind;
    private String[] isTwoPair = new String[2];
    private String isOnePair;
    private String isTwoPair_highestkick;
    private String[] isOnePair_kicker = new String[3];
    private String[] isThreeOfAKind_kicker = new String[2];

    // Function to set the hand of cards and initialize card attributes
    public get_card_infor(String[] hand) {
        validateInput(hand);
        this.cards = hand;
        this.card_rank = getCardRank();
        Arrays.sort(card_rank);
        this.card_suit = getCardSuit();
        if (Arrays.asList(card_rank).contains("A")) {
            this.isFlush = "Z";
            this.isStraight = "Z";
            this.isStraightFlush = "Z";
        } else {
            this.isFlush = card_rank[4];
            this.isStraight = card_rank[4];
            this.isStraightFlush = card_rank[4];
        }
    }

    // Function to validate the input hand
    private void validateInput(String[] hand) {
        if (hand.length != 5) {
            throw new IllegalArgumentException("Invalid input: must have 5 cards");
        }

        for (String card : hand) {
            if (!isValidCard(card)) {
                throw new IllegalArgumentException("Invalid input: " + card);
            }
        }
    }

    private boolean isValidCard(String card) {
        if (card.substring(0, 2).equals("10")) {
            return Arrays.asList(SUIT).contains(card.substring(2, 3));
        }
        return Arrays.asList(RANK).contains(card.substring(0, 1)) && Arrays.asList(SUIT).contains(card.substring(1, 2));
    }

    private String[] getCardRank() {
        String[] cardRank = new String[5];
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].substring(0, 2).equals("10")) {
                cardRank[i] = "@"; // @ is used to represent 10
            } else {
                cardRank[i] = cards[i].substring(0, 1);
            }
        }
        return cardRank;
    }

    private String[] getCardSuit() {
        String[] cardSuit = new String[5];
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].substring(0, 2).equals("10")) {
                cardSuit[i] = cards[i].substring(2, 3);
            } else {
                cardSuit[i] = cards[i].substring(1, 2);
            }
        }
        return cardSuit;
    }

    public boolean isFlush() {
        for (int i = 1; i < card_suit.length; i++) {
            if (!card_suit[i].equals(card_suit[0])) {
                return false;
            }
        }
        System.out.println(Arrays.toString(card_suit));
        for (int j = 3; j >= 0; j--) {
            isFlush_sequence_rank[j] = card_rank[j];
        }
        return true;
    }

    public boolean isStraight() {
        // convert value to number
        int[] cardValues = new int[5];
        for (int i = 0; i < card_rank.length; i++) {
            switch (card_rank[i]) {
                case "2": cardValues[i] = 2; break;
                case "3": cardValues[i] = 3; break;
                case "4": cardValues[i] = 4; break;
                case "5": cardValues[i] = 5; break;
                case "6": cardValues[i] = 6; break;
                case "7": cardValues[i] = 7; break;
                case "8": cardValues[i] = 8; break;
                case "9": cardValues[i] = 9; break;
                case "@": cardValues[i] = 10; break;
                case "J": cardValues[i] = 11; break;
                case "Q": cardValues[i] = 12; break;
                case "K": cardValues[i] = 13; break;
                case "A": cardValues[i] = 14; break; // A có giá trị cao nhất
            }
        }

        // ordering values
        Arrays.sort(cardValues);

        // specal case
        if (cardValues[0] == 2 && cardValues[1] == 3 && cardValues[2] == 4 && cardValues[3] == 5 && cardValues[4] == 14) {
            return true; // A được tính là 1
        }

        // check value sequence
        for (int i = 1; i < cardValues.length; i++) {
            if (cardValues[i] - cardValues[i - 1] != 1) {
                return false;
            }
        }

        return true;
    }


    public boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    public boolean isFourOfAKind() {
        for (int i = 0; i < (card_rank.length - 3); i++) {
            if (card_rank[i].equals(card_rank[i + 1]) && card_rank[i].equals(card_rank[i + 2])
                    && card_rank[i].equals(card_rank[i + 3])) {
                isFourOfAKind = card_rank[i + 3];
                if (i == 0) {
                    isFourOfAKind_kicker = card_rank[i + 4];
                } else {
                    isFourOfAKind_kicker = card_rank[0];
                }
                return true;
            }
        }
        return false;
    }

    public boolean isFullHouse() {
        if (card_rank[0].equals(card_rank[1]) && card_rank[0].equals(card_rank[2])
                && card_rank[3].equals(card_rank[4])) {
            isFullHouse[0] = card_rank[0];
            isFullHouse[1] = card_rank[3];
            return true;
        } else if (card_rank[0].equals(card_rank[1]) && card_rank[2].equals(card_rank[3])
                && card_rank[2].equals(card_rank[4])) {
            isFullHouse[0] = card_rank[2];
            isFullHouse[1] = card_rank[0];
            return true;
        } else {
            return false;
        }
    }

    public boolean isThreeOfAKind() {
        for (int i = 1; i < (card_rank.length - 2); i++) {
            if (card_rank[i].equals(card_rank[i + 1]) && card_rank[i].equals(card_rank[i + 2])) {
                isThreeOfAKind = card_rank[i];

                int count_kicker = 0;
                for (int j = card_rank.length - 1; j >= 0; j--) {
                    if (!card_rank[i].equals(card_rank[j])) {
                        isThreeOfAKind_kicker[count_kicker] = card_rank[j];
                        count_kicker++;
                    }

                }
                return true;
            }
        }
        return false;
    }

    public boolean isTwoPair() {
        int count = 0;
        for (int i = 0; i < (card_rank.length - 1); i++) {
            if (card_rank[i].equals(card_rank[i + 1])) {
                isTwoPair[count] = card_rank[i];

                if (i == 1) {
                    isTwoPair_highestkick = card_rank[0];
                }

                if (i == 2) {
                    isTwoPair_highestkick = card_rank[4];
                }

                count++;
            }
        }
        return count == 2;
    }

    public boolean isOnePair() {
        for (int i = 0; i < card_rank.length - 1; i++) {
            if (card_rank[i].equals(card_rank[i + 1])) {
                isOnePair = card_rank[i];

                int count_kicker = 0;
                // add kicker of Is One Pair
                for (int j = card_rank.length - 1; j >= 0; j--) {
                    if (!isOnePair.equals(card_rank[j])) {
                        isOnePair_kicker[count_kicker] = card_rank[j];
                        count_kicker++;
                    }
                }

                return true;
            }
        }
        return false;
    }

    public int get_category() {

        if (this.isStraightFlush())
            return 9;
        else if (this.isFourOfAKind())
            return 8;
        else if (this.isFullHouse())
            return 7;
        else if (this.isFlush())
            return 6;
        else if (this.isStraight())
            return 5;
        else if (this.isThreeOfAKind())
            return 4;
        else if (this.isTwoPair())
            return 3;
        else if (this.isOnePair())
            return 2;
        else
            return 1; // High Card
    }

    public String get_category_String() {
        int number = this.get_category();
        switch (number) {
            case 9:
                return "straight_flush";
            case 8:
                return "four_of_a_kind";
            case 7:
                return "full_house";
            case 6:
                return "flush";
            case 5:
                return "straight";
            case 4:
                return "three_of_a_kind";
            case 3:
                return "two_pair";
            case 2:
                return "pair";
            default:
                return "high_card";
        }
    }

    private int compareString(String a, String b) {
        if ((a == null) || (b == null)) {
            return 0;
        }
        int x = a.compareTo(b);
        return Integer.compare(x, 0);
    }

    public int compare_to(get_card_infor otherHand) {
        int hand1 = this.get_category();
        int hand2 = otherHand.get_category();

        if (hand1 != hand2) {
            return Integer.compare(hand1, hand2);
        } else {
            switch (hand1) {
                case 9:
                    return compareString(isStraightFlush, otherHand.isStraightFlush);
                case 8: {
                    int index = compareString(isFourOfAKind, otherHand.isFourOfAKind);
                    if (index == 0) {
                        index = compareString(isFourOfAKind_kicker, otherHand.isFourOfAKind_kicker);
                    }
                    return index;
                }
                case 7:
                    int index = compareString(isFullHouse[0], otherHand.isFullHouse[0]);
                    return (index == 0) ? compareString(isFullHouse[1], otherHand.isFullHouse[1]) : index;
                case 6: {
                    int compare_index = compareString(isFlush, otherHand.isFlush);

                    if (compare_index == 0) {
                        // Compare the highest kicker
                        compare_index = compareString(isFlush_sequence_rank[3], otherHand.isFlush_sequence_rank[3]);
                    }

                    if (compare_index == 0) {
                        // Compare the second highest kicker
                        compare_index = compareString(isFlush_sequence_rank[2], otherHand.isFlush_sequence_rank[2]);
                    }

                    if (compare_index == 0) {
                        // Compare the third highest kicker
                        compare_index = compareString(isFlush_sequence_rank[1], otherHand.isFlush_sequence_rank[1]);
                    }

                    if (compare_index == 0) {
                        // Compare the fourth highest kicker
                        compare_index = compareString(isFlush_sequence_rank[0], otherHand.isFlush_sequence_rank[0]);
                    }

                    return compare_index;
                }
                case 5:
                    return compareString(isStraight, otherHand.isStraight);
                case 4: {
                    int compare_index = compareString(isThreeOfAKind, otherHand.isThreeOfAKind);
                    if (compare_index == 0) {
                        // Compare the highest kicker
                        compare_index = compareString(isThreeOfAKind_kicker[0], otherHand.isThreeOfAKind_kicker[0]);
                    }

                    if (compare_index == 0) {
                        // Compare the second highest kicker
                        compare_index = compareString(isThreeOfAKind_kicker[1], otherHand.isThreeOfAKind_kicker[0]);
                    }

                    if (compare_index == 0) {
                        // Compare the second highest kicker
                        compare_index = compareString(isThreeOfAKind_kicker[2], otherHand.isThreeOfAKind_kicker[0]);
                    }

                    return compare_index;

                }
                case 3: {
                    // Compare the second pairs
                    int indexTwoPair = compareString(isTwoPair[1], otherHand.isTwoPair[1]);

                    // If the second pairs are equal, compare the first pairs
                    if (indexTwoPair == 0) {
                        indexTwoPair = compareString(isTwoPair[0], otherHand.isTwoPair[0]);
                    }

                    // If both pairs are equal, compare the highest kicker
                    if (indexTwoPair == 0) {
                        indexTwoPair = compareString(isTwoPair_highestkick, otherHand.isTwoPair_highestkick);
                    }

                    return indexTwoPair;
                }

                case 2: {
                    int compare_index = compareString(isOnePair, otherHand.isOnePair);

                    if (compare_index == 0) {
                        // Compare the highest kicker
                        compare_index = compareString(isOnePair_kicker[0], otherHand.isOnePair_kicker[0]);
                    }

                    if (compare_index == 0) {
                        // Compare the highest kicker
                        compare_index = compareString(isOnePair_kicker[1], otherHand.isOnePair_kicker[1]);
                    }

                    if (compare_index == 0) {
                        // Compare the highest kicker
                        compare_index = compareString(isOnePair_kicker[2], otherHand.isOnePair_kicker[2]);
                    }

                    return compare_index;
                }

                default:
                    if (Arrays.asList(card_rank).contains("A")) {
                        card_rank[4] = "Z";
                    }
                    if (Arrays.asList(otherHand.card_rank).contains("A")) {
                        otherHand.card_rank[4] = "Z";
                    }
                    return compareString(card_rank[4], otherHand.card_rank[4]);
            }
        }
    }

    // public static void main(String[] args) {
    // try {
    // // Sample test cases
    // String[] cards1 = { "JH", "JC", "8H", "9D", "10S" };
    // hand1.setHand(cards1);
    // String[] card_two = { "JH", "JC" };
    // String[] best_card = hand1.best_combination_cards(card_two, cards1);
    // for (String card : best_card) {
    // System.out.print(card + " ");
    // }

    // } catch (IllegalArgumentException e) {
    // System.out.println(e.getMessage());
    // }
    // }
}
