package cards.ironclad.starter;

import cards.interfaces.PlayerCardAI;
import player.PlayerAI;

public class DefendAI extends PlayerCardAI {

    public DefendAI() {
        cardId = CardId.DEFEND;
        cost = 1;
        exhaust = false;
    }

    @Override
    public void playCard(PlayerAI player) {
        player.addBlock(upgraded ? 8 : 5);
    }
}
