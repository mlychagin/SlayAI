package algorithms;

import cards.interfaces.AbstractCardAI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import static util.DebugStaticVariables.DEBUG_GET_MOVE_SET;

public class CardPriorityUtil {
    public static Comparator<AbstractCardAI> cardComparator = new CardComparator();

    private static void addMove(HashSet<SortedCardList> master, SortedCardList hand, SortedCardList current, int energy) {
        ArrayList<AbstractCardAI> handCards = hand.getCards();
        if (energy == 0) {
            master.add(new SortedCardList(current));
            return;
        }
        for (AbstractCardAI handCard : handCards) {
            if (energy >= handCard.getEnergyCost()) {
                return;
            }
        }
        master.add(new SortedCardList(current));
    }

    private static void addCardRecursive(HashSet<SortedCardList> master, SortedCardList hand, SortedCardList current, int index, int energy) {
        ArrayList<AbstractCardAI> handCards = hand.getCards();
        addMove(master, hand, current, energy);
        for (int i = index; i < handCards.size(); i++) {
            AbstractCardAI card = handCards.get(i);
            int cardEnergyCost = card.getEnergyCost();
            if (energy < cardEnergyCost) {
                continue;
            }

            // Pick the card
            current.addCard(card);
            addCardRecursive(master, hand, current, i + 1, energy - cardEnergyCost);
            current.removeLastCard();
        }
    }

    public static HashSet<ArrayList<AbstractCardAI>> getMoveSet(ArrayList<AbstractCardAI> cards, int energy) {
        SortedCardList hand = new SortedCardList(cards);
        HashSet<ArrayList<AbstractCardAI>> result = new HashSet<>();
        HashSet<SortedCardList> sortedOrder = new HashSet<>();
        hand.prioritySortCards();
        addCardRecursive(sortedOrder, hand, new SortedCardList(), 0, energy);

        for (SortedCardList move : sortedOrder) {
            result.add(move.getCards());
        }

        if (DEBUG_GET_MOVE_SET) {
            for (ArrayList<AbstractCardAI> play : result) {
                ArrayList<AbstractCardAI> debugSet = new ArrayList<>(cards);
                int tmpEnergy = 0;
                for (AbstractCardAI card : play) {
                    tmpEnergy += card.getEnergyCost();
                    if (!debugSet.contains(card)) {
                        throw new RuntimeException("Invalid Play");
                    }
                    debugSet.remove(card);
                }
                if (tmpEnergy > energy) {
                    throw new RuntimeException("Invalid Play");
                }
            }
        }
        return result;
    }

    public static class SortedCardList {
        private final ArrayList<AbstractCardAI> cards;

        public SortedCardList() {
            cards = new ArrayList<>();
        }

        public SortedCardList(SortedCardList cards) {
            this.cards = new ArrayList<>(cards.cards);
        }

        public SortedCardList(ArrayList<AbstractCardAI> cards) {
            this.cards = cards;
        }

        public ArrayList<AbstractCardAI> getCards() {
            return cards;
        }

        public void addCard(AbstractCardAI card) {
            cards.add(card);
        }

        public void removeLastCard() {
            cards.remove(cards.size() - 1);
        }

        public void prioritySortCards() {
            cards.sort(cardComparator);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SortedCardList that = (SortedCardList) o;
            if (this.cards.size() != that.cards.size()) {
                return false;
            }
            for (int i = 0; i < cards.size(); i++) {
                if (!this.cards.get(i).equals(that.cards.get(i))) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            for (AbstractCardAI card : cards) {
                hash += card.hashCode();
            }
            return hash;
        }
    }

    private static class CardComparator implements Comparator<AbstractCardAI> {
        @Override
        public int compare(AbstractCardAI o1, AbstractCardAI o2) {
            return Integer.compare(o1.getPriority(), o2.getPriority());
        }
    }
}
