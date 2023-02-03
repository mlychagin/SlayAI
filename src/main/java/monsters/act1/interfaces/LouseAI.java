package monsters.act1.interfaces;

import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;
import dungeon.CopyableRandom;
import monsters.AbstractCreatureAI;
import monsters.AbstractMonsterAI;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public abstract class LouseAI extends AbstractMonsterAI {
    protected final int bonusDamage;

    public LouseAI(LouseNormal monster) {
        super(monster);
        //Todo: Figure out the exact number when monster attacks
        bonusDamage = 5 + rand.nextInt(3);
    }

    protected LouseAI(LouseAI monster) {
        super(monster);
        bonusDamage = monster.bonusDamage;
    }

    public LouseAI() {
        super();
        this.health = 10 + rand.nextInt(6);
        this.block = 0;
        this.bonusDamage = 5 + rand.nextInt(3);
        this.powers = new ArrayList<>();
        this.moveHistory = new ArrayList<>();
        addPower(PowerTypeAI.CURL_UP, 1);
        getNextMove(null);
    }

    public int getBonusDamage() {
        return bonusDamage;
    }

    @Override
    public void takeDamage(AbstractCreatureAI source, int value) {
        super.takeDamage(source, value);
        PowerAI power;
        for (int i = 0; i < powers.size(); i++) {
            power = powers.get(i);
            if (power.getType() == PowerTypeAI.CURL_UP) {
                addBlock(3 + rand.nextInt(5));
                powers.remove(i);
                return;
            }
        }
    }

}