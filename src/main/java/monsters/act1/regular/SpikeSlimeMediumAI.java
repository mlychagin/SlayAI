package monsters.act1.regular;

import cards.neutral.SlimedAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class SpikeSlimeMediumAI extends AbstractMonsterAI {
    public static final byte FLAME_TACKLE = 1;
    public static final byte LICK = 4;

    private SpikeSlimeMediumAI(SpikeSlimeMediumAI monster) {
        super(monster);
    }

    public SpikeSlimeMediumAI(int health) {
        super();
        this.health = health;
        this.maxHealth = health;
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        getNextMove(null);
    }

    public SpikeSlimeMediumAI() {
        super();
        this.health = 28 + rand.nextInt(5);
        this.maxHealth = health;
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
                state.addCardToDiscardPile(new SlimedAI());
                break;
            case LICK:
                player.addPower(PowerTypeAI.FRAIL, 1);
                break;
        }
    }

    @Override
    public SpikeSlimeMediumAI clone() {
        return new SpikeSlimeMediumAI(this);
    }
}