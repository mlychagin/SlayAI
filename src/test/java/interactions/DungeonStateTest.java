package interactions;

import dungeon.CopyableRandom;
import dungeon.DungeonState;
import org.junit.Assert;
import org.junit.Test;
import util.IroncladUtil;

public class DungeonStateTest {

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
