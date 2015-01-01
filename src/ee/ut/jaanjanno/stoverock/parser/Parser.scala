package ee.ut.jaanjanno.stoverock.parser

import java.util.regex.Pattern
import java.util.regex.Matcher
import scala.collection.mutable.ListBuffer
import ee.ut.jaanjanno.stoverock.game.models._

object Parser {
	
	def rmNl(in : String) : String = {
		return in.replaceAll("\n", "")
	}
	
	def rmParentheses(in : String) : String = {
		
		val regex = "\\(\\-\\d+\\)"
		val p = Pattern.compile(regex)
		val m = p.matcher(in)
		val sb = new StringBuffer()
		
		while (m.find()) {
		    val replacement = m.group().substring(1, m.group().length()-1)
		    m.appendReplacement(sb, replacement)
		}
		
		m.appendTail(sb)
		
		return sb.toString()
	}
	
	def doSplit(in : String) : List[String] = {
		val arr = in.split("\\(")
		val lis = new ListBuffer[String]
		for (line <- arr.slice(1, arr.length)) {
			lis.append(line.split("\\)")(0))
		}
		return lis.toList
	}
	
	def doParseObject(arg : String) : ParseObject = {
		val split = arg.split(",",3)
		new ParseObject(split(0).trim().substring(1, split(0).length()-1), split(1).trim().toInt, split(2).trim())
	}
	
	def parseCard(card : ParseObject) : Card = {
		null
	}

}

class ParseObject (
		name : String,
		cost : Int,
		cardType : String) {
	
	override def toString = {
		"ParseObject {\n  "+ name + "\n  " + cost.toString() + "\n  " + cardType + "\n}"
	}
	
}