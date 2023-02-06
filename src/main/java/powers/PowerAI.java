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

    /*
     * Results:
     *
     *  1: This object is greater
     *  0: Both objects are equal
     * -1: Other object is better
     */
    public int compare(PowerAI other) {
        switch (type) {
            // Buff
            case STRENGTH:
            case CURL_UP:
            case PRE_RITUAL:
            case RITUAL:
            case ANGRY:
                if (other == null || amount > other.amount) {
                    return 1;
                } else if (amount < other.amount) {
                    return -1;
                } else {
                    return 0;
                }

                // Debuff
            case VULNERABLE:
            case WEAK:
            case FRAIL:
            case ENTANGLE:
                if (other == null || amount > other.amount) {
                    return -1;
                } else if (amount < other.amount) {
                    return 1;
                } else {
                    return 0;
                }

                // Don't consider
            case FLEX:
                return 0;

            default:
                return 0;
        }
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
        ENTANGLE,
        FLEX,
        SPORE
    }
}
