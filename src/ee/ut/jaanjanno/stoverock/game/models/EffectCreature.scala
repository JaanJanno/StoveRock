package ee.ut.jaanjanno.stoverock.game.models

object CreatureEffectType extends Enumeration {
	type CreatureEffectType = Value
	val Relative = Value("Relative")
	val Absolute = Value("Absolute")
}
import CreatureEffectType.CreatureEffectType

abstract class AbstractEffectCreature {

	case class CreatureHealthEffect(
		effectType: CreatureAttackEffect,
		health: Int)
		extends AbstractEffectCreature

	case class CreatureAttackEffect(
		effectType: CreatureAttackEffect,
		attack: Int)
		extends AbstractEffectCreature

	case class CreatureTauntEffect(taunt: Boolean) extends AbstractEffectCreature

}