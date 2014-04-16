package backend;

import frontend.Token;
import intermediate.Node;

public class ContentPrinter
{
	/**
	 * walk list and print code given the root node of a list, and the level of list (0 for top)
	 */
	public void printCode(Node rootnode)
	{
		System.out.print("(");
		walk(rootnode, 0);
		System.out.println();
	}

	public static void walk(Node node, int level)
	{
		//if contents is another node
		if (node.element instanceof Node)
		{
			//print newline
			System.out.print("\n");

			//indent
			for (int i = 0; i < level; i++)
				System.out.print("   ");

			System.out.print("(");

			//traverse node
			walk((Node) node.element, level + 1);
		}
		else
		{
			if (node.element != null)
				System.out.print(node.element + " ");
		}

		//if there's a next node
		if (node.next != null)
		{
			//traverse it
			walk(node.next, level);
		}
		else
		{
			System.out.print(")");
		}
	}

	/**
	 * walk list and print tokens given root node
	 */
	public void printTokens(Node node)
	{
		if (node.table != null)
		{
			System.out.println(node.table);
		}

		//if contents is another node
		if (node.element instanceof Node)
		{
			//traverse it
			printTokens((Node) node.element);
		}
//				else	//print node element contents
//				{
//					if (node.element != null)
//						System.out.println(node.element + ", type:"
//							+ ((Token) node.element).type);
//				}

		//if there's a next node
		if (node.next != null)
		{
			//traverse it
			printTokens(node.next);
		}
	}
}
