package frontend;

import intermediate.Lister;

import java.util.regex.Pattern;

public class Parser
{
	Scanner scanner;
	Lister lister;

	/*
	 * regular expressions
	 */
	//	static String ELEMENT = Scanner.WORD + "|" + Scanner.NUMBER + "|"
	//		+ Scanner.BOOLEAN + "|" + Scanner.SPECIAL_SYMBOL;
	//	static String LIST = "\\((" + ELEMENT + "+|(\\(" + ELEMENT + "+\\)*)?"
	//		+ ")\\)";

	public Parser()
	{
		lister = new Lister();
	}

	/**
	 * parse code, put in lists
	 * @param file file with code
	 */
	public void parse(String code)
	{
		scanner = new Scanner(code);

		Token token;

		//for each token
		while ((token = scanner.scanForNextToken()) != null)
		{
			if (isOpenParenthesis(token))
				lister.startList();
			else if (isClosedParenthesis(token))
				lister.endList();
			else
				//list element case
				lister.addElement(token, isScopeMaking(token));

			//print token
			System.out.println(token.string + "\t\t\t" + token.type);
		}
	}

	boolean isOpenParenthesis(Token token)
	{
		return token.string.equals("(") || token.string.equals("[");
	}

	boolean isClosedParenthesis(Token token)
	{
		return token.string.equals(")") || token.string.equals("]");
	}

	boolean isScopeMaking(Token token)
	{
		return Pattern.compile("lambda|let|letrec|let\\*").matcher(token.string)
			.matches();
	}
}
