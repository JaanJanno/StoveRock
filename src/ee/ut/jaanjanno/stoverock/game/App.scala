package ee.ut.jaanjanno.stoverock.game

import ee.ut.jaanjanno.stoverock.game.models._
import scala.collection.mutable.ListBuffer

object App {

	def main(args: Array[String]) = {
		println("tere")
		var t = new ListBuffer[Effect]
		
		val effectType = EffectType.OnDeath
		var effectEvent = new ListBuffer[AbstractEffectEvent]
		t.append(new Effect(effectType, effectEvent.toList))
		
		var a = new SpellCard("asd", 1, t.toList)
		println(a)
	}
	
}