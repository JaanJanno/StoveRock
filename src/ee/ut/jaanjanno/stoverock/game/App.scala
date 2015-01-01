package ee.ut.jaanjanno.stoverock.game

import ee.ut.jaanjanno.stoverock.game.models._
import scala.collection.mutable.ListBuffer
import ee.ut.jaanjanno.stoverock.parser._

object App {

	def main(args: Array[String]) = {
		var t = new ListBuffer[Effect]
		
		val effectType = EffectType.OnDeath
		var effectEvent = new ListBuffer[AbstractEffectEvent]
		t.append(new Effect(effectType, effectEvent.toList))
		
		var x = new CardSpell("asd", 1, t.toList)
		//println(x)
		
		//println(Constants.file)
		//println()
		//println(Constants.file.replaceAll("\n", ""))
		//println(Constants.file.replaceAll("\\(\\-", "-"))
		val a0 = Parser.rmNl(Constants.file)
		val a = Parser.rmParentheses(a0)
		val b = Parser.doSplit(a)
		for(c <- b) {
			println(Parser.doParseObject(c))
		}
	}
	
}