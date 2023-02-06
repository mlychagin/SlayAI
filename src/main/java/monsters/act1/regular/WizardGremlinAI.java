package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import monsters.CreatureIdUtil.CreatureId;
import player.PlayerAI;
import powers.PowerAI;

import java.util.ArrayList;


public class WizardGremlinAI extends AbstractMonsterAI {
    public static final byte ULTIMATE_BLAST = 1;
    public static final byte CHARGING = 2;

    public WizardGremlinAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                           CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.WIZARD_GREMLIN;
    }

    public WizardGremlinAI() {
        super();
        creatureId = CreatureId.WIZARD_GREMLIN;
        health = 10 + rand.nextInt(5);
        maxHealth = health;
        block = 0;
        moveHistory.add(CHARGING);
    }

    @Override
    public void getNextMove(DungeonState state) {
        if (moveHistory.size() < 2) {
            moveHistory.add(CHARGING);
        } else if (moveHistory.size() == 2) {
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
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public WizardGremlinAI clone() {
        return new WizardGremlinAI(health, block, clonePowers(), new ArrayList<>(moveHistory),
                rand.copy(), monster);
    }
}