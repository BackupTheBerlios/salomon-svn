
package salomon.plugin;

import salomon.engine.platform.DataEngine;

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