
package salomon.core.task;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 *  
 */
public class Task
{
	public static final String ACTIVE = "AC";

	public static final String ERROR = "ER";

	public static final String EXCEPTION = "EX";

	public static final String FINISHED = "FI";

	public static final String REALIZATION = "RE";

	private String _name;

	private IPlugin _plugin;

	private IResult _result;

	private ISettings _settings;

	private String _status;
	
	private int _taksId;

	public Task()
	{
		_taksId = 0;
		_status = ACTIVE;
	}

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
		IResult result = _result; 
		if (_result == null) {
			result = _plugin.getResultComponent().getDefaultResult();
		}
		return result;
	}

	/**
	 * @return Returns the _settings.
	 */
	public ISettings getSettings()
	{
		return _settings;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus()
	{
		return _status;
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
		if (_result.isSuccessful()) {
			_status = Task.FINISHED;
		} else {
			_status = Task.ERROR;
		}
	}

	/**
	 * @param _settings
	 *            The _settings to set.
	 */
	public void setSettings(ISettings settings)
	{
		_settings = settings;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status)
	{
		this._status = status;
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
	/**
	 * @return Returns the taksId.
	 */
	public int getTaksId()
	{
		return _taksId;
	}
	/**
	 * @param taksId The taksId to set.
	 */
	public void setTaksId(int taksId)
	{
		_taksId = taksId;
	}
} // end Task
