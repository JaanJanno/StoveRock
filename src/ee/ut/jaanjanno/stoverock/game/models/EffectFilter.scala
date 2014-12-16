package ee.ut.jaanjanno.stoverock.game.models

import MinionType.MinionType

/*
-- filtri list on konjunktsioon, välja arvatud "Any" puhul
Filter ::== "AnyCreature"     -- olendid
          | "AnyHero"         -- kangelased
          | "AnyFriendly"     -- "omad" 
          | "Type" MinionType -- kindlat tüüpi olendid
          | "Self"            -- mõjutav olend ise
          | "Not" [Filter]    -- eitus
          | "Any" [Filter]    -- disjunktsioon: kui üks tingimus on taidetud
 */

object FilterType extends Enumeration {
	type FilterType = Value
	val AnyCreature = Value("AnyCreature")
	val AnyHero = Value("AnyHero")
	val AnyFriendly = Value("AnyFriendly")
	val Self = Value("Self")
	
	val Type = Value("Type")
	val Not = Value("Not")
	val Any = Value("Any")
}
import FilterType.FilterType

class EffectFilter(filterType : FilterType, minionType : MinionType, filters : List[EffectFilter]) {
	
}