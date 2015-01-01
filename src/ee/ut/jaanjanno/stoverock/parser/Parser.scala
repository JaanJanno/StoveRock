package ee.ut.jaanjanno.stoverock.parser

import java.util.regex.Pattern
import java.util.regex.Matcher
import scala.collection.mutable.ListBuffer
import ee.ut.jaanjanno.stoverock.game.models._

/*
 * Parsib lekseemide järjendi kaartideks.
 */

class Parser(in: List[LexerToken]) {

	var index: Int = 0

	def parse(): List[Card] = {
		index = 0
		parseCardList().toList
	}

	def pop(): LexerToken = {
		index += 1
		in(index - 1)
	}

	def peek(): LexerToken = {
		in(index)
	}
	
	//Kaartide listi reegel.
	def parseCardList(): ListBuffer[Card] = {
		val lis = new ListBuffer[Card]
		if (pop() != ListStart()) {
			throw new Exception
		}
		while (peek() == TupleStart()) {
			val card = parseCard()
			lis.append(card)
		}
		if (pop() != ListEnd()) {
			throw new Exception
		}
		if (pop() != EOF()) {
			throw new Exception
		}
		lis
	}
	
	//Kaardi reegel.
	def parseCard(): Card = {
		if (pop() != TupleStart()) {
			throw new Exception
		}
		val name = pop().asInstanceOf[NameQuote].content()
		val cost = pop().asInstanceOf[Number].content()
		val cardTypeName = pop().toString()
		val effectList: ListBuffer[Effect] = parseEffectList()
		val cardType: CardType = cardTypeName match {
			case "SpellCard" => SpellCard(effectList.toList)
			case "MinionCard" => {
				val health = pop().asInstanceOf[Number].content()
				val attack = pop().asInstanceOf[Number].content()
				val taunt = pop().asInstanceOf[Name].asBool()
				val minionType = parseMinionType()
				MinionCard(effectList.toList, health, attack, taunt, minionType)
			}
			case _ => throw new Exception
		}

		if (pop() != TupleEnd()) {
			throw new Exception
		}
		return new Card(name, cost, cardType)
	}
	
	//Efektide listi reegel.
	def parseEffectList(): ListBuffer[Effect] = {
		val lis = new ListBuffer[Effect]
		if (pop() != ListStart()) {
			throw new Exception
		}
		while (peek().isInstanceOf[Name]) {
			val effect = parseEffect()
			lis.append(effect)
		}
		if (pop() != ListEnd()) {
			throw new Exception
		}
		lis
	}
	
	//Efekti reegel.
	def parseEffect(): Effect = {
		val header = pop().asInstanceOf[Name].string
		val eventEffect: List[EventEffect] = parseEventEffectList()

		header match {
			case "OnPlay" => OnPlay(eventEffect)
			case "UntilDeath" => UntilDeath(eventEffect)
			case "OnDamage" => OnDamage(eventEffect)
			case "OnDeath" => OnDeath(eventEffect)
			case _ => throw new Exception
		}
	}
	
	//Event Efektide listi reegel.
	def parseEventEffectList(): List[EventEffect] = {
		val lis = new ListBuffer[EventEffect]
		if (pop() != ListStart()) {
			throw new Exception
		}
		while (peek().isInstanceOf[Name]) {
			val effect = parseEventEffect()
			lis.append(effect)
		}
		if (pop() != ListEnd()) {
			throw new Exception
		}
		lis.toList
	}
	
	//Event Efekti reegel.
	def parseEventEffect(): EventEffect = {
		val header = pop().asInstanceOf[Name].string
		header match {
			case "DrawCard" => DrawCard()
			case _ => {
				val filter: List[Filter] = parseFilterList()
				val creatureEffect: List[CreatureEffect] = parseCreatureEffectList()
				header match {
					case "All" => All(filter, creatureEffect)
					case "Choose" => Choose(filter, creatureEffect)
					case "Random" => Random(filter, creatureEffect)
				}
			}
		}
	}
	
	//Creature Efektide listi reegel.
	def parseCreatureEffectList(): List[CreatureEffect] = {
		val lis = new ListBuffer[CreatureEffect]
		if (pop() != ListStart()) {
			throw new Exception
		}
		while (peek().isInstanceOf[Name]) {
			val effect = parseCreatureEffect()
			lis.append(effect)
		}
		if (pop() != ListEnd()) {
			throw new Exception
		}
		lis.toList
	}
	
	//Creature Efekti reegel.
	def parseCreatureEffect(): CreatureEffect = {
		val header = pop().asInstanceOf[Name].string
		header match {
			case "Taunt" => {
				val taunt = pop().asInstanceOf[Name].asBool()
				Taunt(taunt)
			}
			case _ => {
				val typeOf: EffType = parseEffType()
				val arg = pop().asInstanceOf[Number].content()
				header match {
					case "Health" => {
						Health(typeOf, arg)
					}
					case "Attack" => {
						Attack(typeOf, arg)
					}
				}
			}
		}
	}
	
	//Efekti tüübi reegel.
	def parseEffType(): EffType = {
		val header = pop().asInstanceOf[Name].string
		header match {
			case "Relative" => Relative()
			case "Absolute" => Absolute()
			case _ => throw new Exception
		}
	}
	
	//Filtrite listi reegel.
	def parseFilterList(): List[Filter] = {
		val lis = new ListBuffer[Filter]
		if (pop() != ListStart()) {
			throw new Exception
		}
		while (peek().isInstanceOf[Name]) {
			val filter = parseFilter()
			lis.append(filter)
		}
		if (pop() != ListEnd()) {
			throw new Exception
		}
		lis.toList
	}

	//Filtri reegel.
	def parseFilter(): Filter = {
		val header = pop().asInstanceOf[Name].string
		header match {
			case "AnyCreature" => AnyCreature()
			case "AnyHero" => AnyHero()
			case "AnyFriendly" => AnyFriendly()
			case "Self" => Self()
			case "Type" => {
				val minionType = parseMinionType()
				Type(minionType)
			}
			case "Not" => {
				val subFilter = parseFilterList()
				Not(subFilter)
			}
			case "Any" => {
				val subFilter = parseFilterList()
				Any(subFilter)
			}
		}
	}
	
	//Minion'i tüübi reegel.
	def parseMinionType(): MinionType = {
		val header = pop().asInstanceOf[Name].string
		header match {
			case "Nothing" => null
			case "Beast" => Beast()
			case "Murloc" => Murloc()
			case "Just" => {
				val typeName = pop().asInstanceOf[Name].string
				typeName match {
					case "Beast" => Beast()
					case "Murloc" => Murloc()
					case _ => throw new Exception
				}
			}
		}
	}
}