package interactions;

import monsters.act1.GreenLouseAI;
import util.IroncladUtil;
import cards.ironclad.StrikeAI;
import dungeon.DungeonState;
import monsters.act1.JawWormAI;
import monsters.act1.RedLouseAI;
import org.junit.Assert;
import org.junit.Test;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

public class MonsterInteractions {

    @Test
    public void jawWormTest (){
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        JawWormAI monster = new JawWormAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevStrength = monster.getPower(PowerTypeAI.STRENGTH);
            int prevBlock = monster.getBlock();
            monster.playTurn(player);
            switch (move) {
                case JawWormAI.CHOMP:
                    Assert.assertEquals(player.getHealth() + 11 + prevStrength, prevHealth);
                    break;
                case JawWormAI.BELLOW:
                    Assert.assertEquals(monster.getPower(PowerTypeAI.STRENGTH) - 3, prevStrength);
                    Assert.assertEquals(monster.getBlock() - 6, prevBlock);
                    break;
                case JawWormAI.THRASH:
                    Assert.assertEquals(player.getHealth() + 7 + prevStrength, prevHealth);
                    Assert.assertEquals(monster.getBlock() - 5, prevBlock);
                    break;
            }
            monster.getNextMove();
        }
    }

    @Test
    public void redLouseTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        RedLouseAI monster = new RedLouseAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();
        int bonusDamage = monster.getBonusDamage();
        Assert.assertEquals(monster.getPower(PowerTypeAI.CURL_UP), 1);

        new StrikeAI().playCard(player, monster);
        Assert.assertEquals(monster.getPower(PowerTypeAI.CURL_UP), 0);
        Assert.assertTrue(monster.getBlock() >= 3);

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevStrength = monster.getPower(PowerTypeAI.STRENGTH);
            monster.playTurn(player);
            switch (move) {
                case RedLouseAI.BITE:
                    Assert.assertEquals(player.getHealth() + bonusDamage + prevStrength, prevHealth);
                    break;
                case RedLouseAI.GROW:
                    Assert.assertEquals(monster.getPower(PowerTypeAI.STRENGTH) - 3, prevStrength);
                    break;
            }
            monster.getNextMove();
        }
    }

    @Test
    public void greenLouseTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        GreenLouseAI monster = new GreenLouseAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();
        int bonusDamage = monster.getBonusDamage();
        Assert.assertEquals(monster.getPower(PowerTypeAI.CURL_UP), 1);

        new StrikeAI().playCard(player, monster);
        Assert.assertEquals(monster.getPower(PowerTypeAI.CURL_UP), 0);
        Assert.assertTrue(monster.getBlock() >= 3);

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevWeak = player.getPower(PowerTypeAI.WEAK);
            monster.playTurn(player);
            switch (move) {
                case GreenLouseAI.BITE:
                    Assert.assertEquals(player.getHealth() + bonusDamage, prevHealth);
                    break;
                case GreenLouseAI.WEAK:
                    Assert.assertEquals(player.getPower(PowerTypeAI.WEAK) - 2, prevWeak);
                    break;
            }
            monster.getNextMove();
        }
    }
}
