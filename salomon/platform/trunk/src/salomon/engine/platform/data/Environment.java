

package salomon.engine.platform.data;

import java.util.HashMap;
import java.util.Map;

import salomon.platform.data.IEnvironment;

/**
 *  Class represents environment of task execution.
 */
public class Environment implements IEnvironment
{
	Map _environment = new HashMap();

	public Environment()
	{
	}

	public void put(String key, String value)
	{
		_environment.put(key, value);
	} 

	public String get(String key)
	{
		Object value = _environment.get(key);
		if (value != null) {
			return value.toString();
		}
		return null;
	}
} // class Environment
