package cards.interfaces;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;

public abstract class AttackCardAI extends AbstractCardAI {

    public abstract void playCard(DungeonState state, AbstractMonsterAI monster);

}
