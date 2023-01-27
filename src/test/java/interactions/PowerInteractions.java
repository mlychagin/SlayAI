package interactions;

import monsters.act1.regular.JawWormAI;
import org.junit.Assert;
import org.junit.Test;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import static util.IroncladUtil.getStarterIronclad;

public class PowerInteractions {

    /*
     * TODO:
     * Entangle
     *
     * Fix power tests to have monster + player, instead of just player
     */

    @Test
    public void strengthPowerTest() {
        PlayerAI player = getStarterIronclad();
        int starterHealth = player.getHealth();

        player.addPower(PowerTypeAI.STRENGTH, 10);
        player.takeDamage(player, 10);

        Assert.assertEquals(player.getHealth() + 20, starterHealth);
    }

    @Test
    public void vulnerabilityPowerTest() {
        PlayerAI player = getStarterIronclad();
        int starterHealth = player.getHealth();

        player.addPower(PowerTypeAI.VULNERABLE, 1);
        player.takeDamage(player,10);

        Assert.assertEquals(player.getHealth() + 15, starterHealth);
    }

    @Test
    public void weakPowerTest() {
        PlayerAI player = getStarterIronclad();
        int starterHealth = player.getHealth();

        player.addPower(PowerTypeAI.WEAK, 2);
        player.takeDamage(player, 12);

        Assert.assertEquals(player.getHealth() + 9, starterHealth);
    }

    @Test
    public void ritualPowerTest() {
        PlayerAI player = getStarterIronclad();
        player.addPower(PowerTypeAI.RITUAL, 10);
        player.endTurnPower();

        Assert.assertEquals(player.getPower(PowerTypeAI.STRENGTH), 10);
    }

    @Test
    public void frailPowerTest() {
        PlayerAI player = getStarterIronclad();
        player.addPower(PowerTypeAI.FRAIL, 1);
        player.addBlock(12);

        Assert.assertEquals(player.getBlock(), 9);
    }

    @Test
    public void angryPowerTest() {
        PlayerAI player = getStarterIronclad();
        player.addPower(PowerTypeAI.ANGRY, 1);

        player.takeDamage(player, 1);
        Assert.assertEquals(player.getPower(PowerTypeAI.STRENGTH), 1);

    }
}
