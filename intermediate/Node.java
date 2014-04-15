package intermediate;

/**
* node containing list element
*/
public class Node
{
	Object element; //can be Node or Token
	Node nextnode;
	SymbolTable symboltable;

	public Node()
	{

	}

	public Node(Object element)
	{
		this.element = element;
	}
}
