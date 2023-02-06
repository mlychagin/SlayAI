package interactions.cards.ironclad;

import cards.CardIdUtil;
import cards.interfaces.AbstractCardAI;
import cards.ironclad.common.*;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import monsters.act1.regular.JawWormAI;
import org.junit.Assert;
import org.junit.Test;
import powers.PowerAI;

import static util.IroncladUtil.getJawWormState;

public class CommonInteractions {

    /*
     * TODO:
     *  Armaments - Choose card to upgrade
     *  Headbutt - Put card from your discard pile on top of draw pile
     *  Perfected Strike - Deals additional damage for each card in hand containing Strike
     *  True Grit - Upgraded version is exhaust a selected card
     *  Warcry - Place card from hand onto draw pile
     *  Havoc
     */

    @Test
    public void testAnger() {
        DungeonState state = getJawWormState();
        AbstractMonsterAI monster = state.getMonsters().get(0);
        AngerAI anger = new AngerAI(false);

        anger.playCard(state, monster);

        Assert.assertEquals(state.getDiscardPile().get(0), anger);
        Assert.assertEquals(monster.getHealth() + 6, 44);
    }

    @Test
    public void testBodySlam() {
        DungeonState state = getJawWormState();
        AbstractMonsterAI monster = state.getMonsters().get(0);
        BodySlamAI card = new BodySlamAI(false);

        state.getPlayer().addBlock(100);
        card.playCard(state, monster);

        Assert.assertEquals(monster.getHealth() + 100, 44);
    }

    @Test
    public void testClash() {
        DungeonState state = getJawWormState();
        AbstractMonsterAI monster = state.getMonsters().get(0);
        ClashAI card = new ClashAI(false);

        try {
            card.playCard(state, monster);
            Assert.fail();
        } catch (Exception ignored) {
        }

        state.getHand().clear();

        card.playCard(state, monster);
        Assert.assertEquals(monster.getHealth() + 14, 44);
    }

    @Test
    public void testTwinStrike() {
        DungeonState state = getJawWormState();
        state.getPlayer().addPower(PowerAI.PowerTypeAI.STRENGTH, 1);
        AbstractMonsterAI monster = state.getMonsters().get(0);
        TwinStrikeAI card = new TwinStrikeAI(false);

        card.playCard(state, monster);

        Assert.assertEquals(monster.getHealth() + 12, 44);
    }

    @Test
    public void testThunderClap() {
        DungeonState state = getJawWormState();
        state.getMonsters().add(new JawWormAI());
        ThunderclapAI card = new ThunderclapAI(false);

        card.playCard(state);

        for (AbstractMonsterAI monster : state.getMonsters()) {
            Assert.assertEquals(monster.getHealth() + 4, 44);
            Assert.assertEquals(monster.getPower(PowerAI.PowerTypeAI.VULNERABLE), 1);
        }
    }

    @Test
    public void testIronWave() {
        DungeonState state = getJawWormState();
        AbstractMonsterAI monster = state.getMonsters().get(0);
        IronWaveAI card = new IronWaveAI(false);

        card.playCard(state, monster);

        Assert.assertEquals(monster.getHealth() + 5, 44);
        Assert.assertEquals(state.getPlayer().getBlock(), 5);
    }

    @Test
    public void testClothesline() {
        DungeonState state = getJawWormState();
        AbstractMonsterAI monster = state.getMonsters().get(0);
        ClotheslineAI card = new ClotheslineAI(false);

        card.playCard(state, monster);

        Assert.assertEquals(monster.getHealth() + 12, 44);
        Assert.assertEquals(monster.getPower(PowerAI.PowerTypeAI.WEAK), 2);
    }

    @Test
    public void testCleave() {
        DungeonState state = getJawWormState();
        state.getMonsters().add(new JawWormAI());
        CleaveAI card = new CleaveAI(false);

        card.playCard(state);

        for (AbstractMonsterAI monster : state.getMonsters()) {
            Assert.assertEquals(monster.getHealth() + 8, 44);
        }
    }

    @Test
    public void testSwordBoomerang() {
        DungeonState state = getJawWormState();
        state.getMonsters().add(new JawWormAI());
        SwordBoomerangAI card = new SwordBoomerangAI(false);
        int totalDamage = 0;

        card.playCard(state);

        for (AbstractMonsterAI monster : state.getMonsters()) {
            totalDamage += 44 - monster.getHealth();
        }
        Assert.assertEquals(totalDamage, 9);
    }

    @Test
    public void testPommelStrike() {
        DungeonState state = getJawWormState();
        AbstractMonsterAI monster = state.getMonsters().get(0);
        PommelStrikeAI card = new PommelStrikeAI(false);

        card.playCard(state, monster);

        Assert.assertEquals(monster.getHealth() + 9, 44);
        Assert.assertEquals(state.getHand().size(), 6);
    }

    @Test
    public void testShrugItOff() {
        DungeonState state = getJawWormState();
        ShrugItOffAI card = new ShrugItOffAI(false);

        card.playCard(state);

        Assert.assertEquals(state.getPlayer().getBlock(), 8);
        Assert.assertEquals(state.getHand().size(), 6);
    }

    @Test
    public void testHeavyBlade() {
        DungeonState state = getJawWormState();
        state.getPlayer().addPower(PowerAI.PowerTypeAI.STRENGTH, 2);
        AbstractMonsterAI monster = state.getMonsters().get(0);
        HeavyBladeAI card = new HeavyBladeAI(false);

        card.playCard(state, monster);

        Assert.assertEquals(monster.getHealth() + 14 + (3 * 2), 44);
    }

    @Test
    public void testFlex() {
        DungeonState state = getJawWormState();
        FlexAI card = new FlexAI(false);

        card.playCard(state);

        Assert.assertEquals(state.getPlayer().getPower(PowerAI.PowerTypeAI.FLEX), 2);
    }

    @Test
    public void testWildStrike() {
        DungeonState state = getJawWormState();
        AbstractMonsterAI monster = state.getMonsters().get(0);
        WildStrikeAI card = new WildStrikeAI(false);
        boolean containsWound = false;

        card.playCard(state, monster);

        Assert.assertEquals(monster.getHealth() + 12, 44);

        for (AbstractCardAI cardIter : state.getDrawPile()) {
            if (cardIter.getCardId() == CardIdUtil.CardId.WOUND) {
                containsWound = true;
                break;
            }
        }

        Assert.assertTrue(containsWound);
    }
}
