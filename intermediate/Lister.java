package intermediate;

import java.util.HashMap;
import java.util.Stack;

import backend.Executor;
import frontend.Token;

public class Lister
{
	public Node head; //current root node of list that is being worked on

	Node pointed; //list-traversing reference
	Stack<Node> uppernodes; //for backtracking

	SymbolTable currenttable;
	Stack<SymbolTable> scopestack;
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
		GLOBAL_SYMBOLS.put("<=", new HashMap<String, Object>());
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
		GLOBAL_SYMBOLS.put("eof-object?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("eq?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("equal?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("eqv?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("eval", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("even?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("exact?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("exp", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("expt", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("floor", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("for-each", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("force", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("gcd", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("inexact?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("input-port?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("integer->char", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("interaction-environment",
			new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("integer?", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("lcm", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("length", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("list", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("list->string", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("list->vector", new HashMap<String, Object>());
		GLOBAL_SYMBOLS.put("list-ref", new HashMap<String, Object>());
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

	public Lister()
	{
		head = pointed = null;
		uppernodes = new Stack<Node>();
		scopestack = new Stack<SymbolTable>();
		toplevelsymbols = new SymbolTable();
	}

	/**
	 * start new top level list
	 * @param topleveltable symbol table to add to node
	 * @return whether new list is a new top level list
	 */
	public void startList()
	{
		//new top level list? 
		if (head == null)
		{
			//Make new head.
			head = pointed = new Node();
			pointed.table = currenttable = toplevelsymbols;
			uppernodes.push(pointed);
			scopestack.clear();
			scopestack.push(GLOBAL_SYMBOLS);
			scopestack.push(toplevelsymbols);
		}
		//sublist?
		else
		{
			//node has no element? 
			if (pointed.element == null)
			{
				//Its element is now new node.
				pointed.element = new Node();
				uppernodes.push(pointed);
				pointed = (Node) pointed.element;
			}
			//node has element?
			else
			{
				// Its next node is a new node, with element being a new node.
				pointed.next = new Node(new Node());
				pointed = pointed.next;
				uppernodes.push(pointed);
				pointed = (Node) pointed.element;
			}
		}
	}

	/**
	 * add atom to list
	 * @param token Token that is a Scheme atom (goes into list):
	 * 	Numbers
	 * 	Strings
	 * 	Symbols
	 * 	Booleans
	 * 	Characters
	 */
	public void addElement(Token token, boolean isscopemaking)
	{
		//node has no element? 
		if (pointed.element == null)
		{	
			//set element to token.
			pointed.element = (Object) token;
		}
		//node has element? 
		else
		{
			//make new node.
			pointed.next = new Node(token);
			pointed = pointed.next;
		}

		//scope making symbol? 
		if (isscopemaking)
		{
			//make new scope.
			scopestack.push(currenttable = pointed.table = new SymbolTable());
		}
		//regular symbol?
		else
		{
			// add to scope.
			if (!stackContainsToken(token) && token.type == "symbol")
				currenttable.put(token.string, new HashMap<String, Object>());
		}
	}

	/**
	 * end list
	 */
	public void endList()
	{
		pointed = uppernodes.pop();

		//done with top level list?
		if (pointed == head)
		{
			// Add root node to Executor's list of roots.
			Executor.headnodes.add(head);
			head = pointed = null;
		}
		//not done with top level list yet?
		else
		{
			//going back to another scope? 
			if (pointed.table != null && currenttable != pointed.table)
			{
				//Set current scope table to that scope table.
				scopestack.pop();
				currenttable = pointed.table;
			}
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
