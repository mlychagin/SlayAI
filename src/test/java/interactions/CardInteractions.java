package interactions;

import util.IroncladUtil;
import util.MonsterUtil;
import cards.ironclad.BashAI;
import cards.ironclad.DefendAI;
import cards.ironclad.StrikeAI;
import monsters.act1.JawWormAI;
import org.junit.Assert;
import org.junit.Test;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

public class CardInteractions {

    @Test
    public void starterIroncladCards () {
        PlayerAI player = IroncladUtil.getStarterIronclad();
        JawWormAI monster = MonsterUtil.getJawWorm();

        BashAI bash = new BashAI();
        DefendAI defend = new DefendAI();
        StrikeAI strike = new StrikeAI();

        strike.playCard(player, monster);
        Assert.assertEquals(monster.getHealth(), 38);

        bash.playCard(player, monster);
        Assert.assertEquals(monster.getHealth(), 30);
        Assert.assertEquals(monster.getPower(PowerTypeAI.VULNERABLE), 2);

        defend.playCard(player);
        Assert.assertEquals(player.getBlock(), 5);

        strike.playCard(player, monster);
        Assert.assertEquals(monster.getHealth(), 21);
    }
}
