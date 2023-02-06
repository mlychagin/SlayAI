package player;

import dungeon.CopyableRandom;
import monsters.AbstractCreatureAI;
import monsters.CreatureIdUtil;
import powers.PowerAI;

import java.util.ArrayList;

public class PlayerAI extends AbstractCreatureAI {

    public PlayerAI() {
        creatureId = CreatureIdUtil.CreatureId.PLAYER;
    }

    public PlayerAI(int health, int block, ArrayList<PowerAI> powers, CopyableRandom rand) {
        super(health, block, powers, rand);
        creatureId = CreatureIdUtil.CreatureId.PLAYER;
    }

    public PlayerAI(int health, int block) {
        this.health = health;
        this.block = block;
    }

    @Override
    public PlayerAI clone() {
        return new PlayerAI(health, block, clonePowers(), rand.copy());
    }
}
