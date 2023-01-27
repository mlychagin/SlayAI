package interactions;

import cards.ironclad.starter.BashAI;
import cards.ironclad.starter.DefendAI;
import cards.ironclad.starter.StrikeAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import monsters.act1.regular.JawWormAI;
import org.junit.Assert;
import org.junit.Test;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;
import util.IroncladUtil;
import util.MonsterUtil;

import static util.IroncladUtil.getIronCladDungeonState;
import static util.IroncladUtil.getJawWormState;

public class CardInteractions {

    /*
     * TODO:
     *  Anger
     */

    @Test
    public void starterIroncladCards() {
        DungeonState state = getJawWormState();
        PlayerAI player = state.getPlayer();
        AbstractMonsterAI monster = state.getMonsters().get(0);

        BashAI bash = new BashAI();
        DefendAI defend = new DefendAI();
        StrikeAI strike = new StrikeAI();

        strike.playCard(state, monster);
        Assert.assertEquals(monster.getHealth(), 38);

        bash.playCard(state, monster);
        Assert.assertEquals(monster.getHealth(), 30);
        Assert.assertEquals(monster.getPower(PowerTypeAI.VULNERABLE), 2);

        defend.playCard(player);
        Assert.assertEquals(player.getBlock(), 5);

        strike.playCard(state, monster);
        Assert.assertEquals(monster.getHealth(), 21);
    }
}
