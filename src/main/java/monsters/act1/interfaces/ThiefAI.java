package monsters.act1.interfaces;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import powers.PowerAI;

import java.util.ArrayList;

public abstract class ThiefAI extends AbstractMonsterAI {
    public static final byte MUG = 1;
    public static final byte SMOKE_BOMB = 2;
    public static final byte ESCAPE = 3;
    public static final byte LUNGE = 4;

    public ThiefAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                   CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
    }

    public ThiefAI() {
        super();
    }

    @Override
    public void getNextMove(DungeonState state) {
        if (moveHistory.size() < 2) {
            moveHistory.add(MUG);
            return;
        }
        if (moveHistory.size() == 3 && moveHistory.get(2) == LUNGE) {
            moveHistory.add(SMOKE_BOMB);
            return;
        }
        if (lastMoveEquals(SMOKE_BOMB)) {
            moveHistory.add(ESCAPE);
            return;
        }
        moveHistory.add(rand.nextBoolean() ? LUNGE : SMOKE_BOMB);
    }

}