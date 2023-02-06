package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import monsters.CreatureIdUtil.CreatureId;
import player.PlayerAI;
import powers.PowerAI;

import java.util.ArrayList;

public class SneakyGremlinAI extends AbstractMonsterAI {
    public static final byte PUNCTURE = 1;

    public SneakyGremlinAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                           CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.SNEAKY_GREMLIN;
    }

    public SneakyGremlinAI() {
        super();
        creatureId = CreatureId.SNEAKY_GREMLIN;
        health = 10 + rand.nextInt(5);
        maxHealth = health;
        block = 0;
        getNextMove(null);
    }

    @Override
    public void getNextMove(DungeonState state) {
        moveHistory.add(PUNCTURE);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        if (getCurrentMove() == PUNCTURE) {
            player.takeDamage(this, 9);
            return;
        }
        throw new RuntimeException("Invalid move : " + getCurrentMove());
    }

    @Override
    public SneakyGremlinAI clone() {
        return new SneakyGremlinAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster);
    }
}