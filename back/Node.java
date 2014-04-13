package back;

public class Node
{
	Object contents; //can be Node or Token
	Node nextnode;
	SymbolTable symboltable;

	public Node()
	{

	}

	public Node(Object contents)
	{
		this.contents = contents;
	}
}
