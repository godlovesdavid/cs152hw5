package frontend;

import intermediate.ListMaker;
import intermediate.Node;
import intermediate.SymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Parser
{
	static String SCOPE_MAKERS = "lambda|let|letrec|let\\*";
	//	static String ELEMENT = Scanner.WORD + "|" + Scanner.NUMBER + "|"
	//		+ Scanner.BOOLEAN + "|" + Scanner.SPECIAL_SYMBOL;
	//	static String LIST = "\\((" + ELEMENT + "+|(\\(" + ELEMENT + "+\\)*)?"
	//		+ ")\\)";

	Scanner scanner;
	Stack<SymbolTable> scopestack;
	public List<Node> listrootnodes; //top level list root nodes
	ListMaker listmaker;
	SymbolTable toplevelsymbols;
	static SymbolTable GLOBAL_SYMBOLS;
	static
	{
		GLOBAL_SYMBOLS = new SymbolTable();
		GLOBAL_SYMBOLS.put("exact->inexact", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("inexact->exact", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("*", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("+", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("-", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("/", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("<", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("<=x", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("=", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put(">", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put(">=", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("abs", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("acos", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("append", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("apply", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("asin", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("assoc", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("assq", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("assv", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("atan", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("boolean?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("call-with-current-continuation",
			new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("call-with-input-file", new HashMap<String, Object>());
		GLOBAL_SYMBOLS
			.put("call-with-output-file", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("car", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("cdr", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("ceiling", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char->integer", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-alphabetic?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(char-ci<=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-ci", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-ci=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-ci>=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-ci>?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-downcase", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-lower-case?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-numeric?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-upcase", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-upper-case?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-whitespace?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(char<=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char>=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char>?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("char?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("close-input-port", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("close-output-port", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("complex?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("cons", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("cos", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(current-input-port)", new HashMap<String, Object>());
		GLOBAL_SYMBOLS
			.put("(current-output-port)", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("define", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("display", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(eof-object?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(eq?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("equal?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(eqv?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(eval", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("even?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(exact?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("exp", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("expt", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("floor", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("for-each", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("force", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("gcd", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(inexact?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(input-port?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(integer->char", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("interaction-environment",
			new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("integer?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("lcm", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("length", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("list", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(list->string", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(list->vector", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("(list-ref", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("list-tailL", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("list?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("load", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("log", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("macroexpand", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("make-string", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("make-vector", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("map", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("max", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("member?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("memq", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("memv", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("min", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("modulo", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("negative?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("newline)", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("not", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("null-environment", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("null?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("number->string", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("number?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("odd?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("open-input-file", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("open-output-file", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("output-port?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("pair?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("peek-char", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("positive?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("procedure?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("quotient", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("rational?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("read", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("read-char", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("real?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("remainder", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("reverse", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("round", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("scheme-report-environment",
			new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("set-car!", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("set-cdr!", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("sin", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("sqrt", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string->list", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string->number", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string->symbol", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-append", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ci<=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ci", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ci=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ci>=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ci>?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-copy", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-fill!", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-length", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ref", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-set!", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string<=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string>=?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string>?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("string?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("substring", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("symbol->string", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("symbol?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("tan", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("truncate", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector->list", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector-fill!", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector-length", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector-ref", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector-set!", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("write", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("write-char", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("zero?", new HashMap<String, Object>());
	}
	boolean quotemode;
	int quotemodeunmatchedparentheses;

	public Parser()
	{
		scopestack = new Stack<SymbolTable>();
		listrootnodes = new ArrayList<Node>();
		listmaker = new ListMaker();
		toplevelsymbols = new SymbolTable();
	}

	/**
	 * parses the code, puts in tree list
	 * @param file file with code
	 */
	public void parse(String code)
	{
		scanner = new Scanner(code);

		Token token;
		SymbolTable currenttable = null;

		//for each token
		while ((token = scanner.scanForNextToken()) != null)
		{
			//case start of top level list
			if (listmaker.isAtTopLevel() && isOpenParenthesis(token))
			{
				scopestack.clear();
				scopestack.push(GLOBAL_SYMBOLS);
				scopestack.push(currenttable = toplevelsymbols);
				listmaker.startList(toplevelsymbols);
				listrootnodes.add(listmaker.giveRoot());
			}
			//case not top level
			else
			{
				//scope making--push new symbol table
				if (Pattern.compile(SCOPE_MAKERS).matcher(token.string).matches())
				{
					scopestack.push(currenttable = new SymbolTable());

					listmaker.addElement(token, currenttable);
				}

				//sublist-starting
				else if (isOpenParenthesis(token))
				{
					listmaker.startSublist();

					if (quotemode)
					{
						quotemodeunmatchedparentheses++;
					}
				}
				//list-ending
				else if (isClosedParenthesis(token))
				{
					listmaker.endList();

					if (quotemode)
					{
						quotemodeunmatchedparentheses--;
						if (quotemodeunmatchedparentheses == 0)
						{
							listmaker.endList();
							quotemode = false;
						}
					}
				}
				//quote
				else if (token.string.equals("'"))
				{
					quotemode = true;
					quotemodeunmatchedparentheses = 0;
					listmaker.startSublist();
					listmaker.addElement(new Token("quote", "word"));
				}
				//list element case
				else
				{
					listmaker.addElement(token);

					if (quotemode && quotemodeunmatchedparentheses == 0)
					{
						listmaker.endList();
						quotemode = false;
					}

					//if token is symbol, add to scope
					if (!stackContainsToken(token) && token.type == "symbol")
					{
						currenttable.put(token.string, new HashMap<String, Object>());
					}
				}
			}
			//print token
			System.out.println(token.string + "\t\t\t" + token.type);
		}
	}

	boolean isOpenParenthesis(Token token)
	{
		return token.string.equals("(") || token.string.equals("[")
			|| token.string.equals("{");
	}

	boolean isClosedParenthesis(Token token)
	{
		return token.string.equals(")") || token.string.equals("]")
			|| token.string.equals("}");
	}

	/**
	 * tests if scope stack contains token's string
	 * @param token
	 * @return
	 */
	boolean stackContainsToken(Token token)
	{
		boolean found = false;
		for (SymbolTable table : scopestack)
			if (table.containsKey(token.string))
				found = true;
		return found;
	}
}
