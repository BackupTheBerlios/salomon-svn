/*
 * Created on 2004-05-03
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package salomon.plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nico 
 * 
 * Class represents result of plugin execution.
 */
public class Result
{
	Map _result = null;
	
	public Result() {
		_result = new HashMap();
	}
	
	public void put(String key, Object value) {
		_result.put(key, value);
	}
	public Object get(String key) {
		return _result.get(key);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return _result.toString();
	}
}
