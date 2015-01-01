package ee.ut.jaanjanno.stoverock.game.models

class Card(
	name: String,
	cost: Int,
	cardType: CardType) {

	override def toString(): String = {
		var typeStr: String = ""
		if (cardType.isInstanceOf[SpellCard])
			typeStr = "SpellCard"
		else
			typeStr = "MinionCard"
		typeStr + "{\n    Name: " + name + ", Cost: " + cost.toString() + "\n    " + cardType.toString() + "\n}"
	}
}

abstract class CardType(
	effect: List[Effect]) {

	override def toString(): String = {
		var str: String = "Effects{"
		for (e <- effect) {
			str += "\n        " + e.toString()
		}
		str + "\n    }"
	}
}
case class SpellCard(
	effect: List[Effect]) extends CardType(effect)
case class MinionCard(
	effect: List[Effect],
	health: Int,
	attack: Int,
	taunt: Boolean,
	minionType: MinionType) extends CardType(effect) {

	override def toString(): String = {
		super.toString() +
			"\n    Health: " + health.toString() +
			"\n    Attack: " + attack.toString() +
			"\n    Taunt: " + taunt.toString()
	}
}

abstract class MinionType
case class Beast() extends MinionType
case class Murloc() extends MinionType

abstract class Effect(eventEffect: List[EventEffect]) {

	override def toString(): String = {
		var str: String = "{"
		for (e <- eventEffect) {
			str += "\n            " + e.toString()
		}
		str + "\n        }"
	}
}
case class OnPlay(
	eventEffect: List[EventEffect]) extends Effect(eventEffect) {

	override def toString(): String = {
		"OnPlay" + super.toString()
	}
}
case class UntilDeath(
	eventEffect: List[EventEffect]) extends Effect(eventEffect) {

	override def toString(): String = {
		"UntilDeath" + super.toString()
	}
}
case class OnDamage(
	eventEffect: List[EventEffect]) extends Effect(eventEffect) {

	override def toString(): String = {
		"OnDamage" + super.toString()
	}
}
case class OnDeath(
	eventEffect: List[EventEffect]) extends Effect(eventEffect) {

	override def toString(): String = {
		"OnDeath" + super.toString()
	}
}

abstract class EventEffect
abstract class FilteredEventEffect(
	filter: List[Filter],
	creatureEffect: List[CreatureEffect]) extends EventEffect {
	
	override def toString(): String = {
		var str = "                Filters{\n"
		for(f <- filter) {
			str += "                    " + f.toString() + "\n"
		}
		str += "                }\n"
		str += "                CreatureEffects{\n"
		for(e <- creatureEffect) {
			str += "                    " + e.toString() + "\n"
		}
		str + "                }"
	}
}
case class All(
	filter: List[Filter],
	creatureEffect: List[CreatureEffect]) extends FilteredEventEffect(filter, creatureEffect) {
	
	override def toString(): String = {
		"All{\n" + super.toString() + "\n            }"
	}
}
case class Choose(
	filter: List[Filter],
	creatureEffect: List[CreatureEffect]) extends FilteredEventEffect(filter, creatureEffect) {
	
	override def toString(): String = {
		"Choose{\n" + super.toString() + "\n            }"
	}
}
case class Random(
	filter: List[Filter],
	creatureEffect: List[CreatureEffect]) extends FilteredEventEffect(filter, creatureEffect) {
	
	override def toString(): String = {
		"Random{\n" + super.toString() + "\n            }"
	}
}
case class DrawCard() extends EventEffect

abstract class CreatureEffect
case class Health(
	typeOf: EffType,
	health: Int) extends CreatureEffect
case class Attack(
	typeOf: EffType,
	attack: Int) extends CreatureEffect
case class Taunt(taunt: Boolean) extends CreatureEffect

abstract class EffType
case class Relative() extends EffType
case class Absolute() extends EffType

abstract class Filter
case class AnyCreature() extends Filter
case class AnyHero() extends Filter
case class AnyFriendly() extends Filter
case class Type(minionType: MinionType) extends Filter
case class Self() extends Filter
case class Not(filter: List[Filter]) extends Filter
case class Any(filter: List[Filter]) extends Filter