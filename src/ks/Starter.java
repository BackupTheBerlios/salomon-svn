/** Java class "Starter.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ks.core.IManager;
import ks.core.ManagerEngine;
import ks.core.StandAloneManager;
import ks.core.DBManager;

/**
 *  
 */
public final class Starter
{
	private IManager _manager;

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
		_manager = new StandAloneManager();
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
	}

	public static void main(String[] args)
	{
		(new Starter()).start();
	}
} // end Starter
