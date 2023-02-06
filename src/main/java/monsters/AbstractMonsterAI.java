package monsters;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import powers.PowerAI;

import java.util.ArrayList;

public abstract class AbstractMonsterAI extends AbstractCreatureAI {
    protected transient AbstractMonster monster;
    protected ArrayList<Byte> moveHistory;

    public AbstractMonsterAI(int health, int block, ArrayList<PowerAI> powers,
                             ArrayList<Byte> moveHistory, CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, rand);
        this.monster = monster;
        this.moveHistory = moveHistory;
    }

    public AbstractMonsterAI() {
        moveHistory = new ArrayList<>();
        rand = new CopyableRandom();
    }

    protected boolean lastMoveEquals(byte move) {
        if (moveHistory.size() == 0) {
            return false;
        }
        return moveHistory.get(moveHistory.size() - 1) == move;
    }

    protected boolean lastTwoMovesEqual(byte move) {
        if (moveHistory.size() < 2) {
            return false;
        }
        return moveHistory.get(moveHistory.size() - 1) == move && moveHistory.get(moveHistory.size() - 2) == move;
    }

    protected boolean lastThreeMovesEqual(byte move) {
        if (moveHistory.size() < 3) {
            return false;
        }
        return moveHistory.get(moveHistory.size() - 1) == move && moveHistory.get(moveHistory.size() - 2) == move && moveHistory.get(moveHistory.size() - 3) == move;
    }

    public abstract void getNextMove(DungeonState state);

    public Byte getCurrentMove() {
        if (moveHistory.size() == 0) {
            return -1;
        }
        return moveHistory.get(moveHistory.size() - 1);
    }

    public void playTurn(DungeonState state) {
        playMove(state);
        endTurnPower();
        getNextMove(state);
    }

    public abstract void playMove(DungeonState state);

    protected boolean otherEnemyFound(DungeonState state) {
        for (AbstractMonsterAI monster : state.getMonsters()) {
            if (!monster.isDead() && monster != this) {
                return true;
            }
        }
        return false;
    }

    protected void protectEnemy(DungeonState state, int block) {
        ArrayList<AbstractMonsterAI> monsters = state.getMonsters();
        int index;
        if (otherEnemyFound(state)) {
            while (true) {
                index = rand.nextInt(monsters.size());
                if (monsters.get(index) != this) {
                    monsters.get(index).addBlock(block);
                    break;
                }
            }
        } else {
            addBlock(block);
        }
    }

    public AbstractMonster getMonster() {
        return monster;
    }

    @Override
    public abstract AbstractMonsterAI clone();
}
