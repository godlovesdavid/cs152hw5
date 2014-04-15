package intermediate;

import java.util.Stack;

import frontend.Token;

public class ListManager
{
	public Node root;

	Node pointer;
	Stack<Node> savednodes;

	public ListManager()
	{
		root = pointer = null;
		savednodes = new Stack<Node>();
	}

	/**
	 * start a new list
	 */
	public void startList()
	{
		//top level case
		if (root == null || pointer == root)
		{
			pointer = root = new Node();
			savednodes.push(pointer);
		}
		//contents doesn't have anything
		else if (pointer.element == null)
		{
			pointer.element = new Node();
			savednodes.push(pointer);
			pointer = (Node) pointer.element;
		}
		//contents has something
		else
		{
			pointer.nextnode = new Node(new Node());
			pointer = pointer.nextnode;
			savednodes.push(pointer);
			pointer = (Node) pointer.element;
		}
	}

	/**
	 * add atom to list
	 * @param atom Token that is a Scheme atom (goes into list):
	 * 	Numbers
	 * 	Strings
	 * 	Symbols
	 * 	Booleans
	 * 	Characters
	 */
	public void add(Token atom)
	{
		//contents has nothing
		if (pointer.element == null)
		{
			pointer.element = (Object) atom;
		}
		//contents has something
		else
		{
			pointer.nextnode = new Node(atom);
			pointer = pointer.nextnode;
		}
	}

	/**
	 * end list
	 */
	public void endList()
	{
		pointer = savednodes.pop();
	}

	/**
	 * test whether at top level
	 */
	public boolean isAtTopLevel()
	{
		return pointer == root;
	}

	/**
	 * give root node
	 * @return
	 */
	public Node giveRoot()
	{
		return root;
	}

	public void add(Token atom, SymbolTable symboltable)
	{
		add(atom);

		pointer.symboltable = symboltable;
	}
}
