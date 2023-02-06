package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.StateCardAI;
import dungeon.DungeonState;
import player.PlayerAI;
import powers.PowerAI;

public class FlexAI extends StateCardAI {

    public FlexAI(boolean upgraded) {
        cardId = CardId.FLEX;
        this.upgraded = upgraded;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state) {
        PlayerAI player = state.getPlayer();
        player.addPower(PowerAI.PowerTypeAI.STRENGTH, upgraded ? 4 : 2);
        player.addPower(PowerAI.PowerTypeAI.FLEX, upgraded ? 4 : 2);
    }
}
