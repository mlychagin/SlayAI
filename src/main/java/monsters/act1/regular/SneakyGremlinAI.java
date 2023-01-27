package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class SneakyGremlinAI extends AbstractMonsterAI {
    public static final byte PUNCTURE = 1;

    private SneakyGremlinAI(SneakyGremlinAI monster) {
        super(monster);
    }

    public SneakyGremlinAI() {
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
        moveHistory.add(PUNCTURE);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        if (getCurrentMove() == PUNCTURE) {
            player.takeDamage(this, 9);
        }
    }

    @Override
    public SneakyGremlinAI clone() {
        return new SneakyGremlinAI(this);
    }
}