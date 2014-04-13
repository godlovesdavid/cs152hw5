package front;

public class Token
{
	String type; //keyword, special character, number, boolean, element, etc.
	String string;
	boolean isscopeproducing; //whether it makes a scope

	Token(String type, String string, boolean isscopeproducing)
	{
		this.type = type;
		this.string = string;
		this.isscopeproducing = isscopeproducing;
	}
}
