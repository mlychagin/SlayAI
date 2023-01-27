package cards.neutral;

import cards.interfaces.VoidCardAI;

public class SlimedAI extends VoidCardAI {
    public SlimedAI() {
        cardId = CardId.SLIMED;
        cost = 1;
        exhaust = true;
    }

    @Override
    public void playCard() {
    }
}
