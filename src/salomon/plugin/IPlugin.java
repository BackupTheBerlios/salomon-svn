
package salomon.plugin;

import salomon.core.data.DataEngine;

/**
 * Interface implemented by all plugins.
 *
 */
public interface IPlugin extends IDataPlugin, IGraphicPlugin
{
	// not used
	void initizalize();

	// not used
	void destroy();

	Description getDescription();
}