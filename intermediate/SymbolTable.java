package intermediate;

import java.util.Map;
import java.util.TreeMap;

/**
 * maps from symbol name to attribute map (which has attribute name to value)
 *
 */
public class SymbolTable extends TreeMap<String, Map<String, Object>>
{
	public SymbolTable()
	{

	}
}
