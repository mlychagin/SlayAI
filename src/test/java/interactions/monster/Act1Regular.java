package interactions.monster;

import cards.interfaces.AbstractCardAI;
import cards.ironclad.starter.StrikeAI;
import cards.neutral.SlimedAI;
import dungeon.DungeonState;
import monsters.act1.regular.*;
import org.junit.Assert;
import org.junit.Test;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;
import util.IroncladUtil;

import java.util.ArrayList;

public class Act1Regular {

    /*
     * TODO:
     * Looter - New Power: Thievery, New Move: Escape
     * Mugger - (Same as looter)
     */

    @Test
    public void jawWormTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        JawWormAI monster = new JawWormAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevStrength = monster.getPower(PowerTypeAI.STRENGTH);
            int prevBlock = monster.getBlock();
            monster.playTurn(state);
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
            monster.getNextMove(state);
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

        new StrikeAI().playCard(state, monster);
        Assert.assertEquals(monster.getPower(PowerTypeAI.CURL_UP), 0);
        Assert.assertTrue(monster.getBlock() >= 3);

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevStrength = monster.getPower(PowerTypeAI.STRENGTH);
            monster.playTurn(state);
            switch (move) {
                case RedLouseAI.BITE:
                    Assert.assertEquals(player.getHealth() + bonusDamage + prevStrength, prevHealth);
                    break;
                case RedLouseAI.GROW:
                    Assert.assertEquals(monster.getPower(PowerTypeAI.STRENGTH) - 3, prevStrength);
                    break;
            }
            monster.getNextMove(state);
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

        new StrikeAI().playCard(state, monster);
        Assert.assertEquals(monster.getPower(PowerTypeAI.CURL_UP), 0);
        Assert.assertTrue(monster.getBlock() >= 3);

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevWeak = player.getPower(PowerTypeAI.WEAK);
            monster.playTurn(state);
            switch (move) {
                case GreenLouseAI.BITE:
                    Assert.assertEquals(player.getHealth() + bonusDamage, prevHealth);
                    break;
                case GreenLouseAI.WEAK:
                    Assert.assertEquals(player.getPower(PowerTypeAI.WEAK) - 2, prevWeak);
                    break;
            }
            monster.getNextMove(state);
        }
    }

    @Test
    public void cultistTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        CultistAI monster = new CultistAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevStrength = monster.getPower(PowerTypeAI.STRENGTH);
            monster.playTurn(state);
            switch (move) {
                case CultistAI.DARK_STRIKE:
                    Assert.assertEquals(player.getHealth() + 6 + prevStrength, prevHealth);
                    Assert.assertEquals(monster.getPower(PowerTypeAI.STRENGTH) - 3, prevStrength);
                    break;
                case CultistAI.INCANTATION:
                    Assert.assertEquals(monster.getPower(PowerTypeAI.RITUAL), 3);
                    Assert.assertEquals(monster.getPower(PowerTypeAI.STRENGTH), 0);
                    break;
            }
            monster.getNextMove(state);
        }
    }

    private static int getOccurrences(ArrayList<AbstractCardAI> cards, AbstractCardAI card) {
        int num = 0;
        for (AbstractCardAI c : cards) {
            if (c.equals(card)) {
                num++;
            }
        }
        return num;
    }

    @Test
    public void acidSlimeLargeTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        AcidSlimeLargeAI monster = new AcidSlimeLargeAI();
        PlayerAI player = state.getPlayer();
        state.getMonsters().add(monster);

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevWeak = player.getPower(PowerTypeAI.WEAK);
            int prevSlimed = getOccurrences(state.getDiscardPile(), new SlimedAI());
            monster.playTurn(state);
            switch (move) {
                case AcidSlimeLargeAI.CORROSIVE_SPIT:
                    Assert.assertEquals(player.getHealth() + 11, prevHealth);
                    Assert.assertEquals(prevSlimed + 2, getOccurrences(state.getDiscardPile(), new SlimedAI()));
                    break;
                case AcidSlimeLargeAI.LICK:
                    Assert.assertEquals(player.getPower(PowerTypeAI.WEAK) - prevWeak, 2);
                    break;
                case AcidSlimeLargeAI.TACKLE:
                    Assert.assertEquals(player.getHealth() + 16, prevHealth);
                    break;
            }
            monster.getNextMove(state);
        }

        monster.takeDamage(player, 50);
        Assert.assertEquals((byte) monster.getCurrentMove(), AcidSlimeLargeAI.SPLIT);
        monster.playTurn(state);
        Assert.assertEquals(2, state.getMonsters().size());
    }

    @Test
    public void acidSlimeMediumTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        AcidSlimeMediumAI monster = new AcidSlimeMediumAI();
        PlayerAI player = state.getPlayer();
        state.getMonsters().add(monster);

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevWeak = player.getPower(PowerTypeAI.WEAK);
            int prevSlimed = getOccurrences(state.getDiscardPile(), new SlimedAI());
            monster.playTurn(state);
            switch (move) {
                case AcidSlimeMediumAI.CORROSIVE_SPIT:
                    Assert.assertEquals(player.getHealth() + 7, prevHealth);
                    Assert.assertEquals(prevSlimed + 1, getOccurrences(state.getDiscardPile(), new SlimedAI()));
                    break;
                case AcidSlimeMediumAI.LICK:
                    Assert.assertEquals(player.getPower(PowerTypeAI.WEAK) - prevWeak, 1);
                    break;
                case AcidSlimeMediumAI.TACKLE:
                    Assert.assertEquals(player.getHealth() + 10, prevHealth);
                    break;
            }
            monster.getNextMove(state);
        }

        monster.takeDamage(player, 14);
        Assert.assertTrue(monster.getCurrentMove() != AcidSlimeLargeAI.SPLIT);
        monster.playTurn(state);
        Assert.assertEquals(1, state.getMonsters().size());
    }

    @Test
    public void acidSlimeSmallTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        AcidSlimeSmallAI monster = new AcidSlimeSmallAI();
        PlayerAI player = state.getPlayer();
        state.getMonsters().add(monster);

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevWeak = player.getPower(PowerTypeAI.WEAK);
            monster.playTurn(state);
            switch (move) {
                case AcidSlimeSmallAI.LICK:
                    Assert.assertEquals(player.getPower(PowerTypeAI.WEAK) - prevWeak, 1);
                    break;
                case AcidSlimeSmallAI.TACKLE:
                    Assert.assertEquals(player.getHealth() + 3, prevHealth);
                    break;
            }
            monster.getNextMove(state);
        }

        monster.takeDamage(player, 5);
        Assert.assertTrue(monster.getCurrentMove() != AcidSlimeLargeAI.SPLIT);
        monster.playTurn(state);
        Assert.assertEquals(1, state.getMonsters().size());
    }


    @Test
    public void spikeSlimeLargeTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        SpikeSlimeLargeAI monster = new SpikeSlimeLargeAI();
        PlayerAI player = state.getPlayer();
        state.getMonsters().add(monster);

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevFrail = player.getPower(PowerTypeAI.FRAIL);
            int prevSlimed = getOccurrences(state.getDiscardPile(), new SlimedAI());
            monster.playTurn(state);
            switch (move) {
                case SpikeSlimeLargeAI.FLAME_TACKLE:
                    Assert.assertEquals(player.getHealth() + 16, prevHealth);
                    Assert.assertEquals(prevSlimed + 2, getOccurrences(state.getDiscardPile(), new SlimedAI()));
                    break;
                case SpikeSlimeLargeAI.LICK:
                    Assert.assertEquals(player.getPower(PowerTypeAI.FRAIL) - prevFrail, 2);
                    break;
            }
            monster.getNextMove(state);
        }

        monster.takeDamage(player, 50);
        monster.playTurn(state);
        Assert.assertEquals(2, state.getMonsters().size());
    }

    @Test
    public void spikeSlimeMediumTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        SpikeSlimeMediumAI monster = new SpikeSlimeMediumAI();
        PlayerAI player = state.getPlayer();
        state.getMonsters().add(monster);

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevFrail = player.getPower(PowerTypeAI.FRAIL);
            int prevSlimed = getOccurrences(state.getDiscardPile(), new SlimedAI());
            monster.playTurn(state);
            switch (move) {
                case SpikeSlimeMediumAI.FLAME_TACKLE:
                    Assert.assertEquals(player.getHealth() + 8, prevHealth);
                    Assert.assertEquals(prevSlimed + 1, getOccurrences(state.getDiscardPile(), new SlimedAI()));
                    break;
                case SpikeSlimeMediumAI.LICK:
                    Assert.assertEquals(player.getPower(PowerTypeAI.FRAIL) - prevFrail, 1);
                    break;
            }
            monster.getNextMove(state);
        }

        monster.takeDamage(player, 14);
        monster.playTurn(state);
        Assert.assertEquals(1, state.getMonsters().size());
    }

    @Test
    public void spikeSlimeSmallTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        SpikeSlimeSmallAI monster = new SpikeSlimeSmallAI();
        PlayerAI player = state.getPlayer();
        state.getMonsters().add(monster);

        for (int i = 0; i < 100; i++) {
            int prevHealth = player.getHealth();
            monster.playTurn(state);
            Assert.assertEquals(player.getHealth() + 5, prevHealth);
            monster.getNextMove(state);
        }

        monster.takeDamage(player, 5);
        monster.playTurn(state);
        Assert.assertEquals(1, state.getMonsters().size());
    }

    @Test
    public void fungoBeastTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        FungiBeastAI monster = new FungiBeastAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevStrength = monster.getPower(PowerTypeAI.STRENGTH);
            monster.playTurn(state);
            switch (move) {
                case FungiBeastAI.BITE:
                    Assert.assertEquals(player.getHealth() + 6 + prevStrength, prevHealth);
                    break;
                case FungiBeastAI.GROW:
                    Assert.assertEquals(monster.getPower(PowerTypeAI.STRENGTH) - 3, prevStrength);
                    break;
            }
            monster.getNextMove(state);
        }

        monster.takeDamage(player, 100);
        Assert.assertEquals(player.getPower(PowerTypeAI.VULNERABLE), 2);
    }

    @Test
    public void fatGremlinTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        FatGremlinAI monster = new FatGremlinAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevWeak = player.getPower(PowerTypeAI.WEAK);
            monster.playTurn(state);
            switch (move) {
                case FatGremlinAI.SMASH:
                    Assert.assertEquals(player.getHealth() + 4, prevHealth);
                    Assert.assertEquals(player.getPower(PowerTypeAI.WEAK) - 1, prevWeak);
                    break;
            }
            monster.getNextMove(state);
        }
    }

    @Test
    public void madGremlinTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        MadGremlinAI monster = new MadGremlinAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();
        Assert.assertEquals(monster.getPower(PowerTypeAI.ANGRY), 1);

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            monster.playTurn(state);
            switch (move) {
                case MadGremlinAI.SCRATCH:
                    Assert.assertEquals(player.getHealth() + 4, prevHealth);
                    break;
            }
            monster.getNextMove(state);
        }

        for (int i = 0; i < 20; i++) {
            int prevStrength = monster.getPower(PowerTypeAI.STRENGTH);
            monster.takeDamage(player, 1);
            Assert.assertEquals(monster.getPower(PowerTypeAI.STRENGTH) - 1, prevStrength);
        }
    }

    @Test
    public void shieldGremlinTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        MadGremlinAI madGremlin = new MadGremlinAI();
        state.getMonsters().add(madGremlin);
        ShieldGremlinAI shieldGremlin = new ShieldGremlinAI(state);
        state.getMonsters().add(shieldGremlin);
        PlayerAI player = state.getPlayer();

        for (int i = 0; i < 100; i++) {
            int prevBlock = madGremlin.getBlock();
            Assert.assertEquals((byte) shieldGremlin.getCurrentMove(), ShieldGremlinAI.PROTECT);
            shieldGremlin.playTurn(state);
            Assert.assertEquals(prevBlock + 7, madGremlin.getBlock());
        }

        madGremlin.takeDamage(player, 7000);
        shieldGremlin.playTurn(state);
        Assert.assertEquals(shieldGremlin.getBlock(), 7);

        for (int i = 0; i < 100; i++) {
            int prevHealth = player.getHealth();
            Assert.assertEquals((byte) shieldGremlin.getCurrentMove(), ShieldGremlinAI.SHIELD_BASH);
            shieldGremlin.playTurn(state);
            Assert.assertEquals(player.getHealth() + 6, prevHealth);
        }
    }

    @Test
    public void sneakyGremlinTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        SneakyGremlinAI monster = new SneakyGremlinAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            monster.playTurn(state);
            switch (move) {
                case SneakyGremlinAI.PUNCTURE:
                    Assert.assertEquals(player.getHealth() + 9, prevHealth);
                    break;
            }
            monster.getNextMove(state);
        }
    }

    @Test
    public void wizardGremlinTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        WizardGremlinAI monster = new WizardGremlinAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            monster.playTurn(state);
            switch (move) {
                case WizardGremlinAI.ULTIMATE_BLAST:
                    Assert.assertEquals(player.getHealth() + 25, prevHealth);
                    break;
                case WizardGremlinAI.CHARGING:
                    Assert.assertEquals(player.getHealth(), prevHealth);
                    break;
            }
            monster.getNextMove(state);
        }
    }

    @Test
    public void blueSlaverTest() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        BlueSlaverAI monster = new BlueSlaverAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevWeak = player.getPower(PowerTypeAI.WEAK);
            monster.playTurn(state);
            switch (move) {
                case BlueSlaverAI.STAB:
                    Assert.assertEquals(player.getHealth() + 12, prevHealth);
                    break;
                case BlueSlaverAI.RAKE:
                    Assert.assertEquals(player.getHealth() + 7, prevHealth);
                    Assert.assertEquals(player.getPower(PowerTypeAI.WEAK) - 1, prevWeak);
                    break;
            }
            monster.getNextMove(state);
        }
    }

    @Test
    public void redSlaver() {
        DungeonState state = IroncladUtil.getIronCladDungeonState();
        RedSlaverAI monster = new RedSlaverAI();
        state.getMonsters().add(monster);
        PlayerAI player = state.getPlayer();

        for (int i = 0; i < 100; i++) {
            byte move = monster.getCurrentMove();
            int prevHealth = player.getHealth();
            int prevVulnerable = player.getPower(PowerTypeAI.VULNERABLE);
            monster.playTurn(state);
            switch (move) {
                case RedSlaverAI.STAB:
                    if (prevVulnerable > 0) {
                        Assert.assertEquals(player.getHealth() + (int)(13 * 1.5), prevHealth);
                    } else {
                        Assert.assertEquals(player.getHealth() + 13, prevHealth);
                    }
                    break;
                case RedSlaverAI.ENTANGLE:
                    Assert.assertEquals(player.getPower(PowerTypeAI.ENTANGLE), 1);
                    break;
                case RedSlaverAI.SCRAPE:
                    if (prevVulnerable > 0) {
                        Assert.assertEquals(player.getHealth() + (int)(8 * 1.5), prevHealth);
                    } else {
                        Assert.assertEquals(player.getHealth() + 8, prevHealth);
                    }
                    Assert.assertEquals(player.getPower(PowerTypeAI.VULNERABLE) - 1, prevVulnerable);
                    break;
            }
            monster.getNextMove(state);
        }
    }

}
