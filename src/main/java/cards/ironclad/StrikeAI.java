package cards.ironclad;

import cards.interfaces.AttackCardAI;
import monsters.AbstractMonsterAI;
import player.PlayerAI;

public class StrikeAI extends AttackCardAI {

    public StrikeAI () {
        cardId = CardId.STRIKE;
        cost = 1;
    }

    @Override
    public void playCard(PlayerAI player, AbstractMonsterAI monster) {
        monster.takeDamage(player, upgraded ? 9 : 6);
    }
}
