
package salomon.core;

import salomon.core.data.DBManager;
import salomon.core.data.common.*;
import salomon.core.plugin.PluginLoader;
import salomon.core.task.Task;
import salomon.core.task.TaskManager;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.ISettings;
import java.io.File;
import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *  
 */
public class ProjectManager
{
	private static Logger _logger = Logger.getLogger(ProjectManager.class);

	private DBManager _dbManager;

	private ManagerEngine _managerEngine = null;

	private Project _currentProject = null;

	public ProjectManager(ManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
		try {
			_dbManager = DBManager.getInstance();
		} catch (SQLException e) {
			_logger.fatal("", e);
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
		}
	}

	public Project newProject()
	{
		_currentProject = new Project(_managerEngine);
		// clearing old tasks
		_currentProject.getManagerEngine().getTasksManager().clearTaskList();
		return _currentProject;
	}

	/**
	 * Method loads project from data base.
	 * 
	 * @param projectID
	 * @return loaded project
	 * @throws Exception
	 */
	public Project loadProject(int projectID) throws Exception
	{
		Project project = new Project(_managerEngine);
		//
		// loading plugin information
		//
		// building query
		DBTableName[] tableNames = {new DBTableName("projects")};
		DBColumnName[] columnNames = {new DBColumnName(tableNames[0], "name"),
				new DBColumnName(tableNames[0], "info")};
		DBCondition[] conditions = {new DBCondition(new DBColumnName(
				tableNames[0], "project_id"), DBCondition.REL_EQ, new Integer(
				projectID), DBCondition.NUMBERIC)};
		// executing query
		ResultSet resultSet = null;
		resultSet = _dbManager.select(columnNames, tableNames, conditions);
		resultSet.next();
		String projectName = resultSet.getString("name");
		String projectInfo = resultSet.getString("info");
		_logger.info("plugin: " + projectName + " | " + projectInfo);
		//
		// one row should be found, if found more, the first is got
		//
		if (resultSet.next()) {
			_logger.warn("TOO MANY ROWS");
		}
		resultSet.close();
		project.setName(projectName);
		project.setInfo(projectInfo);
		project.setProjectID(projectID);
		//
		// adding tasks
		//		
		List tasks = getTasksForProject(projectID);
		_logger.info("Project successfully loaded.");
		// clearing old tasks
		TaskManager taskManeger =project.getManagerEngine().getTasksManager(); 
		taskManeger.clearTaskList();
		taskManeger.addAllTasks(tasks);
		_logger.debug("project: " + project);
		_logger.debug("tasks: " + taskManeger.getTasks());
		//
		// setting current project
		//
		_currentProject = project;
		return project;
	}

	/**
	 * Method saves project in data base - project header, plugins and tasks are
	 * saved.
	 * 
	 * @param project
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public void saveProject(Project project) throws Exception,
			ClassNotFoundException
	{
		// saving project header
		int projectID = saveProjectHeader(project);
		project.setProjectID(projectID);
		// saving plugins
		savePlugins(project);
		// saving tasks
		saveTasks(project);
		_logger.info("Project successfully saved.");
	}

	/**
	 * Method selects tasks for given project id
	 * 
	 * @param projectID
	 * @return @throws
	 *         ClassNotFoundException
	 * @throws Exception
	 */
	private List getTasksForProject(int projectID) throws Exception
	{
		List tasks = new LinkedList();
		DBTableName[] tableNames = {new DBTableName("tasks"),
				new DBTableName("plugins")};
		DBColumnName[] columnNames = {
				new DBColumnName(tableNames[0], "task_id"),
				new DBColumnName(tableNames[0], "name", "task_name"),
				new DBColumnName(tableNames[0], "info", "task_info"),
				new DBColumnName(tableNames[1], "plugin_id"),
				new DBColumnName(tableNames[1], "name", "plugin_name"),
				new DBColumnName(tableNames[1], "info", "plugin_info"),
				new DBColumnName(tableNames[1], "location"),
				new DBColumnName(tableNames[0], "plugin_settings", "settings"),
				new DBColumnName(tableNames[0], "plugin_result", "result")};
		DBCondition[] conditions = {
				new DBCondition(new DBColumnName(tableNames[0], "project_id"),
						DBCondition.REL_EQ, new Integer(projectID),
						DBCondition.NUMBERIC),
				new DBCondition(new DBColumnName(tableNames[0], "plugin_id"),
						DBCondition.REL_EQ, new DBColumnName(tableNames[1],
								"plugin_id"), DBCondition.JOIN)};
		// executing query
		ResultSet resultSet = null;
		resultSet = _dbManager.select(columnNames, tableNames, conditions);
		boolean success = true;
		try {
			while (resultSet.next()) {
				int taskId = resultSet.getInt("task_id");
				String taskName = resultSet.getString("task_name");
				String taskInfo = resultSet.getString("task_info");
				int pluginId = resultSet.getInt("plugin_id");
				String pluginName = resultSet.getString("plugin_name");
				String pluginInfo = resultSet.getString("plugin_info");
				String location = resultSet.getString("location");
				String settings = resultSet.getString("settings");
				String result = resultSet.getString("result");
				_logger.info("" + taskId + "|" + taskName + "|" + taskInfo + "|" + pluginName + "|"
						+ pluginInfo + "|" + location + "|" + settings + "|"
						+ result);
				Task task = new Task();
				task.setName(taskName);
				task.setTaksId(taskId);
				IPlugin plugin = PluginLoader.loadPlugin(location);
				plugin.getDescription().setLocation(new File(location));
				plugin.getDescription().setName(pluginName);
				plugin.getDescription().setInfo(pluginInfo);
				plugin.getDescription().setPluginID(pluginId);
				ISettings pluginSettings = plugin.getSettingComponent()
						.getSettings();
				pluginSettings.parseSettings(settings);
				task.setSettings(pluginSettings);
				task.setPlugin(plugin);
				tasks.add(task);
			}
		} catch (Exception e) {
			_logger.fatal("", e);
			resultSet.close();
			throw e;
		}
		resultSet.close();
		return tasks;
	}

	/**
	 * Saves plugins for project. If there are plugins which doesn't exists in
	 * data base, they are inserted.
	 * 
	 * @param project
	 * @throws SQLException
	 */
	private void savePlugins(Project project) throws SQLException
	{
		List tasks = project.getManagerEngine().getTasksManager().getTasks();
		Set plugins = new HashSet();
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			Task task = (Task) iter.next();
			plugins.add(task.getPlugin());
		}
		//
		// for each plugin checking, if exists in base (if there is plugin with
		// the same name and location). If does not exist it is inserted
		//
		for (Iterator iter = plugins.iterator(); iter.hasNext();) {
			IPlugin plugin = (IPlugin) iter.next();
			Description desc = plugin.getDescription();
			DBTableName[] tableNames = {new DBTableName("plugins")};
			DBColumnName[] columnNames = {new DBColumnName(tableNames[0],
					"plugin_id")};
			DBCondition[] conditions = {
					new DBCondition(new DBColumnName(tableNames[0], "name"),
							DBCondition.REL_EQ, desc.getName(),
							DBCondition.TEXT),
					new DBCondition(
							new DBColumnName(tableNames[0], "location"),
							DBCondition.REL_EQ, desc.getLocation(),
							DBCondition.TEXT)};
			// executing query
			ResultSet resultSet = null;
			resultSet = _dbManager.select(columnNames, tableNames, conditions);
			// 
			// if plugin does not exist in base it is inserted
			// otherwise plugin_id is set
			//
			if (resultSet.next()) {
				int pluginID = resultSet.getInt("plugin_id");
				desc.setPluginID(pluginID);
				resultSet.close();
			} else {
				DBValue[] values = {
						new DBValue(new DBColumnName(tableNames[0], "name"),
								desc.getName(), DBValue.TEXT),
						new DBValue(new DBColumnName(tableNames[0], "info"),
								desc.getInfo(), DBValue.TEXT),
						new DBValue(
								new DBColumnName(tableNames[0], "location"),
								desc.getLocation(), DBValue.TEXT)};
				int pluginID = _dbManager.insert(values, "plugin_id");
				// saving plugin ID
				desc.setPluginID(pluginID);
			}
		}
	}

	/**
	 * Method saves tasks for given project. Simple imlementation: method
	 * deletes all tasks connected to given project existing in base and then
	 * inserts them again.
	 * 
	 * @param project
	 * @throws SQLException
	 */
	private void saveTasks(Project project) throws SQLException
	{
		//
		// deleting all tasks connected to given project
		// with status == Task.ACTIVE
		//
		DBTableName tableName = new DBTableName("tasks");
		DBCondition[] conditions = {
				new DBCondition(new DBColumnName(tableName, "project_id"),
						DBCondition.REL_EQ,
						new Integer(project.getProjectID()),
						DBCondition.NUMBERIC),
				new DBCondition(new DBColumnName(tableName, "status"),
						DBCondition.REL_EQ, Task.ACTIVE, DBCondition.TEXT)};
		_dbManager.delete(conditions);
		//
		// saving tasks
		//
		List tasks = project.getManagerEngine().getTasksManager().getTasks();
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			Task task = (Task) iter.next();
			DBValue[] values = {
					new DBValue(new DBColumnName(tableName, "project_id"),
							new Integer(project.getProjectID()),
							DBValue.NUMBERIC),
					new DBValue(new DBColumnName(tableName, "plugin_id"),
							new Integer(task.getPlugin().getDescription()
									.getPluginID()), DBValue.NUMBERIC),
					new DBValue(new DBColumnName(tableName, "name"), task
							.getName(), DBValue.TEXT),
					new DBValue(new DBColumnName(tableName, "plugin_settings"),
							task.getSettings().settingsToString(), DBValue.TEXT),
					new DBValue(new DBColumnName(tableName, "plugin_result"),
							task.getResult().resultToString(), DBValue.TEXT),
					new DBValue(new DBColumnName(tableName, "status"), task
							.getStatus(), DBValue.TEXT)};
			int taskId = _dbManager.insert(values, "task_id");
			task.setTaksId(taskId);
		}
	}

	/**
	 * Updates tasks connected to project. Method can change only status and
	 * result of task. Other columns will be unaffected. If one want change
	 * other settings, saveProject() method should be used. The Project object
	 * is not passed to this method, so method allows to save only one task and
	 * may be called after every task processing. TODO: update date_finished
	 * 
	 * @param
	 * @throws SQLException
	 */
	public void updateTasks(Task[] tasks, int projectId) throws SQLException
	{
		DBTableName tableName = new DBTableName("tasks");
		//
		// common condition for all tasks
		//
		DBCondition commonCondition = new DBCondition(new DBColumnName(
				tableName, "project_id"), DBCondition.REL_EQ, new Integer(
				projectId), DBCondition.NUMBERIC);
		//Task[] tasks =
		// project.getManagerEngine().getTasksManager().getTasks();
		//
		// updating tasks
		// while update only status, settings
		//
		for (int i = 0; i < tasks.length; i++) {
			DBValue[] values = {
					new DBValue(new DBColumnName(tableName, "plugin_result"),
							tasks[i].getResult().resultToString(), DBValue.TEXT),
					new DBValue(new DBColumnName(tableName, "status"), tasks[i]
							.getStatus(), DBValue.TEXT)};
			DBCondition[] conditions = {
					commonCondition,
					new DBCondition(new DBColumnName(tableName, "task_id"),
							DBCondition.REL_EQ, new Integer(tasks[i]
									.getTaksId()), DBCondition.NUMBERIC)};
			_dbManager.update(values, conditions);
		}
	}

	/**
	 * Saves project header. If project does not exist in data base it is
	 * inserted.
	 * 
	 * @param project
	 * @return project id
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private int saveProjectHeader(Project project) throws SQLException,
			ClassNotFoundException
	{
		int projectID = 0;
		//
		// Checking if project exists in data base
		//
		// building query
		DBTableName[] tableNames = {new DBTableName("projects")};
		DBCondition[] conditions = {new DBCondition(new DBColumnName(
				tableNames[0], "project_id"), DBCondition.REL_EQ, new Integer(
				project.getProjectID()), DBCondition.NUMBERIC)};
		// executing query
		ResultSet resultSet = null;
		resultSet = _dbManager.select(null, tableNames, conditions);
		// 
		// if project exists in base its id is selected
		// otherwise new id is generated
		//
		if (resultSet.next()) {
			projectID = resultSet.getInt("project_id");
			resultSet.close();
		} else {
			DBTableName tableName = new DBTableName("projects");
			DBValue[] values = {
					new DBValue(new DBColumnName(tableName, "name"), project
							.getName(), DBValue.TEXT),
					new DBValue(new DBColumnName(tableName, "info"), project
							.getInfo(), DBValue.TEXT)};
			projectID = _dbManager.insert(values, "project_id");
		}
		return projectID;
	}

	/**
	 * @return Returns the currentProject.
	 */
	public Project getCurrentProject()
	{
		return _currentProject;
	}
} // end KnowledgeSystemManager
