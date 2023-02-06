package dungeon;

import cards.interfaces.AbstractCardAI;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import monsters.AbstractMonsterAI;

public class CardMove {
    private final AbstractCardAI card;
    private final AbstractMonsterAI monster;

    public CardMove(AbstractCardAI card) {
        this.card = card;
        this.monster = null;
    }

    public CardMove(AbstractCardAI card, AbstractMonsterAI monster) {
        this.card = card;
        this.monster = monster;
    }

    public AbstractCard getCard() {
        return card.getCard();
    }

    public AbstractMonster getMonster() {
        return monster == null ? null : monster.getMonster();
    }
}
