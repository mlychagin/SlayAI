package monsters.act1;

import com.megacrit.cardcrawl.monsters.exordium.JawWorm;
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
    public void getNextMove() {
        int nextMove;
        while(true) {
            nextMove = r.nextInt(100);
            if (nextMove <= 45) {
                // Can't use Bellow twice in a row
                if (getCurrentMove() == BELLOW) {
                    continue;
                }
                moveHistory.add(BELLOW);
                break;
            } else if (nextMove <= 75) {
                // Can't use Thrash three times in a row
                if (getCurrentMove() == THRASH &&
                        moveHistory.size() > 1 &&
                        moveHistory.get(moveHistory.size() - 2) == THRASH) {
                    continue;
                }
                moveHistory.add(THRASH);
                break;
            } else {
                // Can't use Chomp twice in a row
                if (getCurrentMove() == CHOMP) {
                    continue;
                }
                moveHistory.add(CHOMP);
                break;
            }
        }
    }

    @Override
    public void playMove(PlayerAI player) {
        switch (getCurrentMove()) {
            case CHOMP:
                player.takeDamage(player, 11);
                break;
            case BELLOW:
                addPower(PowerTypeAI.STRENGTH, 3);
                addBlock(6);
                break;
            case THRASH:
                player.takeDamage(player, 7);
                addBlock(5);
                break;
        }
    }

    @Override
    public JawWormAI clone() {
        return new JawWormAI(this);
    }
}