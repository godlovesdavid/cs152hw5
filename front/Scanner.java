package front;

import java.util.Queue;
import java.util.LinkedList;

public class Scanner
{
	/*
	 * regular expressions
	 */
	String letter = "[A-Za-z]";
	String digit = "[0-9]";
	String unsignedint = digit + "+";
	String number = unsignedint + "(\\." + unsignedint + ")?";
	String character = "#\\.";
	String string = "\"[^\"]+\"";
	String bool = "#[tf]";
	String word = letter + "(" + letter + "|" + digit + "|\\-|\\?)+";
	String symbol = "[\\(\\)\\[\\]\\{\\};,\\.\"\'#\\\\]";
	String operator =
		"(\\+|\\-|\\*|/|=|<|<=|=|>=|>|>>=|<<=|\\.|,|:|\\(|\\)|\\[|\\]|\\{|\\}|'|\")";
	String list =
		"\\(" + word + "|" + number + "|" + bool + "|" + symbol + "\\)"; //has to include an element, but element has list, so i donno how to do this
	String element =
		word + "|" + number + "|" + bool + "|" + symbol + "|" + list;
	String keyword =
		"and|begin|begin0|break-var|case|cond|cycle|define|delay|delay-list-cons|do|else|extend-syntax|for|freeze|if|lambda|let|letrec|let*|macro|object-maker|or|quote|repeat|safe-letrec|set!|stream-cons|variable-case|while|wrap";
	String token = operator + "|[^\r\n\t ]+?(?=" + symbol + "|\r|\n|\t| |$)";

	/**
	 * make tokens out of source file
	 * @param src
	 * @return
	 */
	public Queue<Token> tokenize(String src)
	{
		Queue queue = new LinkedList();

		return queue;
	}
}
