package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class MadGremlinAI extends AbstractMonsterAI {
    public static final byte SCRATCH = 2;

    private MadGremlinAI(MadGremlinAI monster) {
        super(monster);
    }

    public MadGremlinAI() {
        super();
        this.health = 20 + rand.nextInt(5);
        this.maxHealth = health;
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        addPower(PowerTypeAI.ANGRY, 1);
        getNextMove(null);
    }

    @Override
    public void getNextMove(DungeonState state) {
        moveHistory.add(SCRATCH);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        if (getCurrentMove() == SCRATCH) {
            player.takeDamage(this, 4);
        }
    }

    @Override
    public MadGremlinAI clone() {
        return new MadGremlinAI(this);
    }
}