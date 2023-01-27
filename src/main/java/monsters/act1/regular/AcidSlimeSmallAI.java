package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class AcidSlimeSmallAI extends AbstractMonsterAI {
    public static final byte TACKLE = 1;
    public static final byte LICK = 2;

    private AcidSlimeSmallAI(AcidSlimeSmallAI monster) {
        super(monster);
    }

    public AcidSlimeSmallAI() {
        super();
        this.health = 8 + rand.nextInt(5);
        this.maxHealth = health;
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        if (rand.nextBoolean()) {
            moveHistory.add(LICK);
        } else {
            moveHistory.add(TACKLE);
        }
    }

    @Override
    public void getNextMove(DungeonState state) {
        if (lastMoveEquals(TACKLE)) {
            moveHistory.add(LICK);
        } else {
            moveHistory.add(TACKLE);
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case LICK:
                player.addPower(PowerTypeAI.WEAK, 1);
                break;
            case TACKLE:
                player.takeDamage(this, 3);
                break;
        }
    }

    @Override
    public AcidSlimeSmallAI clone() {
        return new AcidSlimeSmallAI(this);
    }
}