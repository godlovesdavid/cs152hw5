package frontend;

public class Token
{
	public String string;
	public String type; //can be keyword, string, number, boolean, element, etc.

	Token(String string, String type)
	{
		this.string = string;
		this.type = type;
	}

	public String toString()
	{
		return string;
	}
	
	public boolean equals(Object obj)
	{
		Token other = (Token) obj;
		return string == other.string && type == other.type;
	}
}
