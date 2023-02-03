package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.AbstractCardAI;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;

import static cards.CardIdUtil.isAttackCard;

public class ClashAI extends AttackCardAI {

    public ClashAI() {
        cardId = CardId.CLASH;
        cost = 0;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        for (AbstractCardAI card : state.getHand()) {
            if (isAttackCard(card.getCardId())) {
                throw new RuntimeException();
            }
        }
        monster.takeDamage(state.getPlayer(), upgraded ? 18 : 14);
    }
}
