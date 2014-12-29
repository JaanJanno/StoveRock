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
	val All = Value("All")
	val Choose = Value("Choose")
	val Random = Value("Random")

	val DrawCard = Value("DrawCard")
}
import EffectEventType.EffectEventType

abstract class AbstractEffectEvent() {

	case class EffectEvent(eventType: EffectEventType, filter: List[AbstractEffectFilter], effect: List[AbstractEffectCreature]) extends AbstractEffectEvent;

	case class DrawCardEventEffect() extends AbstractEffectEvent;

}