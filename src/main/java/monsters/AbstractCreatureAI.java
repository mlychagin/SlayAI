package monsters;

import dungeon.CopyableRandom;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public abstract class AbstractCreatureAI {
    protected int health;
    protected int maxHealth;
    protected int block;
    protected ArrayList<PowerAI> powers = new ArrayList<>();
    protected CopyableRandom rand;

    public AbstractCreatureAI() {
        rand = new CopyableRandom(1000);
    }

    protected AbstractCreatureAI(AbstractCreatureAI creature) {
        this.health = creature.health;
        this.block = creature.block;
        for (PowerAI power : creature.powers) {
            this.powers.add(power.clone());
        }
        this.rand = creature.rand.copy();
    }

    public int getHealth() {
        return health;
    }

    public int getBlock() {
        return block;
    }

    public CopyableRandom getRand() {
        return rand;
    }

    public void takeHealing(int value) {
        health += value;
    }

    public void takeDamage(AbstractCreatureAI source, int damage) {
        int strength = 0;
        int vulnerable = 0;
        int weak = 0;

        for (PowerAI power : source.powers) {
            switch (power.getType()) {
                case STRENGTH:
                    strength = power.getAmount();
                    break;
                case WEAK:
                    weak = power.getAmount();
                    break;
                default:
                    break;
            }
        }

        int size = powers.size();
        for (int i = 0; i < size; i++) {
            PowerAI power = powers.get(i);
            switch (power.getType()) {
                case VULNERABLE:
                    vulnerable = power.getAmount();
                    break;
                case ANGRY:
                    addPower(PowerTypeAI.STRENGTH, power.getAmount());
                    break;
                default:
                    break;
            }

        }

        // Strength
        damage += strength;

        // Vulnerable
        if (vulnerable > 0) {
            damage *= 1.5;
        }

        // Weak
        if (weak > 0) {
            damage *= 0.75;
        }

        block -= damage;
        if (block < 0) {
            health += block;
            block = 0;
        }
    }

    public void addBlock(int value) {
        if (getPower(PowerTypeAI.FRAIL) > 0) {
            block += value * 0.75;
        } else {
            block += value;
        }
    }

    public void resetBlock() {
        block = 0;
    }

    public void addPower(PowerTypeAI type, int amount) {
        if (amount == 0) {
            return;
        }
        for (PowerAI power : powers) {
            if (power.getType() == type) {
                power.addAmount(amount);
                return;
            }
        }
        powers.add(new PowerAI(type, amount));
    }

    public void removePower(PowerTypeAI type, int amount) {
        if (amount == 0) {
            return;
        }
        for (PowerAI power : powers) {
            if (power.getType() == type) {
                power.removeAmount(amount);
                return;
            }
        }
    }

    public PowerAI getPowerObject(PowerTypeAI type) {
        PowerAI power;
        for (PowerAI powerAI : powers) {
            power = powerAI;
            if (power.getType() == type) {
                return power;
            }
        }
        return null;
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
        int size = powers.size();
        for (int i = 0; i < size; i++) {
            PowerAI power = powers.get(i);
            switch (power.getType()) {
                case VULNERABLE:
                case WEAK:
                    power.removeAmount(1);
                case PRE_RITUAL:
                    addPower(PowerTypeAI.RITUAL, power.getAmount());
                    power.removeAmount(power.getAmount());
                    break;
                case RITUAL:
                    addPower(PowerTypeAI.STRENGTH, power.getAmount());
                    break;
                case FLEX:
                    removePower(PowerTypeAI.STRENGTH, power.getAmount());
                    power.removeAmount(power.getAmount());
                    break;
                default:
                    break;
            }
        }
    }

    public boolean isBetterInAnyWay(AbstractCreatureAI other) {
        if (this.health < other.health) {
            return false;
        }
        if (this.block < other.block) {
            return false;
        }
        for (PowerAI power : this.powers) {
            if (power.compare(other.getPowerObject(power.getType())) < 0) {
                return false;
            }
        }
        for (PowerAI power : other.powers) {
            if (power.compare(this.getPowerObject(power.getType())) < 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public abstract AbstractCreatureAI clone();

}
