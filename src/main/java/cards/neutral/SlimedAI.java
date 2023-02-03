package cards.neutral;

import cards.CardIdUtil.CardId;
import cards.interfaces.StateCardAI;
import dungeon.DungeonState;

public class SlimedAI extends StateCardAI {
    public SlimedAI() {
        cardId = CardId.SLIMED;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state) {
        exhaustCard(state);
    }
}
