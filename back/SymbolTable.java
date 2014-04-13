package back;

import java.util.HashMap;
import java.util.Map;

/**
 * maps from symbol name to attribute map (which has attribute name to value)
 *
 */
public class SymbolTable extends HashMap<String, Map<String, String>>
{
	SymbolTable()
	{

	}
}
