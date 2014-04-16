package intermediate;

import java.util.Stack;

import frontend.Token;

public class ListMaker
{
	Node root; //current root node of list that is being worked on

	Node pointer; //list-traversing variable
	Stack<Node> savednodes; //for backtracking

	public ListMaker()
	{
		root = pointer = null;
		savednodes = new Stack<Node>();
	}

	/**
	 * start new top level list
	 * @param topleveltable symbol table to add to node
	 */
	public void startList(SymbolTable topleveltable)
	{
		pointer = root = new Node();
		pointer.table = topleveltable;
		savednodes.push(pointer);
	}

	/**
	 * start a new sublist
	 */
	public void startSublist()
	{
		//node doesn't have element
		if (pointer.element == null)
		{
			pointer.element = new Node();
			savednodes.push(pointer);
			pointer = (Node) pointer.element;
		}
		//node has element
		else
		{
			pointer.next = new Node(new Node());
			pointer = pointer.next;
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
	public void addElement(Token atom)
	{
		//contents has nothing
		if (pointer.element == null)
		{
			pointer.element = (Object) atom;
		}
		//contents has something
		else
		{
			pointer.next = new Node(atom);
			pointer = pointer.next;
		}
	}

	/**
	 * add atom to list with symbol table reference
	 * @param atom
	 * @param symboltable
	 */
	public void addElement(Token atom, SymbolTable symboltable)
	{
		addElement(atom);

		pointer.table = symboltable;
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
	 * give root node that this manager is working on at the moment
	 * @return
	 */
	public Node giveRoot()
	{
		return root;
	}
}
