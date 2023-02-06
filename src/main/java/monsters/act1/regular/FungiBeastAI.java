package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.AbstractCreatureAI;
import monsters.AbstractMonsterAI;
import monsters.CreatureIdUtil.CreatureId;
import player.PlayerAI;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class FungiBeastAI extends AbstractMonsterAI {
    public static final byte BITE = 1;
    public static final byte GROW = 2;

    public FungiBeastAI(int health, int block, ArrayList<PowerAI> powers,
                        ArrayList<Byte> moveHistory, CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.FUNGI_BEAST;
    }

    public FungiBeastAI() {
        super();
        creatureId = CreatureId.FUNGI_BEAST;
        health = 22 + rand.nextInt(7);
        block = 0;
        getNextMove(null);
    }

    @Override
    public void takeDamage(AbstractCreatureAI source, int value) {
        super.takeDamage(source, value);
        if (health <= 0) {
            source.addPower(PowerTypeAI.VULNERABLE, 2);
        }
    }

    @Override
    public void getNextMove(DungeonState state) {
        int nextMove;
        while (true) {
            nextMove = rand.nextInt(100);
            if (nextMove <= 60) {
                // Can't use Bite three times in a row
                if (lastTwoMovesEqual(BITE)) {
                    continue;
                }
                moveHistory.add(BITE);
            } else {
                // Can't use Grow two times in a row
                if (lastMoveEquals(GROW)) {
                    continue;
                }
                moveHistory.add(GROW);
            }
            break;
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case BITE:
                player.takeDamage(this, 6);
                break;
            case GROW:
                addPower(PowerTypeAI.STRENGTH, 3);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public FungiBeastAI clone() {
        return new FungiBeastAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster);
    }
}