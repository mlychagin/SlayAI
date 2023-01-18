package cards.ironclad;

import cards.interfaces.AttackCardAI;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

public class BashAI extends AttackCardAI {
    public BashAI () {
        cardId = CardId.BASH;
        cost = 2;
    }

    @Override
    public void playCard(PlayerAI player, AbstractMonsterAI monster) {
        monster.takeDamage(player, upgraded ? 10 : 8);
        monster.addPower(PowerTypeAI.VULNERABLE, upgraded ? 3 : 2);
    }

}
