package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.StateCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI;

public class ThunderclapAI extends StateCardAI {

    public ThunderclapAI() {
        cardId = CardId.THUNDERCLAP;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state) {
        PlayerAI player = state.getPlayer();
        for (AbstractMonsterAI monster : state.getMonsters()) {
            monster.takeDamage(player, upgraded ? 7 : 4);
            monster.addPower(PowerAI.PowerTypeAI.VULNERABLE, 1);
        }
    }
}
