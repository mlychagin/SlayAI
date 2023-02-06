package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import monsters.CreatureIdUtil.CreatureId;
import player.PlayerAI;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class CultistAI extends AbstractMonsterAI {
    public static final byte DARK_STRIKE = 1;
    public static final byte INCANTATION = 3;

    public CultistAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                     CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.CULTIST;
    }

    public CultistAI() {
        super();
        creatureId = CreatureId.CULTIST;
        health = 48 + rand.nextInt(7);
        block = 0;
        moveHistory.add(INCANTATION);
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
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public CultistAI clone() {
        return new CultistAI(health, block, clonePowers(), new ArrayList<>(moveHistory), rand.copy(),
                monster);
    }
}