package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import powers.PowerAI.PowerTypeAI;

public class ClotheslineAI extends AttackCardAI {

    public ClotheslineAI() {
        cardId = CardId.CLOTHESLINE;
        cost = 2;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        monster.takeDamage(state.getPlayer(), upgraded ? 14 : 12);
        monster.addPower(PowerTypeAI.WEAK, upgraded ? 3 : 2);
    }

}
