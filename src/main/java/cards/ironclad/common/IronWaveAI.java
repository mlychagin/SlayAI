package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;

public class IronWaveAI extends AttackCardAI {

    public IronWaveAI() {
        cardId = CardId.IRON_WAVE;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        PlayerAI player = state.getPlayer();
        monster.takeDamage(player, upgraded ? 7 : 5);
        player.addBlock(upgraded ? 7 : 5);
    }
}
