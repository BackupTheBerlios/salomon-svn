/** Java class "Starter.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package salomon;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.controller.IController;
import salomon.controller.LocalController;
import salomon.core.ManagerEngine;
import salomon.core.data.DBManager;
import salomon.core.plugin.PluginLoader;

/**
 *  
 */
public final class Starter
{
	private IController _manager;

	private ManagerEngine _managerEngine;

	private DBManager _dbConnector;

	private static Logger _logger = Logger.getLogger(Starter.class);

	public Starter()
	{
		PropertyConfigurator.configure("log.conf");
		_logger.info("###  Application started  ###");
	}

	private void initManagers()
	{
		_manager = new LocalController();
		_managerEngine = new ManagerEngine();
		try {
			_dbConnector = DBManager.getInstance();
		} catch (SQLException e) {
			_logger.fatal("", e);
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
		}
	}

	public void start()
	{
		initManagers();
		_manager.start(_managerEngine);
		//testLoadPlugin();
	}

	public static void main(String[] args)
	{
		(new Starter()).start();
	}
	// OK
	private void testLoadPlugin()
	{
		try {
			Object plugin = PluginLoader
					.loadPlugin("plugins/AveragePrice.jar");
			_logger.debug(plugin);
		} catch (Exception e) {
			_logger.fatal("", e);
		}
	}
} // end Starter
