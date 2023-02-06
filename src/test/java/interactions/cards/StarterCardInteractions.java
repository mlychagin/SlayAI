package interactions.cards;

import cards.ironclad.starter.BashAI;
import cards.ironclad.starter.DefendAI;
import cards.ironclad.starter.StrikeAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import org.junit.Assert;
import org.junit.Test;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import static util.IroncladUtil.getJawWormState;

public class StarterCardInteractions {

    @Test
    public void starterIroncladCards() {
        DungeonState state = getJawWormState();
        PlayerAI player = state.getPlayer();
        AbstractMonsterAI monster = state.getMonsters().get(0);

        BashAI bash = new BashAI(false);
        DefendAI defend = new DefendAI(false);
        StrikeAI strike = new StrikeAI(false);

        strike.playCard(state, monster);
        Assert.assertEquals(monster.getHealth(), 38);

        bash.playCard(state, monster);
        Assert.assertEquals(monster.getHealth(), 30);
        Assert.assertEquals(monster.getPower(PowerTypeAI.VULNERABLE), 2);

        defend.playCard(state);
        Assert.assertEquals(player.getBlock(), 5);

        strike.playCard(state, monster);
        Assert.assertEquals(monster.getHealth(), 21);
    }

}
