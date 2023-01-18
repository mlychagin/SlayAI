package monsters;

import dungeon.CopyableRandom;
import player.PlayerAI;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public abstract class AbstractCreatureAI {
    protected int health;
    protected int block;
    protected ArrayList<PowerAI> powers = new ArrayList<>();
    protected CopyableRandom r;

    public AbstractCreatureAI() {
        r = new CopyableRandom();
    }

    protected AbstractCreatureAI(AbstractCreatureAI creature) {
        this.health = creature.health;
        this.block = creature.block;
        for (PowerAI power : creature.powers) {
            this.powers.add(power.clone());
        }
        this.r = creature.r.copy();
    }

    public int getHealth() {
        return health;
    }

    public int getBlock() {
        return block;
    }

    public void takeHealing(int value) {
        health += value;
    }

    public void takeDamage(AbstractCreatureAI source, int damage) {
        damage += source.getPower(PowerAI.PowerTypeAI.STRENGTH);

        // Vulnerable
        if (getPower(PowerTypeAI.VULNERABLE) > 0) {
            damage *= 1.5;
        }

        // Weak
        if (source.getPower(PowerTypeAI.WEAK) > 0) {
            damage *= 0.75;
        }

        block -= damage;
        if (block < 0) {
            health += block;
            block = 0;
        }
    }

    public void addBlock (int value) {
        block += value;
    }

    public void resetBlock() {
        block = 0;
    }

    public void addPower(PowerTypeAI type, int amount) {
        for (PowerAI power : powers) {
            if (power.getType() == type) {
                power.addAmount(amount);
                return;
            }
        }
        powers.add(new PowerAI(type, amount));
    }

    public void removePower(PowerTypeAI type, int amount) {
        for (PowerAI power : powers) {
            if (power.getType() == type) {
                power.addAmount(amount);
                return;
            }
        }
    }

    public int getPower(PowerTypeAI type) {
        PowerAI power;
        for (int i = 0; i < powers.size(); i++) {
            power = powers.get(i);
            if (power.getType() == type) {
                if (power.getAmount() <= 0) {
                    powers.remove(i);
                    return 0;
                }
                return power.getAmount();
            }
        }
        return 0;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void endTurnPower() {
        for (PowerAI power : powers) {
            switch (power.getType()) {
                case VULNERABLE:
                case WEAK:
                    power.removeAmount(1);
                default:
                    break;
            }
        }
    }

    @Override
    public abstract AbstractCreatureAI clone();

}
