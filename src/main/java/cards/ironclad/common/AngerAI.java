package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;

public class AngerAI extends AttackCardAI {

    public AngerAI(boolean upgraded) {
        cardId = CardId.ANGER;
        this.upgraded = upgraded;
        cost = 0;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        monster.takeDamage(state.getPlayer(), upgraded ? 8 : 6);
        state.addCardToDiscardPile(new AngerAI(upgraded));
    }
}
