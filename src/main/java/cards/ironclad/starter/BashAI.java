package cards.ironclad.starter;

import cards.CardIdUtil.CardId;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import powers.PowerAI.PowerTypeAI;

public class BashAI extends AttackCardAI {

    public BashAI(boolean upgraded) {
        cardId = CardId.BASH;
        this.upgraded = upgraded;
        cost = 2;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        monster.takeDamage(state.getPlayer(), upgraded ? 10 : 8);
        monster.addPower(PowerTypeAI.VULNERABLE, upgraded ? 3 : 2);
    }

}
