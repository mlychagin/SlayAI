package cards.ironclad.common;

import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;

public class AngerAI extends AttackCardAI {

    public AngerAI() {
        cardId = CardId.STRIKE;
        cost = 0;
        exhaust = false;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        monster.takeDamage(state.getPlayer(), upgraded ? 8 : 6);
        state.addCardToDiscardPile(new AngerAI());
    }
}
