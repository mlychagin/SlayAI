package interactions;

import cards.interfaces.AbstractCardAI;
import cards.ironclad.starter.BashAI;
import cards.ironclad.starter.StrikeAI;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import org.junit.Assert;
import org.junit.Test;
import util.IroncladUtil;
import util.MonsterUtil;

import java.util.ArrayList;

public class DungeonStateTest {

    @Test
    public void starterIroncladMove() {
        DungeonState state = IroncladUtil.getJawWormState();
        ArrayList<AbstractCardAI> move = new ArrayList<>();
        move.add(new BashAI());
        move.add(new StrikeAI());

        // One JawWorm
        Assert.assertEquals(state.getPossibleStates(move).size(), 1);

        // Two JawWorm
        state.getMonsters().add(MonsterUtil.getJawWorm());
        Assert.assertEquals(state.getPossibleStates(move).size(), 4);

        // Three JawWorm
        state.getMonsters().add(MonsterUtil.getJawWorm());
        Assert.assertEquals(state.getPossibleStates(move).size(), 9);
    }

    @Test
    public void starterIroncladMoveSet() {
        DungeonState state = IroncladUtil.getJawWormState();

        // One JawWorm
        Assert.assertEquals(state.getPossibleStates().size(), 3);

        // Two JawWorm
        state.getMonsters().add(MonsterUtil.getJawWorm());
        Assert.assertEquals(state.getPossibleStates().size(), 7);

        // Three JawWorm
        state.getMonsters().add(MonsterUtil.getJawWorm());
        Assert.assertEquals(state.getPossibleStates().size(), 13);
    }

    @Test
    public void basicEndTurn() {
        DungeonState state = IroncladUtil.getJawWormState();

        state.endTurn();
        Assert.assertEquals(state.getPlayer().getHealth(), 80 - 11);
        Assert.assertEquals(state.getHand().size(), 5);
    }

    @Test
    public void copyAbleRandomTest() {
        CopyableRandom r1 = new CopyableRandom(100);
        CopyableRandom r2 = new CopyableRandom(100);

        Assert.assertEquals(r1.nextInt(10), r2.nextInt(10));

        CopyableRandom r3 = r1.copy();

        Assert.assertEquals(r1.nextInt(10), r2.nextInt(10), r3.nextInt(10));
    }
}
