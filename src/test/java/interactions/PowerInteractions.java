package interactions;

import cards.ironclad.StrikeAI;
import monsters.act1.JawWormAI;
import org.junit.Assert;
import org.junit.Test;
import player.PlayerAI;
import powers.PowerAI;

import static util.IroncladUtil.getStarterIronclad;
import static util.MonsterUtil.getJawWorm;

public class PowerInteractions {

    @Test
    public void weakPowerTest() {
        PlayerAI player = getStarterIronclad();
        JawWormAI jawWorm = getJawWorm();
        StrikeAI card = new StrikeAI();
        int starterHealth = jawWorm.getHealth();
        int damageTaken = (int) (6 * 0.75);

        player.addPower(PowerAI.PowerTypeAI.WEAK, 2);
        card.playCard(player, jawWorm);

        Assert.assertEquals(jawWorm.getHealth() + damageTaken, starterHealth);

    }


}
