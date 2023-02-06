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

public class SpikeSlimeMediumAI extends AbstractMonsterAI {
    public static final byte FLAME_TACKLE = 1;
    public static final byte LICK = 4;

    public SpikeSlimeMediumAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory
            , CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.SPIKE_SLIME_MEDIUM;
    }

    public SpikeSlimeMediumAI(int health) {
        super();
        creatureId = CreatureId.SPIKE_SLIME_MEDIUM;
        this.health = health;
        maxHealth = health;
        block = 0;
        getNextMove(null);
    }

    public SpikeSlimeMediumAI() {
        super();
        creatureId = CreatureId.SPIKE_SLIME_MEDIUM;
        health = 28 + rand.nextInt(5);
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
                // Can't use Flame Tackle three times in a row
                if (lastTwoMovesEqual(FLAME_TACKLE)) {
                    continue;
                }
                moveHistory.add(FLAME_TACKLE);
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
            case FLAME_TACKLE:
                player.takeDamage(this, 8);
                state.addCardToDiscardPile(new SlimedAI(false));
                break;
            case LICK:
                player.addPower(PowerTypeAI.FRAIL, 1);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public SpikeSlimeMediumAI clone() {
        return new SpikeSlimeMediumAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster);
    }
}