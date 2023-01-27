package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class BlueSlaverAI extends AbstractMonsterAI {
    public static final byte STAB = 1;
    public static final byte RAKE = 4;

    private BlueSlaverAI(BlueSlaverAI monster) {
        super(monster);
    }

    public BlueSlaverAI() {
        super();
        this.health = 46 + rand.nextInt(5);
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
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
        }
    }

    @Override
    public BlueSlaverAI clone() {
        return new BlueSlaverAI(this);
    }
}