package cards.interfaces;

import cards.CardIdUtil.CardId;
import com.megacrit.cardcrawl.cards.AbstractCard;
import dungeon.DungeonState;

import java.util.Objects;

public abstract class AbstractCardAI {
    protected boolean upgraded;
    protected int cost;
    protected CardId cardId;
    protected boolean draw = false;
    protected transient AbstractCard card;

    public CardId getCardId() {
        return cardId;
    }

    public int getEnergyCost() {
        return cost;
    }

    protected void exhaustCard(DungeonState state) {
        state.getHand().remove(this);
        state.getExhaustPile().add(this);
    }

    public boolean isDraw() {
        return draw;
    }

    public AbstractCard getCard() {
        return card;
    }

    public void setCard(AbstractCard card) {
        this.card = card;
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
