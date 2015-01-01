package ee.ut.jaanjanno.stoverock.game

import ee.ut.jaanjanno.stoverock.parser.Lexer
import ee.ut.jaanjanno.stoverock.parser.Parser

/**
 * Jaan Janno, 1. jaanuar 2015
 */

object Program {
	
	/*
	 * Loeb sisse mõned definitsioonid ja 
	 * tagastab ekraanile.
	 */
	
	def main(args: Array[String]) = {
		val file = scala.io.Source.fromFile("definitions.txt").mkString
		val lex = Lexer.lex(file)
		try {
		  val s = new Parser(lex).parse()
			for(c <- s) println(c)
		} catch {
		  case t: Throwable => {
		 	  t.printStackTrace()
		 	  println("Vigane fail.")
		  }
		}
		
	}
}