package monsters.act1;

import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

public class GreenLouseAI extends LouseAI {
    public static final byte BITE = 3;
    public static final byte WEAK = 4;

    private GreenLouseAI(GreenLouseAI monster) {
        super(monster);
    }

    public GreenLouseAI() {
        super();
    }

    @Override
    public void getNextMove() {
        int nextMove;
        while(true) {
            nextMove = r.nextInt(100);
            if (nextMove <= 25) {
                // Can't use Weak three times in a row
                if (getCurrentMove() == WEAK &&
                        moveHistory.size() > 1 &&
                        moveHistory.get(moveHistory.size() - 2) == WEAK) {
                    continue;
                }
                moveHistory.add(WEAK);
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
            case WEAK:
                player.addPower(PowerTypeAI.WEAK, 2);
                break;
        }
    }

    @Override
    public GreenLouseAI clone() {
        return new GreenLouseAI(this);
    }
}