package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.act1.interfaces.LouseAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

public class RedLouseAI extends LouseAI {
    public static final byte BITE = 3;
    public static final byte GROW = 4;

    private RedLouseAI(RedLouseAI monster) {
        super(monster);
    }

    public RedLouseAI() {
        super();
    }

    @Override
    public void getNextMove(DungeonState state) {
        int nextMove;
        while (true) {
            nextMove = rand.nextInt(100);
            if (nextMove <= 25) {
                // Can't use Grow three times in a row
                if (lastTwoMovesEqual(GROW)) {
                    continue;
                }
                moveHistory.add(GROW);
            } else {
                // Can't use Bite three times in a row
                if (lastTwoMovesEqual(BITE)) {
                    continue;
                }
                moveHistory.add(BITE);
            }
            break;
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case BITE:
                player.takeDamage(this, bonusDamage);
                break;
            case GROW:
                addPower(PowerTypeAI.STRENGTH, 3);
                break;
        }
    }

    @Override
    public RedLouseAI clone() {
        return new RedLouseAI(this);
    }
}