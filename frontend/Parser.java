package frontend;

import intermediate.ListMaker;
import intermediate.Node;
import intermediate.SymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Parser
{
	static String SCOPE_MAKERS = "lambda|let|letrec|let\\*";
	static String ELEMENT = Scanner.WORD + "|" + Scanner.NUMBER + "|"
		+ Scanner.BOOLEAN + "|" + Scanner.SPECIAL_SYMBOL;
	static String LIST = "\\((" + ELEMENT + "+|(\\(" + ELEMENT + "+\\)*)?"
		+ ")\\)";

	Scanner scanner;
	Stack<SymbolTable> scopestack;
	public List<Node> listroots; //top level lists
	ListMaker listmanager;
	SymbolTable firstlevelsymbols;
	static SymbolTable GLOBAL_SYMBOLS;
	static
	{
		GLOBAL_SYMBOLS = new SymbolTable();
		GLOBAL_SYMBOLS.put("exact->inexact", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("inexact->exact", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("*", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("+", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("-", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("/", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("<", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("<=x", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("=", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put(">", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put(">=", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("abs", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("acos", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("append", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("apply", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("asin", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("assoc", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("assq", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("assv", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("atan", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("boolean?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("call-with-current-continuation",
			new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("call-with-input-file", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS
			.put("call-with-output-file", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("car", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("cdr", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("ceiling", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char->integer", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-alphabetic?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(char-ci<=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-ci", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-ci=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-ci>=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-ci>?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-downcase", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-lower-case?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-numeric?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-upcase", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-upper-case?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char-whitespace?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(char<=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char>=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char>?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("char?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("close-input-port", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("close-output-port", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("complex?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("cons", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("cos", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(current-input-port)", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS
			.put("(current-output-port)", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("define", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("display", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(eof-object?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(eq?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("equal?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(eqv?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(eval", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("even?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(exact?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("exp", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("expt", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("floor", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("for-each", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("force", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("gcd", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(inexact?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(input-port?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(integer->char", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("interaction-environment",
			new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("integer?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("lcm", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("length", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("list", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(list->string", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(list->vector", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("(list-ref", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("list-tailL", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("list?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("load", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("log", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("macroexpand", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("make-string", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("make-vector", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("map", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("max", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("member?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("memq", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("memv", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("min", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("modulo", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("negative?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("newline)", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("not", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("null-environment", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("null?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("number->string", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("number?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("odd?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("open-input-file", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("open-output-file", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("output-port?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("pair?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("peek-char", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("positive?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("procedure?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("quotient", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("rational?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("read", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("read-char", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("real?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("remainder", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("reverse", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("round", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("scheme-report-environment",
			new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("set-car!", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("set-cdr!", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("sin", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("sqrt", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string->list", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string->number", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string->symbol", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-append", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ci<=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ci", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ci=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ci>=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ci>?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-copy", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-fill!", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-length", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-ref", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string-set!", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string<=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string>=?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string>?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("string?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("substring", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("symbol->string", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("symbol?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("tan", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("truncate", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector->list", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector-fill!", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector-length", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector-ref", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector-set!", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("vector?", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("write", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("write-char", new TreeMap<String, Object>());
		GLOBAL_SYMBOLS.put("zero?", new TreeMap<String, Object>());
	}
	boolean quotemode;
	int quotemodeunmatchedparentheses;

	public Parser()
	{
		scopestack = new Stack<SymbolTable>();
		listroots = new ArrayList<Node>();
		listmanager = new ListMaker();
		firstlevelsymbols = new SymbolTable();
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
			if (listmanager.isAtTopLevel()
				&& (token.string.equals("(") || token.string.equals("[")))
			{	
				scopestack.clear();
				scopestack.push(GLOBAL_SYMBOLS);
				scopestack.push(currenttable = firstlevelsymbols);
				listmanager.startList(firstlevelsymbols);
				listroots.add(listmanager.giveRoot());
			}
			//case not top level
			else
			{
				//scope making--push new symbol table
				if (Pattern.compile(SCOPE_MAKERS).matcher(token.string).matches())
				{
					scopestack.push(currenttable = new SymbolTable());

					listmanager.addElement(token, currenttable);
				}

				//sublist-starting
				else if (token.string.equals("(") || token.string.equals("["))
				{
					listmanager.startSublist();

					if (quotemode)
					{
						quotemodeunmatchedparentheses++;
					}
				}
				//list-ending
				else if (token.string.equals(")") || token.string.equals("]"))
				{
					listmanager.endList();

					if (quotemode)
					{
						quotemodeunmatchedparentheses--;
						if (quotemodeunmatchedparentheses == 0)
						{
							listmanager.endList();
							quotemode = false;
						}
					}
				}
				//quote
				else if (token.string.equals("'"))
				{
					quotemode = true;
					quotemodeunmatchedparentheses = 0;
					listmanager.startSublist();
					listmanager.addElement(new Token("quote", "word"));
				}
				//add list element case
				else
				{
					listmanager.addElement(token);

					if (quotemode && quotemodeunmatchedparentheses == 0)
					{
						listmanager.endList();
						quotemode = false;
					}

					//if token is a variable, add to scope
					if (!stackContainsToken(token) && token.type != "keyword"
						&& (token.type == "word" || token.type == "specialsymbol"))
					{
						currenttable.put(token.string, new TreeMap<String, Object>());
					}
				}
			}
			//print token
			System.out.println(token.string + "\t\t\t" + token.type);
		}
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
