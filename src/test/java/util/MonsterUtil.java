package util;

import monsters.act1.GreenLouseAI;
import monsters.act1.JawWormAI;
import monsters.act1.RedLouseAI;

public class MonsterUtil {

    public static JawWormAI getJawWorm() {
        return new JawWormAI();
    }

    public static RedLouseAI getRedLouse() {
        return new RedLouseAI();
    }

    public static GreenLouseAI getGreenLouse() {
        return new GreenLouseAI();
    }
}
