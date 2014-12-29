package ee.ut.jaanjanno.stoverock.game.models

/*
   Effect ::== "OnPlay"  [EventEffect]  -- effekt kaardi käimisel
         |  "UntilDeath" [EventEffect]  -- effekt mis kestab välja käimisest kuni olendi surmani
         |  "OnDamage"   [EventEffect]  -- effekt mis tehakse olendi vigastamisel
         |  "OnDeath"    [EventEffect]  -- effekt mis tehakse olendi tapmisel (elupunktid <= 0)
*/

object EffectType extends Enumeration {
	type EffectType = Value
	val OnPlay = Value("OnPlay")
	val UntilDeath = Value("UntilDeath")
	val OnDamage = Value("OnDamage")
	val OnDeath = Value("OnDeath")
}
import EffectType.EffectType

class Effect(
		effectType : EffectType,
		effectEvent : List[AbstractEffectEvent]) {

}