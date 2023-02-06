package monsters.act1.regular;

import cards.neutral.SlimedAI;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import monsters.CreatureIdUtil.CreatureId;
import player.PlayerAI;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class AcidSlimeMediumAI extends AbstractMonsterAI {
    public static final byte CORROSIVE_SPIT = 1;
    public static final byte TACKLE = 2;
    public static final byte LICK = 4;

    public AcidSlimeMediumAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                             CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.ACID_SLIME_MEDIUM;
    }

    public AcidSlimeMediumAI(int health) {
        super();
        creatureId = CreatureId.ACID_SLIME_MEDIUM;
        this.health = health;
        maxHealth = health;
        block = 0;
        getNextMove(null);
    }

    public AcidSlimeMediumAI() {
        super();
        creatureId = CreatureId.ACID_SLIME_MEDIUM;
        health = 65 + rand.nextInt(5);
        maxHealth = health;
        block = 0;
        getNextMove(null);
    }

    @Override
    public void getNextMove(DungeonState state) {
        int nextMove;
        while (true) {
            nextMove = rand.nextInt(100);
            if (nextMove <= 30) {
                // Can't use Corrosive Spit twice in a row
                if (lastMoveEquals(CORROSIVE_SPIT)) {
                    continue;
                }
                moveHistory.add(CORROSIVE_SPIT);
            } else if (nextMove <= 70) {
                // Can't use Tackle three times in a row
                if (lastTwoMovesEqual(TACKLE)) {
                    continue;
                }
                moveHistory.add(TACKLE);
            } else {
                // Can't use Lick three times in a row
                if (lastTwoMovesEqual(LICK)) {
                    continue;
                }
                moveHistory.add(LICK);
            }
            break;
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case CORROSIVE_SPIT:
                player.takeDamage(this, 7);
                state.addCardToDiscardPile(new SlimedAI(false));
                break;
            case LICK:
                player.addPower(PowerTypeAI.WEAK, 1);
                break;
            case TACKLE:
                player.takeDamage(this, 10);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public AcidSlimeMediumAI clone() {
        return new AcidSlimeMediumAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster);
    }
}