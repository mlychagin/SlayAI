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

public class RedSlaverAI extends AbstractMonsterAI {
    public static final byte STAB = 1;
    public static final byte ENTANGLE = 2;
    public static final byte SCRAPE = 3;

    public RedSlaverAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                       CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.RED_SLAVER;
    }

    public RedSlaverAI() {
        super();
        creatureId = CreatureId.RED_SLAVER;
        health = 46 + rand.nextInt(5);
        block = 0;
        moveHistory.add(STAB);
    }

    @Override
    public void getNextMove(DungeonState state) {
        int nextMove;
        boolean entangleUsed = moveHistory.contains(ENTANGLE);
        while (true) {
            nextMove = rand.nextInt(100);
            if (entangleUsed) {
                if (nextMove <= 55) {
                    // Can't use Scrape three times in a row
                    if (lastTwoMovesEqual(SCRAPE)) {
                        continue;
                    }
                    moveHistory.add(SCRAPE);
                } else {
                    // Can't use Stab three times in a row
                    if (lastTwoMovesEqual(STAB)) {
                        continue;
                    }
                    moveHistory.add(STAB);
                }
            } else {
                if (lastMoveEquals(STAB)) {
                    if (nextMove <= 25) {
                        moveHistory.add(ENTANGLE);
                    } else {
                        moveHistory.add(SCRAPE);
                    }
                } else {
                    if (lastTwoMovesEqual(SCRAPE)) {
                        moveHistory.add(STAB);
                    } else {
                        moveHistory.add(SCRAPE);
                    }
                }
            }
            break;
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case STAB:
                player.takeDamage(this, 13);
                break;
            case ENTANGLE:
                player.addPower(PowerTypeAI.ENTANGLE, 1);
                break;
            case SCRAPE:
                player.takeDamage(this, 8);
                player.addPower(PowerTypeAI.VULNERABLE, 1);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public RedSlaverAI clone() {
        return new RedSlaverAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster);
    }
}