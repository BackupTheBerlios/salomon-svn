
package salomon;

import java.sql.SQLException;
import java.util.MissingResourceException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import pl.edu.agh.icsr.salomon.plugin.averageprice.APSettings;
import salomon.controller.ClientController;
import salomon.controller.IController;
import salomon.controller.LocalController;
import salomon.controller.ServerController;
import salomon.core.Config;
import salomon.core.IManagerEngine;
import salomon.core.ManagerEngine;
import salomon.core.data.DBManager;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBCondition;
import salomon.core.data.common.DBTableName;
import salomon.core.data.common.DBValue;
import salomon.core.project.ProjectManager;
import salomon.core.task.TaskManager;

/**
 *  
 */
public final class Starter
{
	private IController _contoroller;

	private IManagerEngine _managerEngine;

	private ProjectManager _projectManager = null;

	private Starter()
	{
		PropertyConfigurator.configure("log.conf");
		_logger.info("###  Application started  ###");
	}

	private void initManagers()
	{
		_managerEngine = new ManagerEngine();
		((TaskManager) _managerEngine.getTasksManager()).setProjectManger((ProjectManager) _managerEngine.getProjectManager());
		// oh shit - loop ;-(
		//_managerEngine.getTasksManager().setProjectManger(_projectManager);
		//_contoroller = new LocalController();
	}

	private void start()
	{
		initManagers();
		_contoroller.start(_managerEngine);

		//		try {
		//			new ProjectManager().loadProject(1);
		//		} catch (Exception e) {
		//			_logger.fatal("", e);
		//		}
		//testSettings();
		//testInsert();

		//testAutoInsert();
		//testDelete();
		//testUpdate();
	}

	private void startClient()
	{
		_logger.debug("starting ClientController");
		_contoroller = new ClientController();
		start();
	}

	private void startLocal()
	{
		_logger.debug("starting LocalController");
		_contoroller = new LocalController();
		start();
	}

	private void startServer()
	{
		_logger.debug("starting ServerController");
		_contoroller = new ServerController();
		start();
	}

	private void testAutoInsert()
	{
		DBTableName tableName = new DBTableName("tasks");
		DBValue[] values = {
				new DBValue(new DBColumnName(tableName, "project_id"),
						new Integer(2), DBValue.NUMBERIC),
				new DBValue(new DBColumnName(tableName, "plugin_id"),
						new Integer(2), DBValue.NUMBERIC),
				new DBValue(new DBColumnName(tableName, "name"),
						"test name auto inc", DBValue.TEXT),};
		try {
			DBManager.getInstance().insert(values, "task_id");
		} catch (SQLException e) {
			_logger.fatal("", e);
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
		}
	}

	private void testDelete()
	{
		DBTableName tableName = new DBTableName("tasks");
		DBCondition[] conditions = {
				new DBCondition(new DBColumnName(tableName, "task_id"),
						DBCondition.REL_M, new Integer(1), DBCondition.NUMBERIC),
				new DBCondition(new DBColumnName(tableName, "plugin_id"),
						DBCondition.REL_NEQ, new Integer(2),
						DBCondition.NUMBERIC)};
		try {
			DBManager.getInstance().delete(conditions);
		} catch (SQLException e) {
			_logger.fatal("", e);
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
		}
	}

	private void testInsert()
	{
		DBTableName tableName = new DBTableName("tasks");
		DBValue[] values = {
				new DBValue(new DBColumnName(tableName, "project_id"),
						new Integer(2), DBValue.NUMBERIC),
				new DBValue(new DBColumnName(tableName, "plugin_id"),
						new Integer(2), DBValue.NUMBERIC),
				new DBValue(new DBColumnName(tableName, "name"), "test name",
						DBValue.TEXT),
				new DBValue(new DBColumnName(tableName, "info"), "test info",
						DBValue.TEXT),
				new DBValue(new DBColumnName(tableName, "plugin_settings"),
						"test settings", DBValue.TEXT),
				new DBValue(new DBColumnName(tableName, "plugin_result"),
						"test result", DBValue.TEXT)};
		try {
			DBManager.getInstance().insert(values);
		} catch (SQLException e) {
			_logger.fatal("", e);
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
		}
	}

	private void testSettings()
	{
		APSettings settings = new APSettings();
		_logger.debug("test setting");
		settings.setNick("nico");
		_logger.debug(settings.settingsToString());
		_logger.debug(settings.toString());
		_logger.debug("test parsing");
		settings = new APSettings();
		settings.parseSettings(";;;");
		_logger.debug(settings.settingsToString());
		_logger.debug(settings.toString());
	}

	private void testUpdate()
	{
		DBTableName tableName = new DBTableName("tasks");
		DBValue[] values = {
				new DBValue(new DBColumnName(tableName, "project_id"),
						new Integer(2), DBValue.NUMBERIC),
				new DBValue(new DBColumnName(tableName, "plugin_id"),
						new Integer(2), DBValue.NUMBERIC),
				new DBValue(new DBColumnName(tableName, "name"), "test name",
						DBValue.TEXT)};
		DBCondition[] conditions = {
				new DBCondition(new DBColumnName(tableName, "task_id"),
						DBCondition.REL_M, new Integer(1), DBCondition.NUMBERIC),
				new DBCondition(new DBColumnName(tableName, "plugin_id"),
						DBCondition.REL_NEQ, new Integer(2),
						DBCondition.NUMBERIC)};
		try {
			DBManager.getInstance().update(values, conditions);
			DBManager.getInstance().update(values, null);
		} catch (SQLException e) {
			_logger.fatal("", e);
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
		}
	}

	public static void main(String[] args)
	{
		Starter starter = new Starter();
		if (args.length > 0) {
			if ("--local".equals(args[0])) {
				starter.startLocal();
			} else if ("--server".equals(args[0])) {
				starter.startServer();
			} else if ("--client".equals(args[0])) {
				starter.startClient();
			}
		} else {
			String mode = null;
			try {
				mode = Config.getString("MODE");
				if ("local".equals(mode)) {
					starter.startLocal();
				} else if ("server".equals(mode)) {
					starter.startServer();
				} else if ("client".equals(mode)) {
					starter.startClient();
				} else {
					_logger.error("Wrong argument");
					starter.startLocal();
				}

			} catch (MissingResourceException e) {
				_logger.fatal("", e);
				//TODO: read from properties which controller should be run
				_logger.warn("No argument choosen");
				starter.startLocal();
			}
		}

	}

	private static Logger _logger = Logger.getLogger(Starter.class);
}