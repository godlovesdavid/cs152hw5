package intermediate;

import java.util.TreeMap;

/**
 * maps from symbol name to attribute map (which has attribute name to value)
 *
 */
public class SymbolTable extends TreeMap<String, TreeMap<String, Object>>
{
	public SymbolTable()
	{

	}
}
