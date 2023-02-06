package monsters.act1.interfaces;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dungeon.CopyableRandom;
import monsters.AbstractCreatureAI;
import monsters.AbstractMonsterAI;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public abstract class LouseAI extends AbstractMonsterAI {
    protected int bonusDamage;

    public LouseAI(int health, int block, ArrayList<PowerAI> powers, ArrayList<Byte> moveHistory,
                   CopyableRandom rand, AbstractMonster monster, int bonusDamage) {
        super(health, block, powers, moveHistory, rand, monster);
        this.bonusDamage = bonusDamage;
    }

    public LouseAI() {
        super();
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