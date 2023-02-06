package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import monsters.CreatureIdUtil.CreatureId;
import player.PlayerAI;
import powers.PowerAI;

import java.util.ArrayList;

public class SpikeSlimeSmallAI extends AbstractMonsterAI {
    public static final byte TACKLE = 1;

    public SpikeSlimeSmallAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                             CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.SPIKE_SLIME_SMALL;
    }

    public SpikeSlimeSmallAI() {
        super();
        creatureId = CreatureId.SPIKE_SLIME_SMALL;
        health = 10 + rand.nextInt(5);
        maxHealth = health;
        block = 0;
        getNextMove(null);
    }

    @Override
    public void getNextMove(DungeonState state) {
        moveHistory.add(TACKLE);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        if (getCurrentMove() == TACKLE) {
            player.takeDamage(this, 5);
            return;
        }
        throw new RuntimeException("Invalid move : " + getCurrentMove());
    }

    @Override
    public SpikeSlimeSmallAI clone() {
        return new SpikeSlimeSmallAI(health, block, clonePowers(), new ArrayList<>(moveHistory),
                rand.copy(), monster);
    }
}