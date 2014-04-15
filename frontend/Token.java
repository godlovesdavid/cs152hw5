package frontend;

public class Token
{
	String type; //keyword, special character, number, boolean, element, etc.
	String string;

	Token(String type, String string)
	{
		this.type = type;
		this.string = string;
	}
}
