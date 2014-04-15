package frontend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	String specialsymbol = "[\\(\\)\\[\\]\\{\\};,\\.\"\'#\\\\]";
	String operator =
		"(\\+|\\-|\\*|/|=|<|<=|=|>=|>|>>=|<<=|\\.|,|:|\\(|\\)|\\[|\\]|\\{|\\}|'|\")";
	String element = word + "|" + number + "|" + bool + "|" + specialsymbol;
	String list = "\\((" + element + "*|(\\(" + element + "*\\))?" + ")\\)";
	String keyword =
		"and|begin|begin0|break-var|case|cond|cycle|define|delay|delay-list-cons|do|else|extend-syntax|for|freeze|if|lambda|let|letrec|let*|macro|object-maker|or|quote|repeat|safe-letrec|set!|stream-cons|variable-case|while|wrap";
	String comment = ";.*?\r?\n";
	String token = "(?<!;[^\n]{0,1000})(" + operator + "|[^ \t\r\n;]+?(?="
		+ specialsymbol + "| |\t|\r|\n|;|$))"; //operator or (not space/CR/LF/comment symbol)+ until (space/CR/LF/comment symbol)
	//	String tokendelimiter = //comment or space/tab/newline or ending a symbol or starting a symbol
	//		comment + "|( |\t|\r|\n)+|(?<=" + specialsymbol + ")(?!" + specialsymbol
	//			+ ")|(?<!" + specialsymbol + ")(?=" + specialsymbol + ")";

	Matcher matcher;

	public Scanner(String sourcecode)
	{
		matcher = Pattern.compile(token).matcher(sourcecode);
	}

	/**
	 * give next token
	 * @return
	 */
	public Token scanForNextToken()
	{
		if (!matcher.find())
			return null;

		String tokenstring = matcher.group();
		String type = null;
		if (Pattern.compile(unsignedint).matcher(tokenstring).matches())
			type = "unsignedint";
		else if (Pattern.compile(number).matcher(tokenstring).matches())
			type = "number";
		else if (Pattern.compile(character).matcher(tokenstring).matches())
			type = "character";
		else if (Pattern.compile(string).matcher(tokenstring).matches())
			type = "string";
		else if (Pattern.compile(bool).matcher(tokenstring).matches())
			type = "bool";
		else if (Pattern.compile(operator).matcher(tokenstring).matches())
			type = "operator";
		else if (Pattern.compile(list).matcher(tokenstring).matches())
			type = "list";
		else if (Pattern.compile(keyword).matcher(tokenstring).matches())
			type = "keyword";
		else if (Pattern.compile(element).matcher(tokenstring).matches())
			type = "element";
		else
			type = "unknown";

		return new Token(tokenstring, type);
	}
}
