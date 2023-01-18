package algorithms;

import util.IroncladUtil;
import cards.interfaces.AbstractCardAI;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static algorithms.CardPriorityUtil.getMoveSet;

public class CardPriorityTest {

    @Test
    public void starterIroncladDeck () {
        ArrayList<AbstractCardAI> hand = IroncladUtil.getStarterIroncladDeck();

        Assert.assertEquals(getMoveSet(hand, 0).size(), 1);
        Assert.assertEquals(getMoveSet(hand, 1).size(), 2);
        Assert.assertEquals(getMoveSet(hand, 2).size(), 4);
        Assert.assertEquals(getMoveSet(hand, 3).size(), 6);
        Assert.assertEquals(getMoveSet(hand, 4).size(), 8);
        Assert.assertEquals(getMoveSet(hand, 5).size(), 10);
        Assert.assertEquals(getMoveSet(hand, 6).size(), 10);
        Assert.assertEquals(getMoveSet(hand, 7).size(), 10);
        Assert.assertEquals(getMoveSet(hand, 8).size(), 8);
        Assert.assertEquals(getMoveSet(hand, 9).size(), 6);
        Assert.assertEquals(getMoveSet(hand, 10).size(), 4);
    }
}
