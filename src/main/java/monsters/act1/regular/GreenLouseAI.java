package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.act1.interfaces.LouseAI;
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
    public void getNextMove(DungeonState state) {
        int nextMove;
        while (true) {
            nextMove = rand.nextInt(100);
            if (nextMove <= 25) {
                // Can't use Weak three times in a row
                if (lastTwoMovesEqual(WEAK)) {
                    continue;
                }
                moveHistory.add(WEAK);
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