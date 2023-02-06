package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.CreatureIdUtil.CreatureId;
import monsters.act1.interfaces.ThiefAI;
import player.PlayerAI;
import powers.PowerAI;

import java.util.ArrayList;

public class LooterAI extends ThiefAI {

    public LooterAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                    CopyableRandom rand, AbstractMonster monster) {
        super(health, block, powers, moveHistory, rand, monster);
        creatureId = CreatureId.LOOTER;
    }

    public LooterAI() {
        super();
        creatureId = CreatureId.LOOTER;
        health = 44 + rand.nextInt(5);
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
                addBlock(6);
                break;
            case ESCAPE:
                health = 0;
                break;
            case LUNGE:
                player.takeDamage(this, 12);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public LooterAI clone() {
        return new LooterAI(health, block, clonePowers(), new ArrayList<>(moveHistory),
                rand.copy(), monster);
    }
}