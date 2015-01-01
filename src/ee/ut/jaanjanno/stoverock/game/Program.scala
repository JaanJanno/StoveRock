package ee.ut.jaanjanno.stoverock.game

import ee.ut.jaanjanno.stoverock.parser.Lexer
import ee.ut.jaanjanno.stoverock.parser.Parser

object Program {
	
	def main(args: Array[String]) = {
		val file = scala.io.Source.fromFile("definitions.txt").mkString
		val lex = Lexer.lex(file)
		val s = new Parser(lex).parse()
		for(c <- s) println(c)
	}
}