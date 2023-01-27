package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.exordium.JawWorm;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class JawWormAI extends AbstractMonsterAI {
    public static final byte CHOMP = 1;
    public static final byte BELLOW = 2;
    public static final byte THRASH = 3;

    public JawWormAI(JawWorm monster) {
        super(monster);
    }

    private JawWormAI(JawWormAI monster) {
        super(monster);
    }

    public JawWormAI() {
        super();
        this.health = 44;
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        this.moveHistory.add(CHOMP);
    }

    @Override
    public void getNextMove(DungeonState state) {
        int nextMove;
        while (true) {
            nextMove = rand.nextInt(100);
            if (nextMove <= 45) {
                // Can't use Bellow twice in a row
                if (lastMoveEquals(BELLOW)) {
                    continue;
                }
                moveHistory.add(BELLOW);
                break;
            } else if (nextMove <= 75) {
                // Can't use Thrash three times in a row
                if (lastTwoMovesEqual(THRASH)) {
                    continue;
                }
                moveHistory.add(THRASH);
                break;
            } else {
                // Can't use Chomp twice in a row
                if (lastMoveEquals(CHOMP)) {
                    continue;
                }
                moveHistory.add(CHOMP);
                break;
            }
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case CHOMP:
                player.takeDamage(this, 11);
                break;
            case BELLOW:
                addPower(PowerTypeAI.STRENGTH, 3);
                addBlock(6);
                break;
            case THRASH:
                player.takeDamage(this, 7);
                addBlock(5);
                break;
        }
    }

    @Override
    public JawWormAI clone() {
        return new JawWormAI(this);
    }
}