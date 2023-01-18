import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostDrawSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import communicationmod.CommandExecutor;
import communicationmod.CommunicationMod;
import communicationmod.OnStateChangeSubscriber;
import dungeon.DungeonState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@SpireInitializer
public class PlayerTurn implements PostDrawSubscriber, OnStateChangeSubscriber, ISubscriber {
    public static final Logger logger = LogManager.getLogger(PlayerTurn.class.getName());
    private ArrayList<AbstractCard> hand = new ArrayList<>();

    public PlayerTurn() {
        CommunicationMod.subscribe(this);
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new PlayerTurn();
    }

    public void receiveOnStateChange() {
        if (CommandExecutor.isInDungeon()) {
            if (AbstractDungeon.player.hand.size() >= 5) {
                dungeonTurn();
            }
        }
    }

    private void dungeonTurn() {
        logger.info("SLAYAI: In Dungeon");
        DungeonState state = new DungeonState();
    }

    public void receivePostDraw(AbstractCard abstractCard) {
        hand.add(abstractCard);
        logger.info("SLAYAI: Draw " + abstractCard.cardID);
    }
}
