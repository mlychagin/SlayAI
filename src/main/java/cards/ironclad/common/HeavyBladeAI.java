package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI;

public class HeavyBladeAI extends AttackCardAI {

    public HeavyBladeAI() {
        cardId = CardId.HEAVY_BLADE;
        cost = 0;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        PlayerAI player = state.getPlayer();
        int strength = player.getPower(PowerAI.PowerTypeAI.STRENGTH);
        monster.takeDamage(player, 14 + (strength * (upgraded ? 4 : 2)));
    }
}
