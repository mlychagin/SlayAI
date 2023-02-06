package networking;

import cards.interfaces.AbstractCardAI;
import cards.ironclad.common.*;
import cards.ironclad.starter.BashAI;
import cards.ironclad.starter.DefendAI;
import cards.ironclad.starter.StrikeAI;
import cards.neutral.SlimedAI;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.powers.*;
import dungeon.CopyableRandom;
import monsters.AbstractMonsterAI;
import monsters.act1.regular.*;
import powers.PowerAI;
import powers.PowerAI.PowerTypeAI;

import java.util.ArrayList;

public class SanitizingUtil {

    public static AbstractMonsterAI getMonster(AbstractMonster monster) {
        int health = monster.currentHealth;
        int block = monster.currentBlock;
        ArrayList<PowerAI> powers = new ArrayList<>();
        ArrayList<Byte> moveHistory = new ArrayList<>(monster.moveHistory);
        for (AbstractPower power : monster.powers) {
            PowerAI powerAI = null;
            switch (power.ID) {
                case StrengthPower.POWER_ID:
                    powerAI = new PowerAI(PowerTypeAI.STRENGTH, power.amount);
                    break;
                case VulnerablePower.POWER_ID:
                    powerAI = new PowerAI(PowerTypeAI.VULNERABLE, power.amount);
                    break;
                case WeakPower.POWER_ID:
                    powerAI = new PowerAI(PowerTypeAI.WEAK, power.amount);
                    break;
                case CurlUpPower.POWER_ID:
                    powerAI = new PowerAI(PowerTypeAI.CURL_UP, power.amount);
                    break;
                case RitualPower.POWER_ID:
                    powerAI = new PowerAI(PowerTypeAI.RITUAL, power.amount);
                    break;
                case FrailPower.POWER_ID:
                    powerAI = new PowerAI(PowerTypeAI.FRAIL, power.amount);
                    break;
                case AngryPower.POWER_ID:
                    powerAI = new PowerAI(PowerTypeAI.ANGRY, power.amount);
                    break;
                // Ignore
                case SporeCloudPower.POWER_ID:
                case SplitPower.POWER_ID:
                case ThieveryPower.POWER_ID:
                    break;
                default:
                    throw new RuntimeException("Invalid power id : " + power.ID);
            }
            if (powerAI != null) {
                powers.add(powerAI);
            }
        }

        switch (monster.id) {
            case AcidSlime_L.ID:
                return new AcidSlimeLargeAI(health, block, powers, moveHistory, new CopyableRandom(),
                        monster);
            case AcidSlime_M.ID:
                return new AcidSlimeMediumAI(health, block, powers, moveHistory, new CopyableRandom(),
                        monster);
            case AcidSlime_S.ID:
                return new AcidSlimeSmallAI(health, block, powers, moveHistory, new CopyableRandom(),
                        monster);
            case Cultist.ID:
                return new CultistAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            case FungiBeast.ID:
                return new FungiBeastAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            case GremlinFat.ID:
                return new FatGremlinAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            case GremlinWarrior.ID:
                return new MadGremlinAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            case GremlinThief.ID:
                return new SneakyGremlinAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            case GremlinTsundere.ID:
                return new ShieldGremlinAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            case GremlinWizard.ID:
                return new WizardGremlinAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            case JawWorm.ID:
                return new JawWormAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            case LouseDefensive.ID:
                // TODO: Calculate bonus damage
                return new GreenLouseAI(health, block, powers, moveHistory, new CopyableRandom(), monster, 5);
            case LouseNormal.ID:
                // TODO: Calculate bonus damage
                return new RedLouseAI(health, block, powers, moveHistory, new CopyableRandom(), monster, 5);
            case SlaverBlue.ID:
                return new BlueSlaverAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            case SlaverRed.ID:
                return new RedSlaverAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            case SpikeSlime_L.ID:
                return new SpikeSlimeLargeAI(health, block, powers, moveHistory, new CopyableRandom(),
                        monster);
            case SpikeSlime_M.ID:
                return new SpikeSlimeMediumAI(health, block, powers, moveHistory, new CopyableRandom(),
                        monster);
            case SpikeSlime_S.ID:
                return new SpikeSlimeSmallAI(health, block, powers, moveHistory, new CopyableRandom(),
                        monster);
            case Looter.ID:
                return new LooterAI(health, block, powers, moveHistory, new CopyableRandom(), monster);
            default:
                throw new RuntimeException("Invalid Monster ID: " + monster.id);
        }
    }

    public static AbstractCardAI getCard(AbstractCard card) {
        switch (card.cardID) {
            case Anger.ID:
                return new AngerAI(card.upgraded);
            case Bash.ID:
                return new BashAI(card.upgraded);
            case BodySlam.ID:
                return new BodySlamAI(card.upgraded);
            case Clash.ID:
                return new ClashAI(card.upgraded);
            case Clothesline.ID:
                return new ClotheslineAI(card.upgraded);
            case Flex.ID:
                return new FlexAI(card.upgraded);
            case HeavyBlade.ID:
                return new HeavyBladeAI(card.upgraded);
            case IronWave.ID:
                return new IronWaveAI(card.upgraded);
            case PerfectedStrike.ID:
                return new PerfectedStrikeAI(card.upgraded);
            case PommelStrike.ID:
                return new PommelStrikeAI(card.upgraded);
            case ShrugItOff.ID:
                return new ShrugItOffAI(card.upgraded);
            case SwordBoomerang.ID:
                return new SwordBoomerangAI(card.upgraded);
            case ThunderClap.ID:
                return new ThunderclapAI(card.upgraded);
            case TwinStrike.ID:
                return new TwinStrikeAI(card.upgraded);
            case WildStrike.ID:
                return new WildStrikeAI(card.upgraded);
            case Defend_Red.ID:
                return new DefendAI(card.upgraded);
            case Strike_Red.ID:
                return new StrikeAI(card.upgraded);
            case Slimed.ID:
                return new SlimedAI(card.upgraded);
            case Cleave.ID:
                return new CleaveAI(card.upgraded);
            default:
                throw new RuntimeException("Invalid Card ID: " + card.cardID);
        }
    }
}
