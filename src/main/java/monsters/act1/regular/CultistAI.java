package monsters.act1.regular;

import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class CultistAI extends AbstractMonsterAI {
    public static final byte DARK_STRIKE = 1;
    public static final byte INCANTATION = 3;

    private CultistAI(CultistAI monster) {
        super(monster);
    }

    public CultistAI() {
        super();
        this.health = 48 + rand.nextInt(7);
        this.block = 0;
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        this.moveHistory.add(INCANTATION);
    }

    @Override
    public void getNextMove(DungeonState state) {
        moveHistory.add(DARK_STRIKE);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case DARK_STRIKE:
                player.takeDamage(this, 6);
                break;
            case INCANTATION:
                addPower(PowerTypeAI.PRE_RITUAL, 3);
                break;
        }
    }

    @Override
    public CultistAI clone() {
        return new CultistAI(this);
    }
}