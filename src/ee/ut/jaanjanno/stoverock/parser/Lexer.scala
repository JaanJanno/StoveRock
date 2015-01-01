package ee.ut.jaanjanno.stoverock.parser

import java.util.regex.Pattern
import scala.collection.mutable.ListBuffer

/**
 * Töötleb sisendstringi lekseemideks
 */

object Lexer {

	def lex(in: String): List[LexerToken] = {
		val str = rmNl(rmParentheses(in))
		var index = 0
		val limit = str.length()

		val tokenList = new ListBuffer[LexerToken]
		while (index < limit) {
			str(index) match {
				case ' ' => ()
				case ',' => ()
				case '[' => tokenList.append(ListStart())
				case ']' => tokenList.append(ListEnd())
				case '(' => tokenList.append(TupleStart())
				case ')' => tokenList.append(TupleEnd())
				case '"' => {
					/*
					 * Jutumärkides sõne reegel.
					 */		
					val start = index
					index += 1
					while (str(index) != '"') {
						index += 1
					}
					tokenList.append(NameQuote(str.substring(start, index + 1)))
				}
				case _ => {
					if (str(index).isDigit || str(index) == '-') {
						/*
						 * Arvu reegel.
						 */
						val start = index
						if (str(index) == '-') {
							index += 1
						}
						while (str(index).isDigit) {
							index += 1
						}
						tokenList.append(Number(str.substring(start, index)))
						index -= 1
					}
					if (str(index).isLetter) {
						/*
						 * Sõne reegel.
						 */
						val start = index
						while (str(index).isLetter) {
							index += 1
						}
						tokenList.append(Name(str.substring(start, index)))
						index -= 1
					}
				}
			}
			index += 1
		}
		tokenList.append(EOF())
		tokenList.toList
	}
	
	/*
	 * Reavahetused pole grammatikas defineeritud, seega
	 * kustutan igaks juhuks.
	 */

	def rmNl(in: String): String = {
		in.replaceAll("\n", "")
	}
	
	/*
	 * Näites oli imelik sulgudesse pandud negatiivne number, mis grammatika
	 * järgi poleks tohtinud seal olla. Igaks juhuks eemaldan kõik sellised sulud.
	 */

	def rmParentheses(in: String): String = {
		val regex = "\\(\\-\\d+\\)"
		val p = Pattern.compile(regex)
		val m = p.matcher(in)
		val sb = new StringBuffer()
		while (m.find()) {
			val replacement = m.group().substring(1, m.group().length() - 1)
			m.appendReplacement(sb, replacement)
		}
		m.appendTail(sb)
		sb.toString()
	}
}