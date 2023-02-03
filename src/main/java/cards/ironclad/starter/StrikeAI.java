package cards.ironclad.starter;

import cards.CardIdUtil.CardId;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;

public class StrikeAI extends AttackCardAI {

    public StrikeAI() {
        cardId = CardId.STRIKE;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        monster.takeDamage(state.getPlayer(), upgraded ? 9 : 6);
    }
}
