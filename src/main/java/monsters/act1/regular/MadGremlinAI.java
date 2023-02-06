package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import monsters.CreatureIdUtil.CreatureId;
import player.PlayerAI;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class MadGremlinAI extends AbstractMonsterAI {
    public static final byte SCRATCH = 2;

    public MadGremlinAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                        CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.MAD_GREMLIN;
    }

    public MadGremlinAI() {
        super();
        creatureId = CreatureId.MAD_GREMLIN;
        health = 20 + rand.nextInt(5);
        maxHealth = health;
        block = 0;
        addPower(PowerTypeAI.ANGRY, 1);
        getNextMove(null);
    }

    @Override
    public void getNextMove(DungeonState state) {
        moveHistory.add(SCRATCH);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        if (getCurrentMove() == SCRATCH) {
            player.takeDamage(this, 4);
            return;
        }
        throw new RuntimeException("Invalid move : " + getCurrentMove());

    }

    @Override
    public MadGremlinAI clone() {
        return new MadGremlinAI(health, block, clonePowers(), new ArrayList<>(moveHistory),
                rand.copy(), monster);
    }
}