package front;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

import back.SymbolTable;
import back.Tree;

public class Parser
{
	Scanner scanner;
	Stack<SymbolTable> scopestack;
	List<Tree> trees; //top level function calls

	/**
	 * parses the code, puts in tree list
	 * @param src code
	 */
	public void parse(String src)
	{
		Queue<Token> tokens = scanner.tokenize(src);
		Token token;
		Tree tree;
		while (!tokens.isEmpty())
		{
			token = tokens.poll();
			if (token.string == "(" && scopestack.isEmpty())
			{
				tree = new Tree();
				scopestack.push(globalsymbols);
			}

		}
	}
}
