package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import monsters.CreatureIdUtil.CreatureId;
import player.PlayerAI;
import powers.PowerAI;

import java.util.ArrayList;


public class ShieldGremlinAI extends AbstractMonsterAI {
    public static final byte PROTECT = 1;
    public static final byte SHIELD_BASH = 2;

    public ShieldGremlinAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                           CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.SHIELD_GREMLIN;
    }

    public ShieldGremlinAI(DungeonState state) {
        super();
        creatureId = CreatureId.SHIELD_GREMLIN;
        health = 12 + rand.nextInt(4);
        maxHealth = health;
        block = 0;
        getNextMove(state);
    }

    @Override
    public void getNextMove(DungeonState state) {
        moveHistory.add(otherEnemyFound(state) ? PROTECT : SHIELD_BASH);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case PROTECT:
                protectEnemy(state, 7);
                break;
            case SHIELD_BASH:
                player.takeDamage(this, 6);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public ShieldGremlinAI clone() {
        return new ShieldGremlinAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster);
    }
}