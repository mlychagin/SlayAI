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

public class BlueSlaverAI extends AbstractMonsterAI {
    public static final byte STAB = 1;
    public static final byte RAKE = 4;

    public BlueSlaverAI(int health, int block, ArrayList<PowerAI> powers,
                        ArrayList<Byte> moveHistory, CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.BLUE_SAVER;
    }

    public BlueSlaverAI() {
        super();
        creatureId = CreatureId.BLUE_SAVER;
        health = 46 + rand.nextInt(5);
        block = 0;
        getNextMove(null);
    }

    @Override
    public void getNextMove(DungeonState state) {
        int nextMove;
        while (true) {
            nextMove = rand.nextInt(100);
            if (nextMove <= 40) {
                // Can't use Rake three times in a row
                if (lastTwoMovesEqual(RAKE)) {
                    continue;
                }
                moveHistory.add(RAKE);
            } else {
                // Can't use Stab three times in a row
                if (lastTwoMovesEqual(STAB)) {
                    continue;
                }
                moveHistory.add(STAB);
            }
            break;
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case STAB:
                player.takeDamage(this, 12);
                break;
            case RAKE:
                player.takeDamage(this, 7);
                player.addPower(PowerTypeAI.WEAK, 1);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public BlueSlaverAI clone() {
        return new BlueSlaverAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster);
    }
}