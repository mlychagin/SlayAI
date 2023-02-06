package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.AttackCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;

public class TwinStrikeAI extends AttackCardAI {

    public TwinStrikeAI(boolean upgraded) {
        cardId = CardId.TWIN_STRIKE;
        this.upgraded = upgraded;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state, AbstractMonsterAI monster) {
        PlayerAI player = state.getPlayer();
        monster.takeDamage(player, upgraded ? 7 : 5);
        monster.takeDamage(player, upgraded ? 7 : 5);
    }
}
