package frontend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner
{
	/*
	* regular expressions
	*/
	//basic characters
	static String LETTER = "[A-Za-z]";
	static String DIGIT = "[0-9]";
	static String UNSIGNED_INT = DIGIT + "+";
	static String BRACKET = "[\\[\\]\\(\\)]";
	static String QUOTE = "'";
	static String WORD = LETTER + "(" + LETTER + "|" + DIGIT + "|\\-|\\?)+";

	//token types
	static String SYMBOL = "[a-zA-Z\\+\\-\\.\\*\\/<=>!?:\\$%_&~^]+";
	static String SPECIAL_SYMBOL = "[\\(\\)\\[\\]\\{\\};,\\.\"'#\\\\]";
	static String NUMBER = UNSIGNED_INT + "(\\." + UNSIGNED_INT + ")?";
	static String CHARACTER = "#\\.";
	static String STRING = "\"[^\"]+\"";
	static String BOOLEAN = "#[tTfF]";
	static String KEYWORD =
		"and|begin|begin0|break-var|case|cond|cycle|define|delay|delay-list-cons|do|else|extend-syntax|for|freeze|if|lambda|let|letrec|let\\*|macro|object-maker|or|quote|repeat|safe-letrec|set!|stream-cons|variable-case|while|wrap";

	//token
	static String TOKEN = "(?<!;[^\n]{0,1000})(" + BRACKET + "|" + QUOTE
		+ "|[^ \t\r\n;]+?(?=" + BRACKET + "| |\t|\r|\n|;|$))"; //skip comments, then match bracket, or non-delimiter characters until delimiter

	Matcher matcher;

	public Scanner(String sourcecode)
	{
		matcher = Pattern.compile(TOKEN).matcher(sourcecode);
	}

	/**
	 * give next token from where the scanner is at
	 * @return
	 */
	public Token scanForNextToken()
	{
		if (!matcher.find())
			return null;

		String tokenstring = matcher.group();
		String type = null;
		if (Pattern.compile(SPECIAL_SYMBOL).matcher(tokenstring).matches())
			type = "specialsymbol";
		else if (Pattern.compile(KEYWORD).matcher(tokenstring).matches())
			type = "keyword";
		else if (Pattern.compile(SYMBOL).matcher(tokenstring).matches())
			type = "symbol";
		else if (Pattern.compile(WORD).matcher(tokenstring).matches())
			type = "word";
		else if (Pattern.compile(NUMBER).matcher(tokenstring).matches())
			type = "number";
		else if (Pattern.compile(BOOLEAN).matcher(tokenstring).matches())
			type = "boolean";
		else if (Pattern.compile(CHARACTER).matcher(tokenstring).matches())
			type = "character";
		else if (Pattern.compile(STRING).matcher(tokenstring).matches())
			type = "string";
		else
			type = "unknown";

		return new Token(tokenstring, type);
	}
}
