package cards.interfaces;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;

public abstract class AttackCardAI extends AbstractCardAI {

    public abstract void playCard(DungeonState state, AbstractMonsterAI monster);

}
