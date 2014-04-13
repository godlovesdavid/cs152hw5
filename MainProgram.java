import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainProgram
{
	/**
	 * runs program, calling parser to parse all content and then and calls content printer to print contents.
	 * @param args first argument being the name of file to read in
	 */
	public static void main(String[] args)
	{
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
			"\\(" + word + "|" + number + "|" + bool + "|" + symbol + "\\)";
		String element =
			word + "|" + number + "|" + bool + "|" + symbol + "|" + list;
		String keyword =
			"and|begin|begin0|break-var|case|cond|cycle|define|delay|delay-list-cons|do|else|extend-syntax|for|freeze|if|lambda|let|letrec|let*|macro|object-maker|or|quote|repeat|safe-letrec|set!|stream-cons|variable-case|while|wrap";
		String token = operator + "|[^\r\n\t ]+?(?=" + symbol + "|\r|\n|\t| |$)";

		Matcher matcher =
			Pattern.compile(token).matcher(
				"testing123()testing now now now Iasdfsdf22");
		System.out.println(matcher.group());
	}

}
