
package salomon.engine.platform.project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.controller.gui.Utils;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.data.DBManager;
import salomon.engine.platform.data.common.SQLSelect;
import salomon.engine.platform.data.common.SQLUpdate;
import salomon.engine.platform.plugin.PluginLoader;
import salomon.engine.platform.task.ITask;
import salomon.engine.platform.task.Task;
import salomon.engine.platform.task.TaskManager;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.ISettings;

/**
 * An implemetation of IProjectManager interface. Class manages with projects
 * editing.
 */
public final class ProjectManager implements IProjectManager
{
	private static Logger _logger = Logger.getLogger(ProjectManager.class);

	private DBManager _dbManager;

	private IManagerEngine _managerEngine = null;

	private IProject _currentProject = null;

	public ProjectManager(IManagerEngine managerEngine)
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

	public void newProject()
	{
		_currentProject = new Project();
		// clearing old tasks
		_managerEngine.getTasksManager().clearTaskList();
	}

	/**
	 * Method loads project from data base.
	 * 
	 * @param projectID
	 * @return loaded project
	 * @throws Exception
	 */
	public void loadProject(int projectID) throws Exception
	{
		Project project = new Project();
		// loading plugin information
        // building query
		SQLSelect select = new SQLSelect();
        select.addTable(Project.TABLE_NAME);
        select.addCondition("project_id =", projectID);
        
		ResultSet resultSet = null;
		resultSet = _dbManager.select(select);
		resultSet.next();
		// loading project
        project.load(resultSet);
		
		// one row should be found, if found more, the first is got		
		if (resultSet.next()) {
			_logger.warn("TOO MANY ROWS");
		}
		resultSet.close();        
		
		// adding tasks				
		Collection<ITask> tasks = getTasksForProject(projectID);
		
		// clearing old tasks
		TaskManager taskManeger = (TaskManager) _managerEngine.getTasksManager();
		taskManeger.clearTaskList();
		taskManeger.addAllTasks(tasks);
		_logger.debug("project: " + project);
		_logger.debug("tasks: " + taskManeger.getTasks());
        _logger.info("Project successfully loaded.");

		// setting current project
		_currentProject = project;
	}

	/**
	 * Method saves project in data base - project header, plugins and tasks are
	 * saved.
	 * 
	 * @param project
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public void saveProject() throws Exception, ClassNotFoundException
	{
		// saving project header
		int projectID = saveProjectHeader();
		_currentProject.setProjectID(projectID);
		// saving tasks
		saveTasks();
		_logger.info("Project successfully saved.");
		//TODO: bez tego czasem zawisa :-(
		_dbManager.commit();
	}

	/**
	 * Method selects tasks for given project id
	 * 
	 * @param projectID
	 * @return @throws ClassNotFoundException
	 * @throws Exception
	 */
	private List<ITask> getTasksForProject(int projectID) throws Exception
	{
		List<ITask> tasks = new LinkedList<ITask>();	
        SQLSelect select = new SQLSelect();
        select.addTable(Task.TABLE_NAME + " t");
        select.addTable(Description.TABLE_NAME + " p");
        select.addColumn("t.task_id");
        select.addColumn("t.project_id");
        select.addColumn("t.task_name");
        select.addColumn("t.task_info");
        select.addColumn("t.status");
        select.addColumn("t.plugin_settings");
        select.addColumn("t.plugin_result");        
        select.addColumn("p.plugin_id");
        select.addColumn("p.plugin_name");
        select.addColumn("p.plugin_info");
        select.addColumn("p.location");
        select.addCondition("t.project_id =", projectID);
        select.addCondition("t.plugin_id = p.plugin_id");
        
		// executing query
		ResultSet resultSet = null;
		resultSet = _dbManager.select(select);
		try {
			while (resultSet.next()) {
				//TODO: move task loading to task manager ?                
                Description description = new Description();
                // loading plugin description
                description.load(resultSet);
				IPlugin plugin = PluginLoader.loadPlugin(description.getLocation());                
				ISettings pluginSettings = plugin.getSettingComponent().getSettings();
                
                Task task = (Task) _managerEngine.getTasksManager().createTask();
				task.setSettings(pluginSettings);
				task.setPlugin(plugin);
                // loading task
                task.load(resultSet);                
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
	 * Method saves tasks for given project. 
	 * 
	 * @param project
	 * @throws SQLException
	 */
	private void saveTasks() throws Exception
	{		
		// saving tasks		
		ITask[] tasks = _managerEngine.getTasksManager().getTasks();
        for (ITask task : tasks) {
            // TODO: is it neccessary?
            ((Task)task).setProjectID(_currentProject.getProjectID());
        	((Task)task).save();
        }
	}

	/**
	 * Updates task connected to project. Method can change only status and
	 * result of task. Other columns will be unaffected. If one want change
	 * other settings, saveProject() method should be used. The Project object
	 * is not passed to this method, so method allows to save only one task and
	 * may be called after every task processing. 
     * TODO: update date_finished
	 * 
	 * @param
	 * @throws SQLException
	 */
	public void updateTask(Task task, int projectId) throws SQLException
	{
		SQLUpdate update = new SQLUpdate(Task.TABLE_NAME);
        update.addValue("plugin_result", task.getResult().resultToString());
        update.addValue("status", task.getStatus());
        update.addValue("stop_time", new Time(System.currentTimeMillis()));
        update.addCondition("project_id =", projectId);

        _dbManager.update(update);
	}

	/**
	 * Saves project header. If project does not exist in data base it is
	 * inserted.
	 * 
	 * @return project id
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private int saveProjectHeader() throws SQLException, ClassNotFoundException
	{
		return ((Project)_currentProject).save();
	}

	/**
	 * @return Returns the currentProject.
	 */
	public IProject getCurrentProject()
	{
		return _currentProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.project.IProjectManager#getAvailableProjects()
	 */
	public Collection getAvailableProjects() throws SQLException,
			ClassNotFoundException
	{
		Collection projects = null;
        SQLSelect select = new SQLSelect();
        select.addTable(Project.TABLE_NAME);
		// executing query
		ResultSet resultSet = null;

		resultSet = DBManager.getInstance().select(select);
		projects = Utils.getDataFromResultSet(resultSet);
		return projects;
	}

} // end KnowledgeSystemManager
