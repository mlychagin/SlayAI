package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;

import java.util.ArrayList;

public class WizardGremlinAI extends AbstractMonsterAI {
    public static final byte ULTIMATE_BLAST = 1;
    public static final byte CHARGING = 2;

    private WizardGremlinAI(WizardGremlinAI monster) {
        super(monster);
    }

    public WizardGremlinAI() {
        super();
        this.health = 10 + rand.nextInt(5);
        this.maxHealth = health;
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        this.moveHistory.add(CHARGING);
    }

    @Override
    public void getNextMove(DungeonState state) {
        if (moveHistory.size() < 2) {
            moveHistory.add(CHARGING);
        } else if (moveHistory.size() == 2){
            moveHistory.add(ULTIMATE_BLAST);
        } else {
            if (lastThreeMovesEqual(CHARGING)) {
                moveHistory.add(ULTIMATE_BLAST);
            } else {
                moveHistory.add(CHARGING);
            }
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case ULTIMATE_BLAST:
                player.takeDamage(this, 25);
                break;
            case CHARGING:
                break;
        }
    }

    @Override
    public WizardGremlinAI clone() {
        return new WizardGremlinAI(this);
    }
}