/** Java class "Task.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.core;

import ks.data.Environment;
import ks.plugins.AbstractPlugin;
import ks.plugins.Settings;

/**
 *  
 */
public class Task
{
	///////////////////////////////////////
	// attributes
	/**
	 * Represents ...
	 */
	private Environment _environment;

	/**
	 * Represents ...
	 */
	private AbstractPlugin _plugin;

	/**
	 * Represents ...
	 */
	private Settings _settings;

	
	/**
	 * @return Returns the _environment.
	 */
	public Environment getEnvironment()
	{
		return _environment;
	}
	/**
	 * @param _environment The _environment to set.
	 */
	public void setEnvironment(Environment environment)
	{
		_environment = environment;
	}
	/**
	 * @return Returns the _plugin.
	 */
	public AbstractPlugin getPlugin()
	{
		return _plugin;
	}
	/**
	 * @param _plugin The _plugin to set.
	 */
	public void setPlugin(AbstractPlugin plugin)
	{
		_plugin = plugin;
	}
	/**
	 * @return Returns the _settings.
	 */
	public Settings getSettings()
	{
		return _settings;
	}
	/**
	 * @param _settings The _settings to set.
	 */
	public void setSettings(Settings settings)
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
} // end Task
