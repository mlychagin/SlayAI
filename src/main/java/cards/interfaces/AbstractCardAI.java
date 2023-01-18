package cards.interfaces;

import java.util.HashMap;
import java.util.Objects;

public abstract class AbstractCardAI {
    public enum CardId {
        BASH,
        STRIKE,
        DEFEND
    }

    protected boolean upgraded;
    protected int cost;
    protected CardId cardId;

    public static HashMap<CardId, Integer> cardPriority = new HashMap<>();

    static {
        cardPriority.put(CardId.BASH, 40);
        cardPriority.put(CardId.STRIKE, 60);
        cardPriority.put(CardId.DEFEND, 70);
    }

    public CardId getCardId() {
        return cardId;
    }

    public int getPriority() {
        return cardPriority.get(cardId);
    }

    public int getEnergyCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCardAI that = (AbstractCardAI) o;
        return upgraded == that.upgraded && cost == that.cost && cardId == that.cardId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(upgraded, cost, cardId);
    }
}
