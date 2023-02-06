package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;

public class BodySlamAI extends AttackCardAI {

    public BodySlamAI(boolean upgraded) {
        cardId = CardId.BODY_SLAM;
        this.upgraded = upgraded;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        PlayerAI player = state.getPlayer();
        monster.takeDamage(player, player.getBlock());
    }
}
