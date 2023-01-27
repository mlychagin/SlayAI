package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;

import java.util.ArrayList;

public class SpikeSlimeSmallAI extends AbstractMonsterAI {
    public static final byte TACKLE = 1;

    private SpikeSlimeSmallAI(SpikeSlimeSmallAI monster) {
        super(monster);
    }

    public SpikeSlimeSmallAI() {
        super();
        this.health = 10 + rand.nextInt(5);
        this.maxHealth = health;
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        getNextMove(null);
    }

    @Override
    public void getNextMove(DungeonState state) {
        moveHistory.add(TACKLE);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        if (getCurrentMove() == TACKLE) {
            player.takeDamage(this, 5);
        }
    }

    @Override
    public SpikeSlimeSmallAI clone() {
        return new SpikeSlimeSmallAI(this);
    }
}