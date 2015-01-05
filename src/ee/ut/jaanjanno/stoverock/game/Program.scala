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

  def main(args: Array[String]) = {
    val file = scala.io.Source.fromFile("definitions.txt").mkString
    val lex = Lexer.lex(file)
    try {
      val s = new Parser(lex).parse()
      //for (c <- s) println(c)

      startGame(s)
    } catch {
      case t: Throwable => {
        t.printStackTrace()
        println("Vigane fail.")
      }
    }

  }

  def startGame(deck: List[Card]) {
    val limit = deck.size

    val redDeck: ListBuffer[Int] = List.range(0, limit).to[ListBuffer]
    val bluDeck: ListBuffer[Int] = List.range(0, limit).to[ListBuffer]

    val redHand: ListBuffer[Int] = new ListBuffer[Int]
    val bluHand: ListBuffer[Int] = new ListBuffer[Int]

    val redGame: ListBuffer[CardOwnership] = new ListBuffer[CardOwnership]
    val bluGame: ListBuffer[CardOwnership] = new ListBuffer[CardOwnership]

    while (run) {
      drawCard(redDeck, redHand)
      doTurn(redHand, redGame, bluGame)
      drawCard(bluDeck, bluHand)
      doTurn(bluHand, bluGame, redGame)
      run = false
    }
  }

  def doTurn(
    myHand: ListBuffer[Int],
    myGame: ListBuffer[CardOwnership],
    enemyGame: ListBuffer[CardOwnership]) = {

    for (card <- myHand) println(card)
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
}

class CardOwnership(
  cardId: Int,
  deck: List[Card]) {

  val effects: ListBuffer[CreatureEffect] = new ListBuffer[CreatureEffect]

  var healthDisplacement: Int = 0
  var attackDisplacement: Int = 0

  def getType(): Card =
    deck(cardId)

  def getName(): String =
    getType().name

  def getHealth(): Int =
    getType().cardType.asInstanceOf[MinionCard].health - healthDisplacement

  def getAttack(): Int =
    getType().cardType.asInstanceOf[MinionCard].attack - attackDisplacement

  def getTaunt(): Boolean =
    getType().cardType.asInstanceOf[MinionCard].taunt

}