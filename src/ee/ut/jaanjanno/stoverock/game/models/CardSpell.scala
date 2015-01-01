package ee.ut.jaanjanno.stoverock.game.models

/*
 * -- fail on list kolmikutest
File ::== [(Name, Cost, CardType)] 
Name ::== <String>
Cost ::== <Int>
 */

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

abstract class Card

class CardSpell(
	name: String,
	cost: Int,
	effect: List[Effect])
	extends Card {

	override def toString = {
		name + cost.toString() + effect.toString()
	}

}