package cards.neutral;

import cards.CardIdUtil.CardId;
import cards.interfaces.StateCardAI;
import dungeon.DungeonState;

public class SlimedAI extends StateCardAI {

    public SlimedAI(boolean upgraded) {
        cardId = CardId.SLIMED;
        this.upgraded = upgraded;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state) {
        exhaustCard(state);
    }
}
