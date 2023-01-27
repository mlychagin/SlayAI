package powers;

public class PowerAI {
    protected PowerTypeAI type;
    protected int amount;

    public PowerAI(PowerAI power) {
        this.type = power.type;
        this.amount = power.amount;
    }

    public PowerAI(PowerTypeAI type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public PowerTypeAI getType() {
        return type;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void removeAmount(int amount) {
        this.amount -= amount;
    }

    public PowerAI clone() {
        return new PowerAI(this);
    }

    public enum PowerTypeAI {
        STRENGTH,
        VULNERABLE,
        WEAK,
        CURL_UP,
        PRE_RITUAL,
        RITUAL,
        FRAIL,
        ANGRY,
        ENTANGLE
    }
}
