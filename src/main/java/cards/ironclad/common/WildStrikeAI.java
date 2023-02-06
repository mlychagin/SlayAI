package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.AttackCardAI;
import cards.neutral.WoundAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;

public class WildStrikeAI extends AttackCardAI {

    public WildStrikeAI(boolean upgraded) {
        cardId = CardId.WILD_STRIKE;
        this.upgraded = upgraded;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        monster.takeDamage(state.getPlayer(), upgraded ? 17 : 12);
        state.addCardToDrawPile(new WoundAI());
    }
}
