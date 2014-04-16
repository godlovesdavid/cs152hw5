package frontend;

public class Token
{
	public String string;
	public String type;

	Token(String string, String type)
	{
		this.string = string;
		this.type = type;
	}

	public String toString()
	{
		return string;
	}
}
