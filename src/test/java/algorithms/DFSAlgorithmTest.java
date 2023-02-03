package algorithms;

import cards.interfaces.AbstractCardAI;
import cards.ironclad.starter.BashAI;
import cards.ironclad.starter.StrikeAI;
import dungeon.DungeonState;
import monsters.act1.regular.JawWormAI;
import org.junit.Assert;
import org.junit.Test;
import util.IroncladUtil;
import util.MonsterUtil;

import java.util.ArrayList;

import static algorithms.DFSAlgorithmUtil.*;

public class DFSAlgorithmTest {

    @Test
    public void starterIroncladMove() {
        DungeonState state = IroncladUtil.getJawWormState();
        ArrayList<AbstractCardAI> move = new ArrayList<>();
        move.add(new BashAI());
        move.add(new StrikeAI());

        // One JawWorm
        Assert.assertEquals(getPossibleStatesForMove(state, move).size(), 1);

        // Two JawWorm
        state.getMonsters().add(MonsterUtil.getJawWorm());
        Assert.assertEquals(getPossibleStatesForMove(state, move).size(), 4);

        // Three JawWorm
        state.getMonsters().add(MonsterUtil.getJawWorm());
        Assert.assertEquals(getPossibleStatesForMove(state, move).size(), 9);
    }

    @Test
    public void starterIroncladMoveSet() {
        DungeonState state = IroncladUtil.getJawWormState();

        // One JawWorm
        Assert.assertEquals(getPossibleStates(state).size(), 3);

        // Two JawWorm
        state.getMonsters().add(MonsterUtil.getJawWorm());
        Assert.assertEquals(getPossibleStates(state).size(), 7);

        // Three JawWorm
        state.getMonsters().add(MonsterUtil.getJawWorm());
        Assert.assertEquals(getPossibleStates(state).size(), 13);
    }

    @Test
    public void starterIroncladMoveSetTMP() {
        int average = 0;
        int worst = 80;
        int best = 0;
        int loops = 1000;
        for (int i = 0; i < loops; i++) {
            DungeonState state = IroncladUtil.getIronCladDungeonState();
            state.getMonsters().add(new JawWormAI());
            ArrayList<DungeonState> bestPath = dungeonStateDFS(state);
            int health = bestPath.get(bestPath.size() - 1).getPlayer().getHealth();
            average += health;
            if (health < worst) {
                worst = health;
            }
            if (health > best) {
                best = health;
            }
        }
        System.out.println("STATES CONSIDERED : " + DungeonState.STATES_CONSIDERED);
        System.out.println("AVERAGE : " + (average / loops));
        System.out.println("WORST : " + worst);
        System.out.println("BEST : " + best);
    }

}
