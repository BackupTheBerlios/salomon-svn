
package salomon.engine.plugin;

import java.io.Serializable;

import org.apache.log4j.Logger;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/** Class helps managing plugin loading */
public final class LocalPlugin implements IPlugin, Serializable
{

	private Description _description;
    
    /**
	 * 
	 */
	public LocalPlugin()
	{
        _description = new Description();
	}

	/**
	 * @see salomon.plugin.IPlugin#destroy()
	 */
	public void destroy()
	{
		throw new UnsupportedOperationException(
				"Method destroy() not implemented yet!");
	}

	/**
	 * @see salomon.plugin.IDataPlugin#doJob(salomon.platform.IDataEngine,
	 *      salomon.platform.IEnvironment, salomon.plugin.ISettings)
	 */
	public IResult doJob(IDataEngine engine, IEnvironment environment,
			ISettings settings)
	{
		throw new UnsupportedOperationException(
				"Method doJob() not implemented yet!");
	}

	/**
	 * @see salomon.plugin.IPlugin#getDescription()
	 */
	public Description getDescription()
	{
		return _description;
	}

	/**
	 * @see salomon.plugin.IGraphicPlugin#getResultComponent()
	 */
	public IResultComponent getResultComponent()
	{
		throw new UnsupportedOperationException(
				"Method getResultComponent() not implemented yet!");
	}

	/**
	 * @see salomon.plugin.IGraphicPlugin#getSettingComponent()
	 */
	public ISettingComponent getSettingComponent()
	{
		throw new UnsupportedOperationException(
				"Method getSettingComponent() not implemented yet!");
	}

	/**
	 * @see salomon.plugin.IPlugin#initizalize()
	 */
	public void initizalize()
	{
		throw new UnsupportedOperationException(
				"Method initizalize() not implemented yet!");
	}

	/**
	 * Method loads plugin. If it is not loaded, tries load it using
	 * PluginLoader
	 * 
	 * @throws Exception
	 */
	public IPlugin load() throws Exception
	{
		LOGGER.debug("trying to load plugin"); //$NON-NLS-1$
		IPlugin plugin = PluginLoader.loadPlugin(_description.getLocation());
        plugin.setDescription(_description);        
		return plugin;
	}

	public void setDescription(Description description)
	{
		_description = description;
	}

	public String toString()
	{
		// TODO: change it to name:version or sth
		String path = _description.getLocation().getPath();
		int index = path.lastIndexOf('/');
		return path.substring(index + 1);
	}

	private static final Logger LOGGER = Logger.getLogger(LocalPlugin.class);
}