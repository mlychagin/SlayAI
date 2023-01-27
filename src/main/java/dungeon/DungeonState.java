package dungeon;

import cards.interfaces.AbstractCardAI;
import cards.interfaces.AttackCardAI;
import cards.interfaces.PlayerCardAI;
import cards.interfaces.VoidCardAI;
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
import java.util.HashSet;

import static algorithms.CardPriorityUtil.getMoveSet;

public class DungeonState {
    public static final Logger logger = LogManager.getLogger(DungeonState.class.getName());
    public static int STATES_CONSIDERED = 0;
    private final PlayerAI player;
    private final ArrayList<AbstractCardAI> masterDeck;
    private final ArrayList<AbstractCardAI> hand;
    private final ArrayList<AbstractCardAI> exhaustPile;
    private final ArrayList<AbstractCardAI> cardsPlayed = new ArrayList<>();
    private ArrayList<AbstractMonsterAI> monsters;
    private ArrayList<AbstractCardAI> drawPile;
    private ArrayList<AbstractCardAI> discardPile;
    private CopyableRandom random;

    public DungeonState() {
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        AbstractPlayer player = AbstractDungeon.player;
        this.player = new PlayerAI(player);
        masterDeck = sanitizeCardList(player.masterDeck);
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

        random = new CopyableRandom();
    }

    public DungeonState(PlayerAI player, ArrayList<AbstractCardAI> deck, ArrayList<AbstractMonsterAI> monsters) {
        this.player = player;
        this.masterDeck = new ArrayList<>(deck);
        this.drawPile = new ArrayList<>(deck);
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.exhaustPile = new ArrayList<>();
        this.monsters = monsters;
        this.random = new CopyableRandom();
        beginTurn();
        STATES_CONSIDERED++;
    }

    private DungeonState(DungeonState state) {
        monsters = new ArrayList<>();
        for (AbstractMonsterAI monster : state.monsters) {
            monsters.add(monster.clone());
        }
        player = state.player.clone();
        masterDeck = new ArrayList<>(state.masterDeck);
        drawPile = new ArrayList<>(state.drawPile);
        hand = new ArrayList<>(state.hand);
        discardPile = new ArrayList<>(state.discardPile);
        exhaustPile = new ArrayList<>(state.exhaustPile);
        random = state.random.copy();
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

    public ArrayList<AbstractMonsterAI> getMonsters() {
        return monsters;
    }

    public ArrayList<AbstractCardAI> getHand() {
        return hand;
    }

    public ArrayList<AbstractCardAI> getDiscardPile() {
        return discardPile;
    }

    public void addCardToDiscardPile(AbstractCardAI card) {
        discardPile.add(card);
    }

    public void exhaustCard(AbstractCardAI card) {
        if (!card.isExhaust()) {
            return;
        }
        hand.remove(card);
        exhaustPile.add(card);
    }

    public ArrayList<DungeonState> getPossibleStates() {
        ArrayList<DungeonState> result = new ArrayList<>();
        HashSet<ArrayList<AbstractCardAI>> moveSet = getMoveSet(hand, 3);
        for (ArrayList<AbstractCardAI> move : moveSet) {
            result.addAll(getPossibleStates(move));
        }
        return result;
    }

    public ArrayList<DungeonState> getPossibleStates(ArrayList<AbstractCardAI> move) {
        ArrayList<DungeonState> tmp;
        ArrayList<DungeonState> input = new ArrayList<>();
        ArrayList<DungeonState> output = new ArrayList<>();
        DungeonState newState;

        input.add(this.clone());
        for (AbstractCardAI card : move) {
            for (DungeonState state : input) {
                if (card instanceof AttackCardAI) {
                    for (int i = 0; i < state.monsters.size(); i++) {
                        AbstractMonsterAI monster = state.monsters.get(i);
                        if (monster.isDead()) {
                            continue;
                        }
                        newState = state.clone();
                        ((AttackCardAI) card).playCard(newState, newState.monsters.get(i));
                        newState.exhaustCard(card);
                        output.add(newState);
                    }
                } else if (card instanceof PlayerCardAI) {
                    newState = state.clone();
                    ((PlayerCardAI) card).playCard(newState.player);
                    newState.exhaustCard(card);
                    output.add(newState);
                } else if (card instanceof VoidCardAI) {
                    newState = state.clone();
                    ((VoidCardAI) card).playCard();
                    newState.exhaustCard(card);
                    output.add(newState);
                }
            }
            tmp = input;
            input = output;
            tmp.clear();
            output = tmp;
        }
        for (DungeonState state : input) {
            state.cardsPlayed.addAll(move);
            state.endTurn();
        }
        return input;
    }

    public void drawCard() {
        if (drawPile.size() == 0) {
            ArrayList<AbstractCardAI> tmp;
            tmp = drawPile;
            drawPile = discardPile;
            discardPile = tmp;
            Collections.shuffle(drawPile);
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

    @Override
    public DungeonState clone() {
        return new DungeonState(this);
    }
}
