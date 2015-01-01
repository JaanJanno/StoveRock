package ee.ut.jaanjanno.stoverock.parser

/*
 * Lekseemid, milleks sisendsõne teisendatakse.
 * Selliseid on mugavam parsida.
 */

abstract class LexerToken(string: String) {

	override def toString(): String = {
		string
	}
}
case class Name(string: String) extends LexerToken(string) {

	def asBool(): Boolean = string.toBoolean
}
case class NameQuote(string: String) extends LexerToken(string) {

	def content(): String = string.substring(1, string.length() - 1)
}
case class Number(string: String) extends LexerToken(string) {

	def content(): Int = string.toInt
}
case class ListStart() extends LexerToken("[")
case class ListEnd() extends LexerToken("]")
case class TupleStart() extends LexerToken("(")
case class TupleEnd() extends LexerToken(")")
case class EOF() extends LexerToken("$")