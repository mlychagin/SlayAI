package util;

import cards.interfaces.AbstractCardAI;
import cards.ironclad.starter.BashAI;
import cards.ironclad.starter.DefendAI;
import cards.ironclad.starter.StrikeAI;
import dungeon.DungeonState;
import monsters.AbstractMonsterAI;
import player.PlayerAI;

import java.util.ArrayList;

public class IroncladUtil {
    // Deck consists of 1x Bash, 5x Defend, 5x Strike
    public static ArrayList<AbstractCardAI> getStarterIroncladDeck() {
        ArrayList<AbstractCardAI> deck = new ArrayList<>();
        deck.add(new BashAI(false));
        for (int i = 0; i < 5; i++) {
            deck.add(new StrikeAI(false));
            deck.add(new DefendAI(false));
        }
        return deck;
    }

    public static PlayerAI getStarterIronclad() {
        return new PlayerAI(80, 0);
    }

    public static DungeonState getIronCladDungeonState() {
        PlayerAI player = getStarterIronclad();
        ArrayList<AbstractCardAI> deck = getStarterIroncladDeck();
        ArrayList<AbstractMonsterAI> monsters = new ArrayList<>();
        return new DungeonState(player, deck, monsters, 3);
    }

    public static DungeonState getJawWormState() {
        PlayerAI player = getStarterIronclad();
        ArrayList<AbstractCardAI> deck = getStarterIroncladDeck();
        ArrayList<AbstractMonsterAI> monsters = new ArrayList<>();
        monsters.add(MonsterUtil.getJawWorm());
        return new DungeonState(player, deck, monsters, 3);
    }

}
