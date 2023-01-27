package cards.interfaces;

import java.util.HashMap;
import java.util.Objects;

public abstract class AbstractCardAI {
    public static HashMap<CardId, Integer> cardPriority = new HashMap<>();

    static {
        cardPriority.put(CardId.BASH, 40);
        cardPriority.put(CardId.STRIKE, 60);
        cardPriority.put(CardId.DEFEND, 70);
        cardPriority.put(CardId.SLIMED, 99);
        cardPriority.put(CardId.ANGER, 60);
    }

    protected boolean upgraded;
    protected int cost;
    protected CardId cardId;
    protected boolean exhaust;

    public CardId getCardId() {
        return cardId;
    }

    public int getPriority() {
        return cardPriority.get(cardId);
    }

    public int getEnergyCost() {
        return cost;
    }

    public boolean isExhaust() {
        return exhaust;
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

    public enum CardId {
        BASH,
        STRIKE,
        DEFEND,
        SLIMED,
        ANGER
    }
}
