
package salomon.core.remote;

import salomon.core.data.DataEngine;
import salomon.core.data.Environment;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class PluginProxy implements IPlugin
{
	private IRemotePlugin _remotePlugin;

	private IPlugin _localPlugin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IPlugin#initizalize()
	 */
	public void initizalize()
	{
		_localPlugin.initizalize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IPlugin#destroy()
	 */
	public void destroy()
	{
		_localPlugin.destroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IPlugin#getDescription()
	 */
	public Description getDescription()
	{

		return null;//_localPlugin.get;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IDataPlugin#doJob(salomon.core.data.DataEngine,
	 *      salomon.core.data.Environment, salomon.plugin.ISettings)
	 */
	public IResult doJob(DataEngine engine, Environment environment,
			ISettings settings)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IGraphicPlugin#getSettingComponent()
	 */
	public ISettingComponent getSettingComponent()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IGraphicPlugin#getResultComponent()
	 */
	public IResultComponent getResultComponent()
	{
		// TODO Auto-generated method stub
		return null;
	}

}