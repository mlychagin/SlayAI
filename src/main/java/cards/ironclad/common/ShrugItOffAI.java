package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.StateCardAI;
import dungeon.DungeonState;

public class ShrugItOffAI extends StateCardAI {

    public ShrugItOffAI(boolean upgraded) {
        cardId = CardId.SHRUG_IT_OFF;
        this.upgraded = upgraded;
        cost = 1;
        draw = true;
    }

    @Override
    public void playCard(DungeonState state) {
        state.getPlayer().addBlock(upgraded ? 11 : 8);
        state.drawCard();
    }
}
