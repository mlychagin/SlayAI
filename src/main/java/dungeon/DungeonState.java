package dungeon;

import cards.interfaces.AbstractCardAI;
import cards.interfaces.AttackCardAI;
import cards.interfaces.StateCardAI;
import cards.ironclad.starter.BashAI;
import cards.ironclad.starter.DefendAI;
import cards.ironclad.starter.StrikeAI;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.JawWorm;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import monsters.AbstractMonsterAI;
import monsters.act1.regular.JawWormAI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import player.PlayerAI;

import java.util.ArrayList;
import java.util.Collections;

public class DungeonState {
    public static final Logger logger = LogManager.getLogger(DungeonState.class.getName());
    public static int STATES_CONSIDERED = 0;
    private final PlayerAI player;
    private final ArrayList<AbstractCardAI> hand;
    private final ArrayList<AbstractCardAI> exhaustPile;
    private final ArrayList<AbstractCardAI> cardsPlayed = new ArrayList<>();
    private ArrayList<AbstractCardAI> discardPile;
    private ArrayList<AbstractCardAI> drawPile;
    private ArrayList<AbstractMonsterAI> monsters;
    private CopyableRandom shuffleRandom;
    private CopyableRandom miscRandom;

    public DungeonState() {
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        AbstractPlayer player = AbstractDungeon.player;
        this.player = new PlayerAI(player);
        drawPile = sanitizeCardList(player.drawPile);
        hand = sanitizeCardList(player.hand);
        discardPile = sanitizeCardList(player.discardPile);
        exhaustPile = sanitizeCardList(player.exhaustPile);

        if (room == null) {
            return;
        }

        MonsterGroup group = room.monsters;
        if (group == null) {
            return;
        }

        monsters = new ArrayList<>();
        for (AbstractMonster m : group.monsters) {
            logger.info(m.name + " : " + m.id);
            if (m instanceof JawWorm) {
                monsters.add(new JawWormAI((JawWorm) m));
            }
        }

        shuffleRandom = new CopyableRandom();
        miscRandom = new CopyableRandom();
    }

    public DungeonState(PlayerAI player, ArrayList<AbstractCardAI> deck, ArrayList<AbstractMonsterAI> monsters) {
        this.player = player;
        this.drawPile = new ArrayList<>(deck);
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.exhaustPile = new ArrayList<>();
        this.monsters = monsters;
        this.shuffleRandom = new CopyableRandom(1000);
        this.miscRandom = new CopyableRandom(1000);
        beginTurn();
    }

    private DungeonState(DungeonState state) {
        monsters = new ArrayList<>();
        for (AbstractMonsterAI monster : state.monsters) {
            monsters.add(monster.clone());
        }
        player = state.player.clone();
        drawPile = new ArrayList<>(state.drawPile);
        hand = new ArrayList<>(state.hand);
        discardPile = new ArrayList<>(state.discardPile);
        exhaustPile = new ArrayList<>(state.exhaustPile);
        shuffleRandom = state.shuffleRandom.copy();
        miscRandom = state.miscRandom.copy();
        STATES_CONSIDERED++;
    }

    private ArrayList<AbstractCardAI> sanitizeCardList(CardGroup cardGroup) {
        ArrayList<AbstractCard> cards = cardGroup.group;
        ArrayList<AbstractCardAI> sanitizedCards = new ArrayList<>();
        for (AbstractCard card : cards) {
            if (card.name.equals("Bash")) {
                sanitizedCards.add(new BashAI());
            }
            if (card.name.equals("Defend")) {
                sanitizedCards.add(new DefendAI());
            }
            if (card.name.equals("Strike")) {
                sanitizedCards.add(new StrikeAI());
            }
        }
        return sanitizedCards;
    }

    public PlayerAI getPlayer() {
        return player;
    }

    public ArrayList<AbstractCardAI> getHand() {
        return hand;
    }

    public ArrayList<AbstractCardAI> getExhaustPile() {
        return exhaustPile;
    }

    public ArrayList<AbstractCardAI> getDiscardPile() {
        return discardPile;
    }

    public ArrayList<AbstractCardAI> getDrawPile() {
        return drawPile;
    }

    public ArrayList<AbstractMonsterAI> getMonsters() {
        return monsters;
    }

    public CopyableRandom getShuffleRandom() {
        return shuffleRandom;
    }

    public CopyableRandom getMiscRandom() {
        return miscRandom;
    }

    public void addCardToDiscardPile(AbstractCardAI card) {
        discardPile.add(card);
    }

    public void addCardToDrawPile(AbstractCardAI card) {
        drawPile.add(card);
    }

    public ArrayList<AbstractCardAI> getCardsPlayed() {
        return cardsPlayed;
    }

    public void playCard(AbstractCardAI card) {
        playCard(card, null);
    }

    public void playCard(AbstractCardAI card, AbstractMonsterAI monster) {
        if (card instanceof AttackCardAI) {
            ((AttackCardAI) card).playCard(this, monster);
        } else if (card instanceof StateCardAI) {
            ((StateCardAI) card).playCard(this);
        }
        hand.remove(card);
        discardPile.add(card);
    }

    public void drawCard() {
        if (drawPile.size() == 0) {
            ArrayList<AbstractCardAI> tmp;
            tmp = drawPile;
            drawPile = discardPile;
            discardPile = tmp;
            Collections.shuffle(drawPile, shuffleRandom);
        }
        hand.add(drawPile.remove(drawPile.size() - 1));
    }

    public void beginTurn() {
        player.resetBlock();
        player.endTurnPower();
        for (int i = 0; i < 5; i++) {
            drawCard();
        }
    }

    public void endTurn() {
        discardPile.addAll(hand);
        hand.clear();
        for (AbstractMonsterAI monster : monsters) {
            if (!monster.isDead()) {
                monster.playTurn(this);
            }
        }
        beginTurn();
    }

    public int getHeuristic() {
        return player.getHealth();
    }

    public boolean isEndState() {
        if (player.isDead()) {
            return true;
        }
        for (AbstractMonsterAI monster : monsters) {
            if (!monster.isDead()) {
                return false;
            }
        }
        return true;
    }

    public boolean isBetterInAnyWay(DungeonState other) {
        if (player.isBetterInAnyWay(other.player)) {
            return true;
        }
        if (monsters.size() != other.monsters.size()) {
            return true;
        }
        for (int i = 0; i < monsters.size(); i++) {
            if (other.monsters.get(i).isBetterInAnyWay(monsters.get(i))) {
                return true;
            }
        }

        // Should we consider draw pile? I think we have to, but leave it alone for now
        return false;
    }

    @Override
    public DungeonState clone() {
        return new DungeonState(this);
    }
}
