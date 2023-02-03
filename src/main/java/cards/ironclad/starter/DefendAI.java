package cards.ironclad.starter;

import cards.CardIdUtil.CardId;
import cards.interfaces.StateCardAI;
import dungeon.DungeonState;

public class DefendAI extends StateCardAI {

    public DefendAI() {
        cardId = CardId.DEFEND;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state) {
        state.getPlayer().addBlock(upgraded ? 8 : 5);
    }
}
