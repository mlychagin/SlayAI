package monsters.act1.regular;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import dungeon.DungeonState;
import monsters.CreatureIdUtil.CreatureId;
import monsters.act1.interfaces.LouseAI;
import player.PlayerAI;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class RedLouseAI extends LouseAI {
    public static final byte BITE = 3;
    public static final byte GROW = 4;

    public RedLouseAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                      CopyableRandom rand, AbstractMonster monster, int bonusDamage) {
        super(health, block, powers, moveHistory, rand, monster, bonusDamage);
        creatureId = CreatureId.RED_LOUSE;
    }

    public RedLouseAI() {
        super();
        creatureId = CreatureId.RED_LOUSE;
        health = 10 + rand.nextInt(6);
        block = 0;
        bonusDamage = 5 + rand.nextInt(3);
        addPower(PowerTypeAI.CURL_UP, 1);
        getNextMove(null);
    }

    @Override
    public void getNextMove(DungeonState state) {
        int nextMove;
        while (true) {
            nextMove = rand.nextInt(100);
            if (nextMove <= 25) {
                // Can't use Grow three times in a row
                if (lastTwoMovesEqual(GROW)) {
                    continue;
                }
                moveHistory.add(GROW);
            } else {
                // Can't use Bite three times in a row
                if (lastTwoMovesEqual(BITE)) {
                    continue;
                }
                moveHistory.add(BITE);
            }
            break;
        }
    }

    @Override
    public void playMove(DungeonState state) {
        PlayerAI player = state.getPlayer();
        switch (getCurrentMove()) {
            case BITE:
                player.takeDamage(this, bonusDamage);
                break;
            case GROW:
                addPower(PowerTypeAI.STRENGTH, 3);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public RedLouseAI clone() {
        return new RedLouseAI(health, block, clonePowers(), new ArrayList<>(moveHistory),
                rand.copy(), monster, bonusDamage);
    }
}