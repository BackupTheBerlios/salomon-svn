
package salomon.core.task;

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
public interface ITask
{
	/**
	 * @return Returns the name.
	 */
	public String getName();

	/**
	 * @return Returns the _plugin.
	 */
	public IPlugin getPlugin();

	/**
	 * @return Returns the _result.
	 */
	public IResult getResult();

	/**
	 * @return Returns the _settings.
	 */
	public ISettings getSettings();

	/**
	 * @return Returns the status.
	 */
	public String getStatus();

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name);

	/**
	 * @param _plugin
	 *            The _plugin to set.
	 */
	public void setPlugin(IPlugin plugin);

	/**
	 * @param _result
	 *            The _result to set.
	 */
	public void setResult(IResult result);

	/**
	 * @param _settings
	 *            The _settings to set.
	 */
	public void setSettings(ISettings settings);

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status);

	/**
	 * @return Returns the taksId.
	 */
	public int getTaskId();

	/**
	 * @param taksId
	 *            The taksId to set.
	 */
	public void setTaskId(int taskId);
}