package monsters.act1;

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
    public void getNextMove() {
        int nextMove;
        while(true) {
            nextMove = r.nextInt(100);
            if (nextMove <= 25) {
                // Can't use Grow three times in a row
                if (getCurrentMove() == GROW &&
                        moveHistory.size() > 1 &&
                        moveHistory.get(moveHistory.size() - 2) == GROW) {
                    continue;
                }
                moveHistory.add(GROW);
            } else {
                // Can't use Bite three times in a row
                if (getCurrentMove() == BITE &&
                        moveHistory.size() > 1 &&
                        moveHistory.get(moveHistory.size() - 2) == BITE) {
                    continue;
                }
                moveHistory.add(BITE);
            }
            break;
        }
    }

    @Override
    public void playMove(PlayerAI player) {
        switch (getCurrentMove()) {
            case BITE:
                player.takeDamage(player, bonusDamage);
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