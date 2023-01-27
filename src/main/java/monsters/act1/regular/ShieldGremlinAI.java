package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;

import java.util.ArrayList;

public class ShieldGremlinAI extends AbstractMonsterAI {
    public static final byte PROTECT = 1;
    public static final byte SHIELD_BASH = 2;

    private ShieldGremlinAI(ShieldGremlinAI monster) {
        super(monster);
    }

    public ShieldGremlinAI(DungeonState state) {
        super();
        this.health = 12 + rand.nextInt(4);
        this.maxHealth = health;
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        getNextMove(state);
    }

    @Override
    public void getNextMove(DungeonState state) {
        moveHistory.add(otherEnemyFound(state) ? PROTECT : SHIELD_BASH);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case PROTECT:
                protectEnemy(state, 7);
                break;
            case SHIELD_BASH:
                player.takeDamage(this, 6);
                break;
        }
    }

    @Override
    public ShieldGremlinAI clone() {
        return new ShieldGremlinAI(this);
    }
}