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
}
import FilterType.FilterType

abstract class AbstractEffectFilter {
	
	//def filterCards(cards : List[Card]) : List[Card]
	
	case class EffectFilter(filterType : FilterType) extends AbstractEffectFilter {
		
	}
	
	case class EffectFilterByType(minionType : MinionType) extends AbstractEffectFilter {
		
	}
	
	case class EffectFilterByNot(filters : List[EffectFilter]) extends AbstractEffectFilter {
		
	}
	
	case class EffectFilterByAny(filters : List[EffectFilter]) extends AbstractEffectFilter {
		
	}
	
}

