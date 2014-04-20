package backend;

import java.util.ArrayList;
import java.util.List;

import frontend.Token;
import intermediate.Node;

public class Executor
{
	public static List<Node> headnodes = new ArrayList<Node>();  //top level list root nodes

	public static void execute()
	{
		for (Node node : headnodes)
		{
			System.out.println();
			Executor.printCode(node);
			System.out.println("\nScope:");
			Executor.printTokens(node);
		}
	}

	/**
	 * walk list and print code given the root node of a list
	 */
	public static void printCode(Node node)
	{
		System.out.print("(");
		walk(node, 0);
		System.out.println();
	}

	public static void walk(Node node, int level)
	{
		//element is another node?
		if (node.element instanceof Node)
		{
			//print newline.
			System.out.print("\n");

			//indent.
			for (int i = 0; i < level; i++)
				System.out.print("    ");

			System.out.print("(");

			//traverse node.
			walk((Node) node.element, level + 1);
		}
		else
		{
			if (node.element != null)
				System.out.print(node.element + " ");
		}

		//there's a next node?
		if (node.next != null)
		{
			//traverse it.
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
	public static void printTokens(Node node)
	{
		if (node.table != null)
			System.out.println(node.table);

		//element is another node?
		if (node.element instanceof Node)
			//traverse it.
			printTokens((Node) node.element);

		//there's a next node?
		if (node.next != null)
			//traverse it.
			printTokens(node.next);
	}
}
