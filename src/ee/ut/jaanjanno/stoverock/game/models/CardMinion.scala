package ee.ut.jaanjanno.stoverock.game.models

/*
 * -- on kahte tüüpi kaarte: olendid ja loitsud
CardType ::== "MinionCard" [Effect] Health Attack Taunt ("Just" MinionType | "Nothing")
           |  "SpellCard" [Effect]
-- elupunktide arv
Health ::== <Int>
-- ründepunktide arv
Attack ::== <Int>
-- mõnitav olend
Taunt ::== <Bool>
 */

object MinionType extends Enumeration {
	type MinionType = Value
	val Murloc = Value("Murloc")
	val Beast = Value("Beast")
	val Nothing = Value("Nothing")
}
import MinionType.MinionType

class CardMinion(
	name: String,
	cost: Int,
	effect: List[Effect],
	var health: Int,
	var attack: Int,
	taunt: Boolean,
	minionType: MinionType) extends SpellCard(name, cost, effect) {
}
