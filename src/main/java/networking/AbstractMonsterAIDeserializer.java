package networking;

import com.google.gson.*;
import dungeon.CopyableRandom;
import monsters.AbstractMonsterAI;
import monsters.CreatureIdUtil.CreatureId;
import monsters.act1.regular.*;
import powers.PowerAI;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class AbstractMonsterAIDeserializer implements JsonDeserializer<AbstractMonsterAI> {
    private final ArrayList<PowerAI> powerType = new ArrayList<PowerAI>();

    @Override
    public AbstractMonsterAI deserialize(JsonElement json, Type type,
                                         JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        CreatureId creatureId = CreatureId.valueOf(jsonObject.get("creatureId").getAsString());
        int health = jsonObject.get("health").getAsInt();
        int block = jsonObject.get("block").getAsInt();
        CopyableRandom rand = jsonDeserializationContext.deserialize(jsonObject.getAsJsonObject("rand"),
                CopyableRandom.class);

        // Powers
        PowerAI[] powersPre = jsonDeserializationContext.deserialize(jsonObject.getAsJsonArray("powers"),
                PowerAI[].class);
        ArrayList<PowerAI> powers = new ArrayList<>(Arrays.asList(powersPre));


        // Moves
        byte[] moveHistoryBytes = jsonDeserializationContext.deserialize(jsonObject.getAsJsonArray(
                "moveHistory"), byte[].class);
        ArrayList<Byte> moveHistory = new ArrayList<>();
        for (byte b : moveHistoryBytes) {
            moveHistory.add(b);
        }

        switch (creatureId) {
            case ACID_SLIME_LARGE:
                return new AcidSlimeLargeAI(health, block, powers, moveHistory, rand, null);
            case ACID_SLIME_MEDIUM:
                return new AcidSlimeMediumAI(health, block, powers, moveHistory, rand, null);
            case ACID_SLIME_SMALL:
                return new AcidSlimeSmallAI(health, block, powers, moveHistory, rand, null);
            case BLUE_SAVER:
                return new BlueSlaverAI(health, block, powers, moveHistory, rand, null);
            case CULTIST:
                return new CultistAI(health, block, powers, moveHistory, rand, null);
            case FAT_GREMLIN:
                return new FatGremlinAI(health, block, powers, moveHistory, rand, null);
            case FUNGI_BEAST:
                return new FungiBeastAI(health, block, powers, moveHistory, rand, null);
            case GREEN_LOUSE:
                return new GreenLouseAI(health, block, powers, moveHistory, rand, null, jsonObject.get(
                        "bonusDamage").getAsInt());
            case JAWWORM:
                return new JawWormAI(health, block, powers, moveHistory, rand, null);
            case MAD_GREMLIN:
                return new MadGremlinAI(health, block, powers, moveHistory, rand, null);
            case RED_LOUSE:
                return new RedLouseAI(health, block, powers, moveHistory, rand, null, jsonObject.get(
                        "bonusDamage").getAsInt());
            case RED_SLAVER:
                return new RedSlaverAI(health, block, powers, moveHistory, rand, null);
            case SHIELD_GREMLIN:
                return new ShieldGremlinAI(health, block, powers, moveHistory, rand, null);
            case SNEAKY_GREMLIN:
                return new SneakyGremlinAI(health, block, powers, moveHistory, rand, null);
            case SPIKE_SLIME_LARGE:
                return new SpikeSlimeLargeAI(health, block, powers, moveHistory, rand, null);
            case SPIKE_SLIME_MEDIUM:
                return new SpikeSlimeMediumAI(health, block, powers, moveHistory, rand, null);
            case SPIKE_SLIME_SMALL:
                return new SpikeSlimeSmallAI(health, block, powers, moveHistory, rand, null);
            case WIZARD_GREMLIN:
                return new WizardGremlinAI(health, block, powers, moveHistory, rand, null);
            default:
                throw new RuntimeException();
        }
    }
}