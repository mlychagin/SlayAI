package algorithms;

import dungeon.DungeonState;
import monsters.act1.regular.*;
import org.junit.Test;
import util.IroncladUtil;

import java.util.ArrayList;

import static algorithms.DFSAlgorithmUtil.dungeonStateDFS;

public class DFSAlgorithmTest {

    @Test
    public void starterIroncladMoveSet() {
        int average = 0;
        int worst = 80;
        int best = 0;
        int loops = 5;
        for (int i = 0; i < loops; i++) {
            DungeonState state = IroncladUtil.getIronCladDungeonState();
            state.getMonsters().add(new WizardGremlinAI());
            state.getMonsters().add(new ShieldGremlinAI(state));
            state.getMonsters().add(new MadGremlinAI());
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
