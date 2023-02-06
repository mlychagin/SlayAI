package networking;

import cards.CardIdUtil.CardId;
import cards.interfaces.AbstractCardAI;
import cards.ironclad.common.*;
import cards.ironclad.starter.BashAI;
import cards.ironclad.starter.DefendAI;
import cards.ironclad.starter.StrikeAI;
import cards.neutral.SlimedAI;
import cards.neutral.WoundAI;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AbstractCardAIDeserializer implements JsonDeserializer<AbstractCardAI> {

    @Override
    public AbstractCardAI deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        CardId id = CardId.valueOf(jsonObject.get("cardId").getAsString());
        boolean upgraded = jsonObject.get("upgraded").getAsBoolean();

        switch (id) {
            case BASH:
                return new BashAI(upgraded);
            case STRIKE:
                return new StrikeAI(upgraded);
            case DEFEND:
                return new DefendAI(upgraded);
            case ANGER:
                return new AngerAI(upgraded);
            case BODY_SLAM:
                return new BodySlamAI(upgraded);
            case CLASH:
                return new ClashAI(upgraded);
            case CLEAVE:
                return new CleaveAI(upgraded);
            case CLOTHESLINE:
                return new ClotheslineAI(upgraded);
            case FLEX:
                return new FlexAI(upgraded);
            case HEAVY_BLADE:
                return new HeavyBladeAI(upgraded);
            case IRON_WAVE:
                return new IronWaveAI(upgraded);
            case PERFECTED_STRIKE:
                return new PerfectedStrikeAI(upgraded);
            case POMMEL_STRIKE:
                return new PommelStrikeAI(upgraded);
            case SHRUG_IT_OFF:
                return new ShrugItOffAI(upgraded);
            case SWORD_BOOMERANG:
                return new SwordBoomerangAI(upgraded);
            case THUNDERCLAP:
                return new ThunderclapAI(upgraded);
            case TWIN_STRIKE:
                return new TwinStrikeAI(upgraded);
            case WILD_STRIKE:
                return new WildStrikeAI(upgraded);
            case SLIMED:
                return new SlimedAI(upgraded);
            case WOUND:
                return new WoundAI();
            case WARCRY:
            case BATTLE_TRANCE:
            case BLOOD_FOR_BLOOD:
            case BLOODLETTING:
            case BURNING_PACT:
            case CARNAGE:
            case COMBUST:
            case DARK_EMBRACE:
            case DISARM:
            case DROPKICK:
            case DUAL_WIELD:
            case ENTRENCH:
            case EVOLVE:
            case FEEL_NO_PAIN:
            case FIRE_BREATHING:
            case FLAME_BARRIER:
            case GHOSTLY_ARMOR:
            case HEMOKINESIS:
            case INFERNAL_BLADE:
            case INFLAME:
            case INTIMIDATE:
            case METALLICIZE:
            case POWER_THROUGH:
            case PUMMEL:
            case RAGE:
            case RAMPAGE:
            case RECKLESS_CHARGE:
            case RUPTURE:
            case SEARING_BLOW:
            case SECOND_WIND:
            case SEEING_RED:
            case SENTINEL:
            case SEVER_SOUL:
            case SHOCKWAVE:
            case SPOT_WEAKNESS:
            case UPPERCUT:
            case WHIRLWIND:
            case BARRICADE:
            case BERSEK:
            case BLUDGEON:
            case BRUTALITY:
            case CORRUPTION:
            case DEMON_FORM:
            case DOUBLE_TAP:
            case EXHUME:
            case FEED:
            case FIEND_FIRE:
            case IMMOLATE:
            case IMPERVIOUS:
            case JUGGERNAUT:
            case LIMIT_BREAK:
            case OFFERING:
            case REAPER:
            case ARMAMENTS:
            case HAVOC:
            case HEADBUTT:
            case TRUE_GRIT:
            default:
                throw new RuntimeException();
        }
    }
}