
package salomon.engine.plugin;

import java.io.Serializable;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * Class helps managing plugin loading
 */
public final class LocalPlugin implements ILocalPlugin, Serializable, Cloneable
{

	/**
	 * 
	 * @uml.property name="_plugin"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IPlugin _plugin;

	/**
	 * 
	 * @uml.property name="_pluginInfo"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private PluginInfo _pluginInfo;

	/**
	 * 
	 */
	protected LocalPlugin(DBManager manager)
	{
		_pluginInfo = new PluginInfo(manager);
	}

	/**
	 * @see salomon.plugin.IDataPlugin#doJob(salomon.platform.IDataEngine,
	 *      salomon.platform.IEnvironment, salomon.plugin.ISettings)
	 */
	public IResult doJob(IDataEngine engine, IEnvironment environment,
			ISettings settings)
	{
		if (_plugin == null) {
			throw new Error("Plugin not loaded");
		}
		return _plugin.doJob(engine, environment, settings);
	}

	public PluginInfo getInfo() throws PlatformException
	{
		return _pluginInfo;
	}

	/**
	 * @see salomon.plugin.IGraphicPlugin#getResultComponent()
	 */
	public IResultComponent getResultComponent()
	{
		if (_plugin == null) {
			throw new Error("Plugin not loaded");
		}
		return _plugin.getResultComponent();
	}

	/**
	 * @see salomon.plugin.IGraphicPlugin#getSettingComponent()
	 */
	public ISettingComponent getSettingComponent()
	{
		if (_plugin == null) {
			throw new Error("Plugin not loaded");
		}
		return _plugin.getSettingComponent();
	}

	/**
	 * Method loads plugin. If it is not loaded, tries load it using
	 * PluginLoader
	 * 
	 * @throws Exception
	 */
	public void load() throws Exception
	{
		LOGGER.debug("trying to load plugin"); //$NON-NLS-1$
		if (_plugin == null) {
			_plugin = PluginLoader.loadPlugin(_pluginInfo.getLocation());
		} else {
			LOGGER.debug("plugin already loaded"); //$NON-NLS-1$
		}
	}

	public String toString()
	{
		// TODO: change it to name:version or sth
		return _pluginInfo.getName();
	}
	
	@Override
	public Object clone() 
	{
		Object newPlugin = null;
		try {
			newPlugin = super.clone();
		} catch (CloneNotSupportedException e) {
			LOGGER.fatal("", e);
		}
		return newPlugin;
	}

	private static final Logger LOGGER = Logger.getLogger(LocalPlugin.class);
}