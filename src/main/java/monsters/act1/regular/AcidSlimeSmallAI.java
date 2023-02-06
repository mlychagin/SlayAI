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

public class AcidSlimeSmallAI extends AbstractMonsterAI {
    public static final byte TACKLE = 1;
    public static final byte LICK = 2;

    public AcidSlimeSmallAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                            CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.ACID_SLIME_SMALL;
    }

    public AcidSlimeSmallAI() {
        super();
        creatureId = CreatureId.ACID_SLIME_SMALL;
        health = 8 + rand.nextInt(5);
        maxHealth = health;
        block = 0;
        moveHistory.add(rand.nextBoolean() ? LICK : TACKLE);
    }

    @Override
    public void getNextMove(DungeonState state) {
        if (lastMoveEquals(TACKLE)) {
            moveHistory.add(LICK);
        } else {
            moveHistory.add(TACKLE);
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case LICK:
                player.addPower(PowerTypeAI.WEAK, 1);
                break;
            case TACKLE:
                player.takeDamage(this, 3);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public AcidSlimeSmallAI clone() {
        return new AcidSlimeSmallAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster);
    }
}