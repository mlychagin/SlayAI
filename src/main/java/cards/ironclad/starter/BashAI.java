package cards.ironclad.starter;

import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import powers.PowerAI.PowerTypeAI;

public class BashAI extends AttackCardAI {
    public BashAI() {
        cardId = CardId.BASH;
        cost = 2;
        exhaust = false;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        monster.takeDamage(state.getPlayer(), upgraded ? 10 : 8);
        monster.addPower(PowerTypeAI.VULNERABLE, upgraded ? 3 : 2);
    }

}
