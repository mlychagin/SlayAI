package cards.ironclad.starter;

import cards.CardIdUtil.CardId;
import cards.interfaces.StateCardAI;
import dungeon.DungeonState;

public class DefendAI extends StateCardAI {

    public DefendAI(boolean upgraded) {
        cardId = CardId.DEFEND;
        this.upgraded = upgraded;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state) {
        state.getPlayer().addBlock(upgraded ? 8 : 5);
    }
}
