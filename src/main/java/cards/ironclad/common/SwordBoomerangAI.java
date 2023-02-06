package cards.ironclad.common;

import cards.CardIdUtil.CardId;
import cards.interfaces.StateCardAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;

import java.util.ArrayList;
import java.util.Random;

public class SwordBoomerangAI extends StateCardAI {

    public SwordBoomerangAI(boolean upgraded) {
        cardId = CardId.SWORD_BOOMERANG;
        this.upgraded = upgraded;
        cost = 1;
    }

    @Override
    public void playCard(DungeonState state) {
        ArrayList<AbstractMonsterAI> monsters = state.getMonsters();
        AbstractMonsterAI monster;
        Random r = state.getMiscRandom();

        for (int i = 0; i < (upgraded ? 4 : 3); i++) {
            monster = monsters.get(r.nextInt(monsters.size()));
            if (!monster.isDead()) {
                monster.takeDamage(state.getPlayer(), 3);
            }
        }
    }
}
