
package salomon.controller.gui;

import salomon.core.task.ITask;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class TaskGUI
{
	private String _name;

	private IPlugin _plugin;

	private IResult _result;

	private ISettings _settings;

	private String _status;

	private ITask _task;

	private int _taskId;

	public TaskGUI()
	{
		_taskId = 0;
	}

	public TaskGUI(ITask task)
	{
		_task = task;
		_name = _task.getName();
		_plugin = _task.getPlugin();
		_result = _task.getResult();
		_settings = _task.getSettings();
		_status = _task.getStatus();
		_taskId = _task.getTaskId();
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
		if (isInitialized()) {
			return _task.getPlugin();
		} else {
			return _plugin;
		}
	}

	/**
	 * @return Returns the _result.
	 */
	public IResult getResult()
	{
		if (isInitialized()) {
			return _task.getResult();
		} else {
			return _result;
		}
	}

	/**
	 * @return Returns the _settings.
	 */
	public ISettings getSettings()
	{
		if (isInitialized()) {
			return _task.getSettings();
		} else {
			return _settings;
		}
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus()
	{
		if (isInitialized()) {
			return _task.getStatus();
		} else {
			return _status;
		}
	}

	/**
	 * @return Returns the taksId.
	 */
	public int getTaskId()
	{
		if (isInitialized()) {
			return _task.getTaskId();
		} else {
			return _taskId;
		}
	}

	public void save()
	{
		save(_task);
	}

	public void save(ITask task)
	{
		_task = task;
		_task.setPlugin(_plugin);
		_task.setName(_name);
		_task.setSettings(_settings);
	}

	public boolean isInitialized()
	{
		return _task != null;
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

	//    /**
	//     * @param _result
	//     * The _result to set.
	//     */
	//    public void setResult(IResult result)
	//    {
	//        _result = result;
	//        if (_result.isSuccessful()) {
	//            _status = Task.FINISHED;
	//        } else {
	//            _status = Task.ERROR;
	//        }
	//    }

	/**
	 * @param _settings
	 *            The _settings to set.
	 */
	public void setSettings(ISettings settings)
	{
		_settings = settings;
	}

	//    /**
	//     * @param status
	//     * The status to set.
	//     */
	//    public void setStatus(String status)
	//    {
	//        this._status = status;
	//    }

	//    /**
	//     * @param taksId
	//     * The taksId to set.
	//     */
	//    public void setTaskId(int taskId)
	//    {
	//        _taskId = taskId;
	//    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return _name + " [" + _plugin + "]";
	}
}