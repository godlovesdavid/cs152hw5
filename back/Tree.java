package back;

import front.Token;

public class Tree
{
	Node root;
	NodeIterator iterator;

	Tree()
	{
		iterator = new NodeIterator(root = new Node());
	}

	/**
	 * start a new list
	 */
	public void startList()
	{
		if (iterator.currentnode.contents == null)
		{
			iterator.currentnode = new Node();
			iterator.rememberNode();
			iterator.goIntoContents();
		}
		else
		{
			iterator.currentnode.nextnode = new Node(new Node());
			iterator.rememberNode();
			iterator.goNext();
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
		if (iterator.currentnode == null)
		{
			iterator.currentnode.contents = (Object) atom;
		}
		else
		{
			iterator.currentnode.nextnode = new Node(atom);
			iterator.goNext();
		}
	}

	/**
	 * end list
	 */
	public void endList()
	{
		iterator.backtrack();
	}
}
