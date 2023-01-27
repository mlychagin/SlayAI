package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractCreatureAI;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class FungiBeastAI extends AbstractMonsterAI {
    public static final byte BITE = 1;
    public static final byte GROW = 2;

    private FungiBeastAI(FungiBeastAI monster) {
        super(monster);
    }

    public FungiBeastAI() {
        super();
        this.health = 22 + rand.nextInt(7);
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        getNextMove(null);
    }

    @Override
    public void takeDamage(AbstractCreatureAI source, int value) {
        super.takeDamage(source, value);
        if (health <= 0) {
            source.addPower(PowerTypeAI.VULNERABLE, 2);
        }
    }

    @Override
    public void getNextMove(DungeonState state) {
        int nextMove;
        while (true) {
            nextMove = rand.nextInt(100);
            if (nextMove <= 60) {
                // Can't use Bite three times in a row
                if (lastTwoMovesEqual(BITE)) {
                    continue;
                }
                moveHistory.add(BITE);
            } else {
                // Can't use Grow two times in a row
                if (lastMoveEquals(GROW)) {
                    continue;
                }
                moveHistory.add(GROW);
            }
            break;
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case BITE:
                player.takeDamage(this, 6);
                break;
            case GROW:
                addPower(PowerTypeAI.STRENGTH, 3);
                break;
        }
    }

    @Override
    public FungiBeastAI clone() {
        return new FungiBeastAI(this);
    }
}