package back;

import java.util.Stack;

public class NodeIterator
{
	Node currentnode;
	Stack<Node> savednodes;

	NodeIterator(Node startingnode)
	{
		currentnode = startingnode;
	}

	/**
	 * goes to next node (node connected to currentnode)
	 */
	public void goNext()
	{
		currentnode = currentnode.nextnode;
	}

	/**
	 * treats the contents of this node as a node and sets its current node to it
	 */
	public void goIntoContents()
	{
		currentnode = (Node) currentnode.contents;
	}

	/**
	 * save position
	 */
	public void rememberNode()
	{
		savednodes.push(currentnode);
	}

	/**
	 * load position
	 */
	public void backtrack()
	{
		currentnode = savednodes.pop();
	}
}
