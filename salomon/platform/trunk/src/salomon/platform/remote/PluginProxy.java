
package salomon.platform.remote;

import salomon.platform.data.DataEngine;
import salomon.platform.data.Environment;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * Class is a sever side wrapper of IPluginProxy object. It implements IPlugin
 * interface and delegates methods execution to remote object catching all
 * RemoteExceptions.
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
	 * @see salomon.plugin.IDataPlugin#doJob(salomon.platform.data.DataEngine,
	 *      salomon.platform.data.Environment, salomon.plugin.ISettings)
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