/** Java class "Task.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package salomon.core.task;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 *  
 */
public class Task
{
	private IPlugin _plugin;

	private ISettings _settings;

	private IResult _result;
	
	/**
	 * @return Returns the _plugin.
	 */
	public IPlugin getPlugin()
	{
		return _plugin;
	}
	/**
	 * @param _plugin The _plugin to set.
	 */
	public void setPlugin(IPlugin plugin)
	{
		_plugin = plugin;
	}
	/**
	 * @return Returns the _settings.
	 */
	public ISettings getSettings()
	{
		return _settings;
	}
	/**
	 * @param _settings The _settings to set.
	 */
	public void setSettings(ISettings settings)
	{
		_settings = settings;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{		
		return super.toString() + "[" + _plugin + "]";
	}
	/**
	 * @return Returns the _result.
	 */
	public IResult getResult()
	{
		return _result;
	}
	/**
	 * @param _result The _result to set.
	 */
	public void setResult(IResult result)
	{
		_result = result;
	}
} // end Task
