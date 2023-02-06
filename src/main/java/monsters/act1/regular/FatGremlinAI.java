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

public class FatGremlinAI extends AbstractMonsterAI {
    public static final byte SMASH = 2;

    public FatGremlinAI(int health, int block, ArrayList<PowerAI> powers,
                        ArrayList<Byte> moveHistory, CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.FAT_GREMLIN;
    }

    public FatGremlinAI() {
        super();
        creatureId = CreatureId.FAT_GREMLIN;
        health = 13 + rand.nextInt(5);
        maxHealth = health;
        block = 0;
        getNextMove(null);
    }

    @Override
    public void getNextMove(DungeonState state) {
        moveHistory.add(SMASH);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        if (getCurrentMove() == SMASH) {
            player.takeDamage(this, 4);
            player.addPower(PowerTypeAI.WEAK, 1);
            return;
        }
        throw new RuntimeException("Invalid move : " + getCurrentMove());
    }

    @Override
    public FatGremlinAI clone() {
        return new FatGremlinAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster);
    }
}