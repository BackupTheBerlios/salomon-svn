/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.task;

import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.Environment;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.serialization.XMLSerializer;
import salomon.engine.plugin.ILocalPlugin;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.plugin.PlatformUtil;
import salomon.engine.plugin.PluginInfo;
import salomon.engine.project.IProject;
import salomon.engine.task.event.TaskEvent;
import salomon.engine.task.event.TaskListener;
import salomon.platform.IDataEngine;
import salomon.platform.IVariable;
import salomon.platform.exception.PlatformException;
import salomon.platform.serialization.IObject;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;

/**
 * An implemetation of ITaskManager interface. Class manages with tasks editing
 * and executing.
 */
public final class TaskManager implements ITaskManager
{
    private static final Logger LOGGER = Logger.getLogger(TaskManager.class);

    /**
     * 
     * @uml.property name="_dataEngine"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IDataEngine _dataEngine;

    /**
     * 
     * @uml.property name="_dbManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private DBManager _dbManager;

    /**
     * 
     * @uml.property name="_environment"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private Environment _environment;

    /**
     * 
     * @uml.property name="_managerEngine"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    // private IProject _project;
    private IManagerEngine _managerEngine;

    private PlatformUtil _platformUtil;

    private List<TaskListener> _taskListeners;

    private ITaskRunner _taskRunner;

    /**
     * 
     * @uml.property name="_taskEngine"
     * @uml.associationEnd multiplicity="(0 1)"
     */

    private LinkedList<ITask> _tasks;

    public TaskManager(IManagerEngine managerEngine, DBManager manager)
    {
        _managerEngine = managerEngine;
        _dbManager = manager;
        _taskRunner = new TaskRunner();
        _tasks = new LinkedList<ITask>();
        _taskListeners = new LinkedList<TaskListener>();
        // TODO: where it should be created?
        _environment = new Environment();
        // TODO: temporary
        // TODO: temporary
        IVariable variable;
        try {
            variable = _environment.createEmpty("CURRENT_DATA_SET");
            variable.setValue(new SimpleString("all_data"));
            _environment.add(variable);
        } catch (PlatformException e) {
            LOGGER.fatal("Exception was thrown!", e);
        }

        _platformUtil = new PlatformUtil();
    }

    @Deprecated
    public void addAllTasks(Collection<ITask> tasks)
    {
        _tasks.addAll(tasks);
    }

    /**
     * @see salomon.engine.task.ITaskManager#loadTasks(salomon.platform.task.ITask)
     */
    public void addTask(ITask task) throws PlatformException
    {
        try {
            task.getInfo().save();
            _dbManager.commit();
        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        _tasks.add(task);
    }

    public void addTaskListener(TaskListener listener)
    {
        _taskListeners.add(listener);
    }

    public void clearTasks()
    {
        _tasks.clear();
    }

    public ITask createTask() throws PlatformException
    {
        Task newTask = new Task(_dbManager);
        newTask.getInfo().setProjectID(
                _managerEngine.getProjectManager().getCurrentProject().getInfo().getId());
        newTask.getInfo().setTaskNr(_tasks.size() + 1);
        return newTask;
    }

    public ITask getCurrentTask()
    {
        throw new UnsupportedOperationException(
                "Method getCurrentTask() not implemented yet!");
    }

    public DBManager getDBManager()
    {
        return _dbManager;
    }

    /**
     * Returns the platformUtil.
     * @return The platformUtil
     */
    public PlatformUtil getPlatformUtil()
    {
        return _platformUtil;
    }

    public IProject getProject() throws PlatformException
    {
        // FIXME: change it - implement cascade model support
        return _managerEngine.getProjectManager().getCurrentProject();
    }

    /**
     * @see salomon.engine.task.ITaskManager#getRunner()
     */
    public ITaskRunner getRunner() throws PlatformException
    {
        return _taskRunner;
    }

    /**
     * Returns tasks associated with current project. They are returned sorted
     * by task_nr.
     * 
     * @see salomon.engine.task.ITaskManager#getTasks()
     */
    public ITask[] getTasks() throws PlatformException
    {
        LinkedList<ITask> tasks = null;
        // TODO: use _project in stead of it
        IProject currProject = _managerEngine.getProjectManager().getCurrentProject();
        if (_tasks.isEmpty() && currProject != null) {
            tasks = new LinkedList<ITask>();
            SQLSelect select = new SQLSelect();
            select.addTable(TaskInfo.TABLE_NAME + " t");
            select.addTable(PluginInfo.TABLE_NAME + " p");
            select.addColumn("t.task_id");
            select.addColumn("t.task_nr");
            select.addColumn("t.project_id");
            select.addColumn("t.task_name");
            select.addColumn("t.task_info");
            select.addColumn("t.status");
            select.addColumn("t.plugin_settings");
            select.addColumn("t.plugin_result");
            select.addColumn("t.c_date");
            select.addColumn("t.lm_date");
            select.addColumn("p.plugin_id");
            select.addColumn("p.plugin_name");
            select.addColumn("p.plugin_info");
            select.addColumn("p.location");
            int projectID = currProject.getInfo().getId();
            select.addCondition("t.project_id =", projectID);
            select.addCondition("t.plugin_id = p.plugin_id");
            // tasks are sorted by task_nr
            select.addOrderBy("t.task_nr");

            // executing query
            ResultSet resultSet = null;
            try {
                resultSet = _dbManager.select(select);
                while (resultSet.next()) {
                    // TODO: Plugin info shoul not be instantied from outside of
                    // pluginManger :-/
                    PluginInfo pluginInfo = new PluginInfo(_dbManager);
                    // FIXME: c_date and lm_date for the plugin will be loaded incorrectly
                    // it will be taken from the task :-/
                    pluginInfo.load(resultSet);
                    LocalPlugin loadedPlugin = (LocalPlugin) _managerEngine.getPluginManager().getPlugin(
                            pluginInfo.getId());

                    // loading plugin
                    loadedPlugin.load();

                    // calculation are made on the new plugin instance
                    // to avoid object sharing
                    LocalPlugin localPlugin = (LocalPlugin) loadedPlugin.clone();

                    // creating default settings
                    // it will be used to parse string representation of
                    // settings
                    ISettings pluginSettings = localPlugin.getSettingComponent(
                            _platformUtil).getDefaultSettings();

                    Task task = (Task) this.createTask();
                    // loading task
                    task.getInfo().load(resultSet);
                    // init settings
                    LOGGER.debug("loading settings...");
                    String strSettings = task.getInfo().getSettings();
                    if (strSettings != null) {
                        ByteArrayInputStream bis = new ByteArrayInputStream(
                                strSettings.getBytes());
                        IObject object = XMLSerializer.deserialize(bis);
                        pluginSettings.init(object);
                        LOGGER.debug("settings loaded");
                    }

                    task.setSettings(pluginSettings);

                    // init last result
                    LOGGER.debug("loading result...");
                    String strResult = task.getInfo().getResult();
                    IResult pluginResult = localPlugin.getResultComponent().getDefaultResult();
                    if (strResult != null) {
                        ByteArrayInputStream bis = new ByteArrayInputStream(
                                strResult.getBytes());
                        IObject object = XMLSerializer.deserialize(bis);
                        pluginResult.init(object);
                        LOGGER.debug("result loaded");
                    }

                    task.setResult(pluginResult);
                    task.setPlugin(localPlugin);
                    tasks.add(task);
                }
                resultSet.close();
                // if everything is OK, then loaded tasks list is assigned
                // to manager's tasks list
                _tasks = tasks;
            } catch (Exception e) {
                LOGGER.fatal("", e);
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    LOGGER.fatal("", ex);
                }
                throw new PlatformException(e.getLocalizedMessage());
            }
        }
        ITask[] tasksArr = new ITask[_tasks.size()];
        return _tasks.toArray(tasksArr);
    }

    /**
     * Removes all tasks for current project.
     * 
     * @see salomon.engine.task.ITaskManager#removeAll()
     */
    public boolean removeAll() throws PlatformException
    {
        SQLDelete delete = new SQLDelete(TaskInfo.TABLE_NAME);;
        IProject currProject = _managerEngine.getProjectManager().getCurrentProject();
        delete.addCondition("project_id =", currProject.getInfo().getId());
        boolean retVal = false;
        int deletedRows = 0;
        try {
            deletedRows = _dbManager.delete(delete);
            _dbManager.commit();
            // removing from list
            _tasks.clear();
            retVal = (deletedRows > 0);
        } catch (SQLException e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        return retVal;
    }

    /**
     * Removes given tasks.
     * 
     * @see salomon.engine.task.ITaskManager#removeTask(salomon.engine.task.ITask)
     */
    public boolean removeTask(ITask task) throws PlatformException
    {
        SQLDelete delete = new SQLDelete(TaskInfo.TABLE_NAME);
        IProject currProject = _managerEngine.getProjectManager().getCurrentProject();
        delete.addCondition("project_id =", currProject.getInfo().getId());
        delete.addCondition("task_id = ", task.getInfo().getId());
        boolean retVal = false;
        int deletedRows = 0;
        try {
            deletedRows = _dbManager.delete(delete);
            _dbManager.commit();
            // removing from list
            _tasks.remove(task);
            retVal = (deletedRows > 0);
        } catch (SQLException e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        return retVal;
    }

    public void removeTaskListener(TaskListener listener)
    {
        _taskListeners.remove(listener);
    }

    public void saveTasks() throws PlatformException
    {
        try {
            for (ITask task : _tasks) {
                ((Task) task).getInfo().save();
            }
            _dbManager.commit();
        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    /**
     * Method used only in this package.
     */
    IDataEngine getDataEngine() throws PlatformException
    {
        if (_dataEngine == null) {
            _dataEngine = _managerEngine.getSolutionManager().getCurrentSolution().getDataEngine();
        }
        return _dataEngine;
    }

    private void fireTaskFailed(TaskEvent event)
    {
        for (TaskListener listener : _taskListeners) {
            listener.taskFailed(event);
        }
    }

    private void fireTaskPaused(TaskEvent event)
    {
        for (TaskListener listener : _taskListeners) {
            listener.taskPaused(event);
        }
    }

    private void fireTaskProcessed(TaskEvent event)
    {
        for (TaskListener listener : _taskListeners) {
            listener.taskProcessed(event);
        }
    }

    private void fireTaskResumed(TaskEvent event)
    {
        for (TaskListener listener : _taskListeners) {
            listener.taskResumed(event);
        }
    }

    private void fireTasksInitialized(int taskCount)
    {
        for (TaskListener listener : _taskListeners) {
            listener.tasksInitialized(taskCount);
        }
    }

    private void fireTaskStarted(TaskEvent event)
    {
        for (TaskListener listener : _taskListeners) {
            listener.taskStarted(event);
        }
    }

    private void fireTaskStopped(TaskEvent event)
    {
        for (TaskListener listener : _taskListeners) {
            listener.taskStopped(event);
        }
    }

    private final class TaskEngine extends Thread
    {
        private ITask _currentTask = null;

        private boolean _paused = false;

        public void pauseTasks()
        {
            _paused = true;
            try {
                fireTaskPaused(new TaskEvent((TaskInfo) _currentTask.getInfo()));
            } catch (PlatformException e) {
                LOGGER.fatal("", e);
            }
        }

        public synchronized void resumeTasks()
        {
            _paused = false;
            notify();
            try {
                fireTaskResumed(new TaskEvent((TaskInfo) _currentTask.getInfo()));
            } catch (PlatformException e) {
                LOGGER.fatal("", e);
            }
        }

        @Override
        public void run()
        {
            LOGGER.info("Running tasks");
            for (ITask task : _tasks) {
                _currentTask = task;
                // running task
                startTask(task);
                // FIXME: temporary!
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    LOGGER.fatal("", e1);
                }
                synchronized (this) {
                    while (_paused) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            LOGGER.fatal("", e);
                        }
                    }
                }
            }
        }

        private void startTask(ITask task)
        {
            TaskInfo taskInfo = null;
            TaskEvent taskEvent = null;
            try {
                taskInfo = ((Task) task).getInfo();
                taskEvent = new TaskEvent(taskInfo);
                LOGGER.info("task: " + taskInfo.getName());
                ISettings settings = task.getSettings();
                taskInfo.setStatus(TaskInfo.REALIZATION);
                // changing status
                taskInfo.save();
                _dbManager.commit();

                // informing listeners about starting task
                fireTaskStarted(taskEvent);

                //
                // processing task
                //
                ILocalPlugin plugin = task.getPlugin();
                LOGGER.debug("plugin: " + plugin + " id: "
                        + plugin.getInfo().getId());
                IResult result = plugin.doJob(getDataEngine(), _environment,
                        settings);
                //
                // saving result of its execution
                //
                task.setResult(result);
                taskInfo.save();
                _dbManager.commit();

                // informing listeners about finishing processing
                if (result.isSuccessful()) {
                    fireTaskProcessed(taskEvent);
                } else {
                    fireTaskFailed(taskEvent);
                }
                //
                // if task was not processed correctly
                // exception is caught and shoul be handled here
                //
            } catch (Exception e) {
                LOGGER.fatal("TASK PROCESSING ERROR", e);
                try {
                    fireTaskFailed(taskEvent);
                    taskInfo.setStatus(TaskInfo.EXCEPTION);
                    taskInfo.save();
                } catch (Exception ex) {
                    LOGGER.fatal("", ex);
                }
                _dbManager.commit();
            }
        }

        private void stopCurrentTask()
        {
            if (_currentTask != null) {
                TaskInfo taskInfo = null;
                try {
                    taskInfo = (TaskInfo) _currentTask.getInfo();
                    LOGGER.debug("Stopping task: " + taskInfo.getName());
                    taskInfo.setStatus(TaskInfo.INTERRUPTED);
                    // changing status
                    taskInfo.save();
                    _dbManager.commit();

                    fireTaskStopped(new TaskEvent(taskInfo));
                } catch (Exception e) {
                    LOGGER.fatal("TASK INTERRUPTING ERROR", e);
                    try {
                        taskInfo.setStatus(TaskInfo.EXCEPTION);
                        taskInfo.save();
                    } catch (Exception ex) {
                        LOGGER.fatal("", ex);
                    }
                    _dbManager.commit();
                }
            }
        }
    }

    private final class TaskRunner implements ITaskRunner
    {
        private TaskEngine _taskEngine;

        public void pause() throws PlatformException
        {
            LOGGER.info("TaskRunner.pause()");
            if (_taskEngine != null) {
                _taskEngine.pauseTasks();
            }
        }

        public void resume() throws PlatformException
        {
            LOGGER.info("TaskRunner.resume()");
            if (_taskEngine != null) {
                _taskEngine.resumeTasks();
            }
        }

        public void start() throws PlatformException
        {
            LOGGER.info("TaskRunner.start()");
            // new thread is created before each tasks execution
            _taskEngine = new TaskEngine();
            fireTasksInitialized(_tasks.size());
            _taskEngine.start();
        }

        public void stop() throws PlatformException
        {
            LOGGER.info("TaskRunner.stop()");
            if (_taskEngine != null) {
                _taskEngine.stopCurrentTask();
                _taskEngine.interrupt();
            }
            _taskEngine = null;
        }

    }
}