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

public class JawWormAI extends AbstractMonsterAI {
    public static final byte CHOMP = 1;
    public static final byte BELLOW = 2;
    public static final byte THRASH = 3;

    public JawWormAI(int health, int block, ArrayList<PowerAI> powers,
                     ArrayList<Byte> moveHistory, CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.JAWWORM;
    }

    public JawWormAI() {
        super();
        creatureId = CreatureId.JAWWORM;
        health = 44;
        block = 0;
        moveHistory.add(CHOMP);
    }

    @Override
    public void getNextMove(DungeonState state) {
        int nextMove;
        while (true) {
            nextMove = rand.nextInt(100);
            if (nextMove <= 45) {
                // Can't use Bellow twice in a row
                if (lastMoveEquals(BELLOW)) {
                    continue;
                }
                moveHistory.add(BELLOW);
                break;
            } else if (nextMove <= 75) {
                // Can't use Thrash three times in a row
                if (lastTwoMovesEqual(THRASH)) {
                    continue;
                }
                moveHistory.add(THRASH);
                break;
            } else {
                // Can't use Chomp twice in a row
                if (lastMoveEquals(CHOMP)) {
                    continue;
                }
                moveHistory.add(CHOMP);
                break;
            }
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case CHOMP:
                player.takeDamage(this, 11);
                break;
            case BELLOW:
                addPower(PowerTypeAI.STRENGTH, 3);
                addBlock(6);
                break;
            case THRASH:
                player.takeDamage(this, 7);
                addBlock(5);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public JawWormAI clone() {
        return new JawWormAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster);
    }
}