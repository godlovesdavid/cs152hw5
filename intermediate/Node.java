package intermediate;

public class Node
{
	public Object element; //can be Node or Token
	public Node next;
	public SymbolTable table;

	public Node()
	{

	}

	public Node(Object element)
	{
		this.element = element;
	}

	public String toString()
	{
		return element.toString();
	}
}