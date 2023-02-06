package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.StateCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;

public class CleaveAI extends StateCardAI {

    public CleaveAI(boolean upgraded) {
        cardId = CardId.CLEAVE;
        this.upgraded = upgraded;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state) {
        PlayerAI player = state.getPlayer();
        for (AbstractMonsterAI monster : state.getMonsters()) {
            monster.takeDamage(player, upgraded ? 11 : 8);
        }
    }
}
