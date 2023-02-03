package algorithms;

import cards.CardIdUtil.CardId;
import cards.interfaces.AbstractCardAI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import static cards.CardIdUtil.*;


public class CardPriorityUtil {

    private static void addMove(HashSet<SortedCardList> master, SortedCardList hand, SortedCardList current, int energy) {
        ArrayList<AbstractCardAI> handCards = hand.getCards();
        if (energy == 0 || current.containsDrawCard()) {
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
        if (current.containsDrawCard()) {
            return;
        }
        for (int i = index; i < handCards.size(); i++) {
            AbstractCardAI card = handCards.get(i);
            int cardEnergyCost = card.getEnergyCost();

            // Don't have enough energy
            if (energy < cardEnergyCost) {
                continue;
            }

            // Card isn't playable
            if (!isPlayableCard(card.getCardId())) {
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

        // Default order
        hand.getCardComparator().setPriorityMap(DEFAULT_CARD_PRIORITY);
        hand.prioritySortCards();
        addCardRecursive(sortedOrder, hand, new SortedCardList(), 0, energy);

        // Draw order
        if (hand.containsMultipurposeDrawCards()) {
            hand.getCardComparator().setPriorityMap(DRAW_CARD_PRIORITY);
            hand.prioritySortCards();
            addCardRecursive(sortedOrder, hand, new SortedCardList(), 0, energy);
        }

        for (SortedCardList move : sortedOrder) {
            result.add(move.getCards());
        }

        return result;
    }

    public static class SortedCardList {
        private final ArrayList<AbstractCardAI> cards;
        private final CardComparator cardComparator = new CardComparator();

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

        public boolean containsDrawCard() {
            if (cards.size() == 0) {
                return false;
            }
            switch (cards.get(cards.size() - 1).getCardId()) {

                case HAVOC:
                case TRUE_GRIT:
                case WARCRY:
                case BURNING_PACT:
                case INFERNAL_BLADE:
                case SHRUG_IT_OFF:
                case DUAL_WIELD:
                case OFFERING:
                case BATTLE_TRANCE:
                case DROPKICK:
                case POMMEL_STRIKE:
                case HEADBUTT:
                    return true;
                default:
                    return false;
            }
        }

        public CardComparator getCardComparator() {
            return cardComparator;
        }

        public boolean containsMultipurposeDrawCards() {
            for (AbstractCardAI card : cards) {
                switch (card.getCardId()) {
                    case DROPKICK:
                    case POMMEL_STRIKE:
                    case HEADBUTT:
                        return true;
                    default:
                        break;
                }
            }
            return false;
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
        private HashMap<CardId, Integer> priorityMap;

        public void setPriorityMap(HashMap<CardId, Integer> priorityMap) {
            this.priorityMap = priorityMap;
        }

        @Override
        public int compare(AbstractCardAI o1, AbstractCardAI o2) {
            return Integer.compare(priorityMap.get(o1.getCardId()), priorityMap.get(o2.getCardId()));
        }
    }
}
