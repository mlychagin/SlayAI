package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.CreatureIdUtil.CreatureId;
import monsters.act1.interfaces.ThiefAI;
import player.PlayerAI;
import powers.PowerAI;

import java.util.ArrayList;

public class MuggerAI extends ThiefAI {

    public MuggerAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                    CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.MUGGER;
    }

    public MuggerAI() {
        super();
        creatureId = CreatureId.MUGGER;
        health = 48 + rand.nextInt(5);
        block = 0;
        getNextMove(null);
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case MUG:
                player.takeDamage(this, 10);
                break;
            case SMOKE_BOMB:
                addBlock(11);
                break;
            case ESCAPE:
                health = 0;
                break;
            case LUNGE:
                player.takeDamage(this, 16);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public MuggerAI clone() {
        return new MuggerAI(health, block, clonePowers(), new ArrayList<>(moveHistory),
                rand.copy(), monster);
    }
}