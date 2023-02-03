package cards.neutral;

import cards.CardIdUtil.CardId;
import cards.interfaces.StateCardAI;
import dungeon.DungeonState;

public class WoundAI extends StateCardAI {
    public WoundAI() {
        cardId = CardId.WOUND;
        cost = 0;
    }

    @Override
    public void playCard(DungeonState state) {
        exhaustCard(state);
    }
}
