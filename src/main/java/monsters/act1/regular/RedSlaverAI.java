package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class RedSlaverAI extends AbstractMonsterAI {
    public static final byte STAB = 1;
    public static final byte ENTANGLE = 2;
    public static final byte SCRAPE = 3;

    private RedSlaverAI(RedSlaverAI monster) {
        super(monster);
    }

    public RedSlaverAI() {
        super();
        this.health = 46 + rand.nextInt(5);
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        this.moveHistory.add(STAB);
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
        }
    }

    @Override
    public RedSlaverAI clone() {
        return new RedSlaverAI(this);
    }
}