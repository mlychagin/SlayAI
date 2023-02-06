package networking;

import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import communicationmod.CommandExecutor;
import communicationmod.CommunicationMod;
import communicationmod.OnStateChangeSubscriber;
import dungeon.CardMove;
import dungeon.DungeonState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static algorithms.DFSAlgorithmUtil.dungeonStateDFS;

@SpireInitializer
public class SlayModAI implements PostDrawSubscriber, OnStateChangeSubscriber, RenderSubscriber, ISubscriber {
    public static final Logger logger = LogManager.getLogger(SlayModAI.class.getName());
    private final Queue<CardMove> moves = new LinkedList<>();
    private final Gson gson = new Gson();
    private boolean movesCalculated = false;

    public SlayModAI() {
        CommunicationMod.subscribe(this);
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new SlayModAI();
    }

    public void receiveOnStateChange() {
        AbstractRoom room;
        if (CommandExecutor.isInDungeon()) {
            room = AbstractDungeon.getCurrRoom();
            if (room.monsters == null) {
                return;
            }
            if (room.phase == AbstractRoom.RoomPhase.COMPLETE) {
                return;
            }
            if (moves.size() == 0) {
                if (movesCalculated) {
                    logger.info("End turn");
                    AbstractDungeon.actionManager.callEndTurnEarlySequence();
                    movesCalculated = false;
                    return;
                }
                populateMoves();
            }
            CardMove move = moves.remove();
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(move.getCard(), move.getMonster()));

            logger.info("Move played : " + move.getCard().cardID + " -> " + (move.getMonster() == null ?
                    "null" : move.getMonster().id));
        }
    }

    private void populateMoves() {
        DungeonState state = new DungeonState(true);
        logger.info(gson.toJson(state));
        ArrayList<DungeonState> bestPath = dungeonStateDFS(state);
        if (bestPath.size() == 0) {
            throw new RuntimeException();
        }
        moves.addAll(bestPath.get(1).getCardsPlayed());
        movesCalculated = true;
        logger.info("Moves populated : " + moves.size());
    }


    public void receivePostDraw(AbstractCard abstractCard) {
        logger.info("Move cleared");
        moves.clear();
        movesCalculated = false;
    }

    @Override
    public void receiveRender(SpriteBatch spriteBatch) {
    }

}
