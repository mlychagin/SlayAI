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

public class GreenLouseAI extends LouseAI {
    public static final byte BITE = 3;
    public static final byte WEAK = 4;

    public GreenLouseAI(int health, int block, ArrayList<PowerAI> powers,
                        ArrayList<Byte> moveHistory, CopyableRandom rand, AbstractMonster monster,
                        int bonusDamage) {
        super(health, block, powers, moveHistory, rand, monster, bonusDamage);
        creatureId = CreatureId.GREEN_LOUSE;
    }

    public GreenLouseAI() {
        super();
        creatureId = CreatureId.GREEN_LOUSE;
        health = 11 + rand.nextInt(7);
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
                // Can't use Weak three times in a row
                if (lastTwoMovesEqual(WEAK)) {
                    continue;
                }
                moveHistory.add(WEAK);
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
            case WEAK:
                player.addPower(PowerTypeAI.WEAK, 2);
                break;
            default:
                throw new RuntimeException("Invalid move : " + getCurrentMove());
        }
    }

    @Override
    public GreenLouseAI clone() {
        return new GreenLouseAI(health, block, clonePowers(),
                new ArrayList<>(moveHistory), rand.copy(), monster, bonusDamage);
    }
}