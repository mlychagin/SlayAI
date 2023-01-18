package player;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import monsters.AbstractCreatureAI;

public class PlayerAI extends AbstractCreatureAI {

    public PlayerAI(AbstractPlayer player) {
        health = player.currentHealth;
        block = player.currentBlock;
    }

    public PlayerAI(PlayerAI player) {
        this.health = player.health;
        this.block = player.block;
    }

    public PlayerAI(int health, int block) {
        this.health = health;
        this.block = block;
    }

    @Override
    public PlayerAI clone() {
        return new PlayerAI(this);
    }
}
