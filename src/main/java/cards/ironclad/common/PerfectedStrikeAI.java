package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.AbstractCardAI;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;

import static cards.CardIdUtil.isStrikeCard;

public class PerfectedStrikeAI extends AttackCardAI {

    public PerfectedStrikeAI(boolean upgraded) {
        cardId = CardId.PERFECTED_STRIKE;
        this.upgraded = upgraded;
        cost = 2;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        int numOfStrikes = 0;

        // Hand
        for (AbstractCardAI card : state.getHand()) {
            if (isStrikeCard(card.getCardId())) {
                numOfStrikes++;
            }
        }

        // Discard
        for (AbstractCardAI card : state.getDiscardPile()) {
            if (isStrikeCard(card.getCardId())) {
                numOfStrikes++;
            }
        }

        // Discard
        for (AbstractCardAI card : state.getDrawPile()) {
            if (isStrikeCard(card.getCardId())) {
                numOfStrikes++;
            }
        }

        monster.takeDamage(state.getPlayer(), 6 + (numOfStrikes * (upgraded ? 3 : 2)));
    }
}
