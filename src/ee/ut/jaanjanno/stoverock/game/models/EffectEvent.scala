package ee.ut.jaanjanno.stoverock.game.models

/*
 -- toime on kaardi võtmine või olendite mõjutamine --- vastavalt filtrile
EventEffect ::== "All" [Filter] [CreatureEffect] 
                             -- mõjutatake kõiki filtrile vastavaid olendeid
               | "Choose" [Filter] [CreatureEffect]       
                             -- mõjutatake üht kasutaja valitud filtrile vastavat olendeit
               | "Random" [Filter] [CreatureEffect]       
                             -- mõjutatake üht juhuslikku filtrile vastavat olendeit
               | "DrawCard"  -- toime olendi omanik võtab kaardi
 */

object EffectEventType extends Enumeration {
	type EffectEventType = Value
	val OnPlay = Value("OnPlay")
	val UntilDeath = Value("UntilDeath")
	val OnDamage = Value("OnDamage")
	val OnDeath = Value("OnDeath")
}
import EffectEventType.EffectEventType

class EffectEvent(eventType : EffectEventType, filter : List[EffectFilter], effect : List[EffectCreature]) {
	
}