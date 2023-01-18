package monsters;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import player.PlayerAI;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public abstract class AbstractMonsterAI extends AbstractCreatureAI {
    protected ArrayList<Byte> moveHistory;

    public AbstractMonsterAI() {
    }

    public AbstractMonsterAI(AbstractMonster monster) {
        health = monster.currentHealth;
        block = monster.currentBlock;
        powers = new ArrayList<>();
        for (AbstractPower power : monster.powers) {
            if (power instanceof StrengthPower) {
                powers.add(new PowerAI(PowerTypeAI.STRENGTH, power.amount));
            }
        }
        moveHistory = new ArrayList<>(monster.moveHistory);
    }

    protected AbstractMonsterAI(AbstractMonsterAI monster) {
        super(monster);
        moveHistory = new ArrayList<>(monster.moveHistory);
    }

    public abstract void getNextMove();

    public Byte getCurrentMove() {
        if (moveHistory.size() == 0) {
            return -1;
        }
        return moveHistory.get(moveHistory.size() - 1);
    }

    public void playTurn(PlayerAI player) {
        playMove(player);
        endTurnPower();
        getNextMove();
    }

    public abstract void playMove(PlayerAI player);

    @Override
    public abstract AbstractMonsterAI clone();
}
