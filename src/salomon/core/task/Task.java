
package salomon.core.task;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 *  
 */
public class Task
{
	private String _name;

	private IPlugin _plugin;

	private IResult _result;

	private ISettings _settings;

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return Returns the _plugin.
	 */
	public IPlugin getPlugin()
	{
		return _plugin;
	}

	/**
	 * @return Returns the _result.
	 */
	public IResult getResult()
	{
		return _result;
	}

	/**
	 * @return Returns the _settings.
	 */
	public ISettings getSettings()
	{
		return _settings;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name)
	{
		this._name = name;
	}

	/**
	 * @param _plugin
	 *            The _plugin to set.
	 */
	public void setPlugin(IPlugin plugin)
	{
		_plugin = plugin;
	}

	/**
	 * @param _result
	 *            The _result to set.
	 */
	public void setResult(IResult result)
	{
		_result = result;
	}

	/**
	 * @param _settings
	 *            The _settings to set.
	 */
	public void setSettings(ISettings settings)
	{
		_settings = settings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return _name + " [" + _plugin + "]";
	}
} // end Task
