/** Java class "Environment.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package salomon.core.data;

import java.util.HashMap;
import java.util.Map;

/**
 *  
 */
public class Environment
{
	Map _environment = new HashMap();

	public Environment()
	{
	}

	public void put(String key, String value)
	{
		_environment.put(key, value);
	} // end put

	public String get(String key)
	{
		Object value = _environment.get(key);
		if (value != null) {
			return value.toString();
		}
		return null;
	}
} // class Environment
