package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;

public class PommelStrikeAI extends AttackCardAI {

    public PommelStrikeAI() {
        cardId = CardId.POMMEL_STRIKE;
        cost = 1;
        draw = true;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        monster.takeDamage(state.getPlayer(), upgraded ? 10 : 9);
        state.drawCard();
        if (upgraded) {
            state.drawCard();
        }
    }
}
