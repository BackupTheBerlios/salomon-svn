/*
 * Created on 2004-05-22
 *
 */

package salomon.plugin;

/**
 * Interface supplies graphic interface for plugin. It enables configuration and
 * showing result of plugin execution.
 * 
 * @author nico 
 */
public interface IGraphicPlugin
{
	/**
	 * Method returns object which is responsible for showing plugin settings.
	 * 
	 * @return object showing plugin settings
	 */
	ISettingComponent getSettingComponent();

	/**
	 * Method returns object which is responsible for showing result of plugin
	 * execution.
	 * 
	 * @return object showing of plugin execution
	 */
	IResultComponent getResultComponent();
}