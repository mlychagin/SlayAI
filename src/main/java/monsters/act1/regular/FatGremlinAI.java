package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class FatGremlinAI extends AbstractMonsterAI {
    public static final byte SMASH = 2;

    private FatGremlinAI(FatGremlinAI monster) {
        super(monster);
    }

    public FatGremlinAI() {
        super();
        this.health = 13 + rand.nextInt(5);
        this.maxHealth = health;
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        getNextMove(null);
    }

    @Override
    public void getNextMove(DungeonState state) {
        moveHistory.add(SMASH);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        if (getCurrentMove() == SMASH) {
            player.takeDamage(this, 4);
            player.addPower(PowerTypeAI.WEAK, 1);
        }
    }

    @Override
    public FatGremlinAI clone() {
        return new FatGremlinAI(this);
    }
}