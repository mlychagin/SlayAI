package cards;

import java.util.HashMap;

public class CardIdUtil {
    public static HashMap<CardId, Integer> DEFAULT_CARD_PRIORITY = new HashMap<>();
    public static HashMap<CardId, Integer> DRAW_CARD_PRIORITY;

    static {
        // Upgrade
        DEFAULT_CARD_PRIORITY.put(CardId.ARMAMENTS, 1);

        // Power
        DEFAULT_CARD_PRIORITY.put(CardId.RAGE, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.COMBUST, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.DARK_EMBRACE, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.EVOLVE, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.FEEL_NO_PAIN, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.FIRE_BREATHING, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.INFLAME, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.METALLICIZE, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.RUPTURE, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.BARRICADE, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.BERSEK, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.BRUTALITY, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.CORRUPTION, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.DEMON_FORM, 10);
        DEFAULT_CARD_PRIORITY.put(CardId.JUGGERNAUT, 10);

        // Draw
        DEFAULT_CARD_PRIORITY.put(CardId.HAVOC, 20);
        DEFAULT_CARD_PRIORITY.put(CardId.TRUE_GRIT, 20);
        DEFAULT_CARD_PRIORITY.put(CardId.WARCRY, 20);
        DEFAULT_CARD_PRIORITY.put(CardId.BURNING_PACT, 20);
        DEFAULT_CARD_PRIORITY.put(CardId.INFERNAL_BLADE, 20);
        DEFAULT_CARD_PRIORITY.put(CardId.SHRUG_IT_OFF, 20);
        DEFAULT_CARD_PRIORITY.put(CardId.DUAL_WIELD, 20);
        DEFAULT_CARD_PRIORITY.put(CardId.OFFERING, 20);
        DEFAULT_CARD_PRIORITY.put(CardId.BATTLE_TRANCE, 20);

        // Energy
        DEFAULT_CARD_PRIORITY.put(CardId.BLOODLETTING, 25);
        DEFAULT_CARD_PRIORITY.put(CardId.SEEING_RED, 25);

        // Buff
        DEFAULT_CARD_PRIORITY.put(CardId.FLEX, 30);
        DEFAULT_CARD_PRIORITY.put(CardId.SPOT_WEAKNESS, 30);
        DEFAULT_CARD_PRIORITY.put(CardId.LIMIT_BREAK, 30);

        // Vulnerable
        DEFAULT_CARD_PRIORITY.put(CardId.UPPERCUT, 40);
        DEFAULT_CARD_PRIORITY.put(CardId.THUNDERCLAP, 40);
        DEFAULT_CARD_PRIORITY.put(CardId.SHOCKWAVE, 40);
        DEFAULT_CARD_PRIORITY.put(CardId.BASH, 40);

        // Attack Prio
        DEFAULT_CARD_PRIORITY.put(CardId.PERFECTED_STRIKE, 50);
        DEFAULT_CARD_PRIORITY.put(CardId.SWORD_BOOMERANG, 50);

        // Attack
        DEFAULT_CARD_PRIORITY.put(CardId.STRIKE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.ANGER, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.CLASH, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.CLEAVE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.HEAVY_BLADE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.TWIN_STRIKE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.WILD_STRIKE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.BLOOD_FOR_BLOOD, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.CARNAGE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.BLUDGEON, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.IMMOLATE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.REAPER, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.HEMOKINESIS, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.PUMMEL, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.RAMPAGE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.RECKLESS_CHARGE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.SEARING_BLOW, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.CLOTHESLINE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.IRON_WAVE, 60);

        // Need special logic for feed
        DEFAULT_CARD_PRIORITY.put(CardId.FEED, 62);

        // Block
        DEFAULT_CARD_PRIORITY.put(CardId.DEFEND, 70);
        DEFAULT_CARD_PRIORITY.put(CardId.FLAME_BARRIER, 70);
        DEFAULT_CARD_PRIORITY.put(CardId.GHOSTLY_ARMOR, 70);
        DEFAULT_CARD_PRIORITY.put(CardId.POWER_THROUGH, 70);
        DEFAULT_CARD_PRIORITY.put(CardId.SENTINEL, 70);
        DEFAULT_CARD_PRIORITY.put(CardId.IMPERVIOUS, 70);

        // Weak
        DEFAULT_CARD_PRIORITY.put(CardId.DISARM, 80);
        DEFAULT_CARD_PRIORITY.put(CardId.INTIMIDATE, 80);

        // Mass Exhaust
        DEFAULT_CARD_PRIORITY.put(CardId.SEVER_SOUL, 90);
        DEFAULT_CARD_PRIORITY.put(CardId.SECOND_WIND, 90);

        // All block is played
        DEFAULT_CARD_PRIORITY.put(CardId.ENTRENCH, 95);
        DEFAULT_CARD_PRIORITY.put(CardId.BODY_SLAM, 95);

        // Ends turn
        DEFAULT_CARD_PRIORITY.put(CardId.FIEND_FIRE, 99);
        DEFAULT_CARD_PRIORITY.put(CardId.WHIRLWIND, 99);

        // Neutral
        DEFAULT_CARD_PRIORITY.put(CardId.SLIMED, 100);

        // Handle multipurpose draw cards
        DRAW_CARD_PRIORITY = new HashMap<>(DEFAULT_CARD_PRIORITY);
        DRAW_CARD_PRIORITY.put(CardId.DROPKICK, 20);
        DRAW_CARD_PRIORITY.put(CardId.POMMEL_STRIKE, 20);
        DRAW_CARD_PRIORITY.put(CardId.HEADBUTT, 20);

        DEFAULT_CARD_PRIORITY.put(CardId.DROPKICK, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.POMMEL_STRIKE, 60);
        DEFAULT_CARD_PRIORITY.put(CardId.HEADBUTT, 60);
    }

    public static boolean isStrikeCard(CardId id) {
        switch (id) {
            case PERFECTED_STRIKE:
            case POMMEL_STRIKE:
            case TWIN_STRIKE:
            case WILD_STRIKE:
            case STRIKE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isPlayableCard(CardId id) {
        switch (id) {
            case WOUND:
                return false;
            default:
                return true;
        }
    }

    public static boolean isAttackCard(CardId id) {
        switch (id) {
            case BASH:
            case STRIKE:
            case ANGER:
            case BODY_SLAM:
            case CLASH:
            case CLEAVE:
            case CLOTHESLINE:
            case HEADBUTT:
            case HEAVY_BLADE:
            case IRON_WAVE:
            case PERFECTED_STRIKE:
            case POMMEL_STRIKE:
            case SWORD_BOOMERANG:
            case THUNDERCLAP:
            case TWIN_STRIKE:
            case WILD_STRIKE:
            case BLOOD_FOR_BLOOD:
            case CARNAGE:
            case DROPKICK:
            case HEMOKINESIS:
            case PUMMEL:
            case RAMPAGE:
            case RECKLESS_CHARGE:
            case SEARING_BLOW:
            case SEVER_SOUL:
            case UPPERCUT:
            case WHIRLWIND:
            case BLUDGEON:
            case FEED:
            case FIEND_FIRE:
            case IMMOLATE:
            case REAPER:
                return true;
            default:
                return false;
        }
    }

    public enum CardId {
        // Ironclad
        BASH,
        STRIKE,
        DEFEND,
        ANGER,
        ARMAMENTS,
        BODY_SLAM,
        CLASH,
        CLEAVE,
        CLOTHESLINE,
        FLEX,
        HAVOC,
        HEADBUTT,
        HEAVY_BLADE,
        IRON_WAVE,
        PERFECTED_STRIKE,
        POMMEL_STRIKE,
        SHRUG_IT_OFF,
        SWORD_BOOMERANG,
        THUNDERCLAP,
        TRUE_GRIT,
        TWIN_STRIKE,
        WARCRY,
        WILD_STRIKE,
        BATTLE_TRANCE,
        BLOOD_FOR_BLOOD,
        BLOODLETTING,
        BURNING_PACT,
        CARNAGE,
        COMBUST,
        DARK_EMBRACE,
        DISARM,
        DROPKICK,
        DUAL_WIELD,
        ENTRENCH,
        EVOLVE,
        FEEL_NO_PAIN,
        FIRE_BREATHING,
        FLAME_BARRIER,
        GHOSTLY_ARMOR,
        HEMOKINESIS,
        INFERNAL_BLADE,
        INFLAME,
        INTIMIDATE,
        METALLICIZE,
        POWER_THROUGH,
        PUMMEL,
        RAGE,
        RAMPAGE,
        RECKLESS_CHARGE,
        RUPTURE,
        SEARING_BLOW,
        SECOND_WIND,
        SEEING_RED,
        SENTINEL,
        SEVER_SOUL,
        SHOCKWAVE,
        SPOT_WEAKNESS,
        UPPERCUT,
        WHIRLWIND,
        BARRICADE,
        BERSEK,
        BLUDGEON,
        BRUTALITY,
        CORRUPTION,
        DEMON_FORM,
        DOUBLE_TAP,
        EXHUME,
        FEED,
        FIEND_FIRE,
        IMMOLATE,
        IMPERVIOUS,
        JUGGERNAUT,
        LIMIT_BREAK,
        OFFERING,
        REAPER,

        //Neutral
        SLIMED,
        WOUND
    }
}
