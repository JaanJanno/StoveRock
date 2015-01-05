package ee.ut.jaanjanno.stoverock.game

import ee.ut.jaanjanno.stoverock.parser.Lexer
import ee.ut.jaanjanno.stoverock.parser.Parser
import ee.ut.jaanjanno.stoverock.game.models._
import scala.collection.mutable.ListBuffer

/**
 * Jaan Janno, 1. jaanuar 2015
 */

object Program {

	/*
	 * Loeb sisse mõned definitsioonid ja 
	 * tagastab ekraanile.
	 */

	var run = true
	var actionPoints = 1

	var redLife = 30
	var bluLife = 30

	def main(args: Array[String]) = {
		val file = scala.io.Source.fromFile("definitions.txt").mkString
		val lex = Lexer.lex(file)
		try {
			val s = new Parser(lex).parse()

			startGame(s)
			if (redLife > bluLife)
				println("Red won!")
			else if (redLife < bluLife)
				println("Blue won!")
			else
				println("Draw!")
		} catch {
			case t: Throwable => {
				t.printStackTrace()
				println("Error.")
			}
		}

	}

	def startGame(deck: List[Card]) {
		val limit = deck.size

		val redDeck: ListBuffer[Int] = List.range(0, limit).to[ListBuffer]
		val bluDeck: ListBuffer[Int] = List.range(0, limit).to[ListBuffer]

		val redHand = new ListBuffer[Int]
		val bluHand = new ListBuffer[Int]

		val redGame = new ListBuffer[CardOwnership]
		val bluGame = new ListBuffer[CardOwnership]

		while (run) {
			println("\n------------------------\nRed player's turn:")
			if (!drawCard(redDeck, redHand))
				redLife -= 10

			doTurn(deck, redHand, redGame, bluGame, true)

			println("\n------------------------\nBlue player's turn:")
			if (!drawCard(bluDeck, bluHand))
				bluLife -= 10
			doTurn(deck, bluHand, bluGame, redGame, false)

			if(redLife < 1 || bluLife < 1)
				return
			actionPoints += 1
		}
	}

	def doTurn(
		deck: List[Card],
		myHand: ListBuffer[Int],
		myGame: ListBuffer[CardOwnership],
		enemyGame: ListBuffer[CardOwnership],
		red: Boolean) = {

		println("\nCurrent action points: " + actionPoints.toString())

		println("Blue lives: " + bluLife.toString())
		println("Red lives: " + redLife.toString())

		println("\nCurrent enemy cards on table: ")
		echoCards(enemyGame)

		println("\nCurrent cards on table: ")
		echoCards(myGame)

		var points = actionPoints
		var i = -1
		while (i != 0) {
			println("\nCurrent cards in hand: ")
			echoHand(deck, myHand)

			println("\nChoose card number to play: (0 for none)")
			i = askNumber(0, myHand.length)
			if (i != 0) {
				val card = deck(myHand(i - 1))
				val cost = card.cost
				if (cost <= points) {
					if (card.cardType.isInstanceOf[MinionCard])
						playCard(deck, i - 1, myHand, myGame, red)
					else {
						// TODO
						myHand.remove(i - 1)
					}
				} else {
					println("Too expensive move! Coose another card.")
				}
			}
		}
	}

	def drawCard(
		myDeck: ListBuffer[Int],
		myHand: ListBuffer[Int]): Boolean = {
		if (myDeck.length > 0) {
			val r = scala.util.Random.nextInt(myDeck.length)
			myDeck.remove(r)
			myHand.append(r)
			true
		} else false
	}

	def playCard(
		deck: List[Card],
		index: Int,
		myHand: ListBuffer[Int],
		myGame: ListBuffer[CardOwnership],
		red: Boolean) = {

		val card = deck(myHand(index))
		myHand.remove(index)
		val own = new CardOwnership(card, red)
		myGame.append(own)
	}

	def echoCards(game: ListBuffer[CardOwnership]) = {
		for (card <- game) println(card.toString())
	}

	def echoHand(
		deck: List[Card],
		myHand: ListBuffer[Int]) = {

		for (i <- 0 to myHand.length - 1) {
			val card = deck(myHand(i))
			println((i + 1).toString() + " = " + card.toString())
		}
	}

	def askNumber(min: Int, max: Int) = {
		var i = -1
		while (i < min || i > max) {
			try {
				i = readInt()
				if (i < min || i > max)
					println("Wrong number, try again!")
			} catch {
				case t: Throwable => println("Bad input format. Try again!")
			}
		}
		i
	}
}

class CardOwnership(
	card: Card,
	val owner: Boolean) {

	val effects: ListBuffer[CreatureEffect] = new ListBuffer[CreatureEffect]

	var healthDisplacement: Int = 0
	var attackDisplacement: Int = 0

	def getType(): MinionCard = card.cardType.asInstanceOf[MinionCard]

	def getName(): String = card.name

	def getHealth(): Int =
		getType().health + healthDisplacement

	def getAttack(): Int =
		getType().attack + attackDisplacement

	def getTaunt(): Boolean =
		getType().taunt

	def getOwnString() = {
		if (owner == true)
			"Red"
		else
			"Blue"
	}

	override def toString(): String = {
		var str = getOwnString + " Card{\n    Name: " + getName() + "\n    " + getType().toDefString(getHealth(), getAttack(), getTaunt()) + "\n}"
		str
	}
}