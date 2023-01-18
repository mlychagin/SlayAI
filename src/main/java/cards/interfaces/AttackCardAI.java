package cards.interfaces;

import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI;

public abstract class AttackCardAI extends AbstractCardAI {

    public abstract void playCard(PlayerAI player, AbstractMonsterAI monster);

}
