/** Java class "Settings.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.plugins;

import java.util.HashMap;
import java.util.Map;

/**
 *  
 */
public class Settings
{
	Map _settings = null;
	
	public Settings() {
		_settings = new HashMap();
	}
	
	public void put(String key, Object value) {
		_settings.put(key, value);
	}
	public Object get(String key) {
		return _settings.get(key);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return _settings.toString();
	}
} 
