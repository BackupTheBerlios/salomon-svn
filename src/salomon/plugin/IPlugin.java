
package salomon.plugin;

/**
 * Interface implemented by all plugins.
 * 
 * @author nico
 */
public interface IPlugin extends IDataPlugin, IGraphicPlugin
{
	// not used
	void initizalize();

	// not used
	void destroy();

	Description getDescription();
}