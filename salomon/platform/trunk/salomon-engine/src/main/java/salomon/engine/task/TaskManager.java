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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.agent.IAgentProcessingComponent;
import salomon.engine.agent.AgentProcessingComponent;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.task.event.ITaskListener;
import salomon.engine.task.event.TaskEvent;
import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlatformUtil;

/**
 * An implemetation of ITaskManager interface. Class manages with tasks editing
 * and executing.
 */
public final class TaskManager implements ITaskManager {

	private static final Logger LOGGER = Logger.getLogger(TaskManager.class);

	private static TaskComparator _taskComparator;

	private IDataEngine _dataEngine;

	private IManagerEngine _managerEngine;

	private List<ITaskListener> _taskListeners;

	private ITaskRunner _taskRunner;

	private AgentProcessingComponent _processingComponent;

	private List<Task> _taskList;

	public TaskManager(AgentProcessingComponent processinComponent) {
		_processingComponent = processinComponent;
		_taskList = new ArrayList<Task>();
		_taskComparator = new TaskComparator();
	}

	@Deprecated
	public TaskManager(IManagerEngine managerEngine) {
		_managerEngine = managerEngine;
		_taskRunner = new TaskRunner();
		_taskListeners = new LinkedList<ITaskListener>();
		// TODO: temporary
//		IVariable variable;
//		try {
//			variable = _environment.createEmpty("CURRENT_DATA_SET");
//			variable.setValue(new SimpleString("all_data"));
//			_environment.add(variable);
//		} catch (PlatformException e) {
//			LOGGER.fatal("Exception was thrown!", e);
//		}
	}

	public void addTask(ITask task) throws PlatformException {
		Task t = (Task) task;
		t.setAgentProcessingComponent(_processingComponent);
		t.setTaskNr(_taskList.size());
		_taskList.add(t);
	}

	
	public void addTaskListener(ITaskListener listener) {
		_taskListeners.add(listener);
	}
	
    public ITask createTask() throws PlatformException {
		Task task = new Task();
		return task;
	}

	
	/**
	 * @see salomon.engine.task.ITaskManager#getAgentProcessingComponent()
	 */
	public IAgentProcessingComponent getAgentProcessingComponent() {
		return _processingComponent;
	}

	public ITask getCurrentTask() {
		throw new UnsupportedOperationException(
				"Method getCurrentTask() not implemented yet!");
	}

	/**
	 * Returns platform util. Delegates method call to manager engine.
	 * 
	 * @return
	 */
	public IPlatformUtil getPlatformUtil() {
		return _managerEngine.getPlatformUtil();
	}

	/**
	 * @see salomon.engine.task.ITaskManager#getRunner()
	 */
	public ITaskRunner getRunner() throws PlatformException {
		return _taskRunner;
	}

	/**
	 * @see salomon.engine.task.ITaskManager#getTask(long)
	 */
	public ITask getTask(long taskId) {
		ITask task = null;
		for (Task t : _taskList) {
			if (taskId == t.getTaskId()) {
				task = t;
				break;
			}
		}
		return task;
	}

	/**
	 * @see salomon.engine.task.ITaskManager#getTask(java.lang.String)
	 */
	public ITask getTask(String taskName) {
		ITask task = null;
		for (Task t : _taskList) {
			if (taskName.equals(t.getTaskName())) {
				task = t;
				break;
			}
		}
		return task;
	}

	public List<Task> getTaskList()
    {
        // make sure the list of tasks is sorted
        Collections.sort(_taskList, _taskComparator);
        return _taskList;
    }

	/**
	 * Returns tasks associated with current project. They are returned sorted
	 * by task_nr.
	 * 
	 * @see salomon.engine.task.ITaskManager#getTasks()
	 */
	/**
	 * @see salomon.agent.IAgentProcessingComponent#getTasks()
	 */
	public Task[] getTasks() {
		// make sure the list of tasks is sorted
		Collections.sort(_taskList, _taskComparator);
		return _taskList.toArray(new Task[_taskList.size()]);
	}

	/**
	 * Removes given tasks.
	 * 
	 * @see salomon.engine.task.ITaskManager#removeTask(salomon.engine.task.ITask)
	 */
	/**
	 * @see salomon.agent.IAgentProcessingComponent#removeTask(salomon.engine.task.ITask)
	 */
	public void removeTask(ITask task) {
		_taskList.remove(task);
	}

	public void removeTaskListener(ITaskListener listener) {
		_taskListeners.remove(listener);
	}

	/**
	 * For testing purpose only. This method runs task using run() method. Using
	 * Thread.start() causes some Firebird synchronization problems.
	 */
	// FIXME: move it to test class
	public void runTasks() {
		LOGGER.info("TaskManager.runTasks()");
		// intentionally used run() instead of start()
		new TaskEngine().run();
	}

	public void setTaskList(List<Task> tasks)
    {
        _taskList = tasks;
    }

	/**
	 * Method used only in this package.
	 */
	// FIXME:
	IDataEngine getDataEngine() throws PlatformException {
		if (_dataEngine == null) {
			// FIXME: _dataEngine =
			// _managerEngine.getSolutionManager().getCurrentSolution().getDataEngine();
		}
		return _dataEngine;
	}

	private void fireTaskFailed(TaskEvent event) {
		for (ITaskListener listener : _taskListeners) {
			listener.taskFailed(event);
		}
	}

	private void fireTaskPaused(TaskEvent event) {
		for (ITaskListener listener : _taskListeners) {
			listener.taskPaused(event);
		}
	}

	private void fireTaskProcessed(TaskEvent event) {
		for (ITaskListener listener : _taskListeners) {
			listener.taskProcessed(event);
		}
	}

	private void fireTaskResumed(TaskEvent event) {
		for (ITaskListener listener : _taskListeners) {
			listener.taskResumed(event);
		}
	}

	private void fireTasksInitialized(int taskCount) {
		for (ITaskListener listener : _taskListeners) {
			listener.tasksInitialized(taskCount);
		}
	}

	private void fireTasksProcessed() {
		for (ITaskListener listener : _taskListeners) {
			listener.tasksProcessed();
		}
	}

	private void fireTaskStarted(TaskEvent event) {
		for (ITaskListener listener : _taskListeners) {
			listener.taskStarted(event);
		}
	}

	private void fireTaskStopped(TaskEvent event) {
		for (ITaskListener listener : _taskListeners) {
			listener.taskStopped(event);
		}
	}

	/**
	 * Comparator used to mantain the order of tasks in the collection.
	 */
	private class TaskComparator implements Comparator<Task> {
		public int compare(Task task1, Task task2) {
			int task1Nr = task1.getTaskNr();
			int task2Nr = task2.getTaskNr();
			return (task1Nr < task2Nr ? -1 : (task1Nr == task2Nr ? 0 : 1));
		}
	}

	private final class TaskEngine extends Thread {
		private ITask _currentTask = null;

		private boolean _paused = false;

		public void pauseTasks() {
			_paused = true;
			try {
				fireTaskPaused(new TaskEvent((TaskInfo) _currentTask.getInfo()));
			} catch (PlatformException e) {
				LOGGER.fatal("", e);
			}
		}

		public synchronized void resumeTasks() {
			_paused = false;
			notify();
			try {
				fireTaskResumed(new TaskEvent((TaskInfo) _currentTask.getInfo()));
			} catch (PlatformException e) {
				LOGGER.fatal("", e);
			}
		}

		@Override
		public void run() {
			LOGGER.info("Running tasks");
			for (ITask task : _taskList) {
				_currentTask = task;
				// running task
				startTask(task);

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
			// informing listeners about finishing task execution
			fireTasksProcessed();
		}

		private void startTask(ITask task) {
			// FIXME:
			// TaskInfo taskInfo = null;
			// TaskEvent taskEvent = null;
			// try {
			// taskInfo = ((Task) task).getInfo();
			// taskEvent = new TaskEvent(taskInfo);
			// LOGGER.info("task: " + taskInfo.getName());
			// ISettings settings = task.getSettings();
			// taskInfo.setStatus(TaskInfo.REALIZATION);
			// // changing status
			// taskInfo.save();
			// _dbManager.commit();
			//
			// // informing listeners about starting task
			// fireTaskStarted(taskEvent);
			//
			// //
			// // processing task
			// //
			// ILocalPlugin plugin = task.getPlugin();
			// LOGGER.debug("plugin: " + plugin + " id: "
			// + plugin.getInfo().getId());
			// IResult result = plugin.doJob(getDataEngine(), _environment,
			// settings);
			// //
			// // saving result of its execution
			// //
			// task.setResult(result);
			// taskInfo.save();
			// _dbManager.commit();
			//
			// // informing listeners about finishing processing
			// if (result.isSuccessful()) {
			// fireTaskProcessed(taskEvent);
			// } else {
			// fireTaskFailed(taskEvent);
			// }
			// //
			// // if task was not processed correctly
			// // exception is caught and shoul be handled here
			// //
			// } catch (Exception e) {
			// LOGGER.fatal("TASK PROCESSING ERROR", e);
			// try {
			// fireTaskFailed(taskEvent);
			// taskInfo.setStatus(TaskInfo.EXCEPTION);
			// taskInfo.save();
			// } catch (Exception ex) {
			// LOGGER.fatal("", ex);
			// }
			// _dbManager.commit();
			// }
		}

		private void stopCurrentTask() {
			// FIXME:
			// if (_currentTask != null) {
			// TaskInfo taskInfo = null;
			// try {
			// taskInfo = (TaskInfo) _currentTask.getInfo();
			// LOGGER.debug("Stopping task: " + taskInfo.getName());
			// taskInfo.setStatus(TaskInfo.INTERRUPTED);
			// // changing status
			// taskInfo.save();
			// _dbManager.commit();
			//
			// fireTaskStopped(new TaskEvent(taskInfo));
			// } catch (Exception e) {
			// LOGGER.fatal("TASK INTERRUPTING ERROR", e);
			// try {
			// taskInfo.setStatus(TaskInfo.EXCEPTION);
			// taskInfo.save();
			// } catch (Exception ex) {
			// LOGGER.fatal("", ex);
			// }
			// _dbManager.commit();
			// }
			// }
		}
	}

	private final class TaskRunner implements ITaskRunner {
		private TaskEngine _taskEngine;

		public void pause() throws PlatformException {
			LOGGER.info("TaskRunner.pause()");
			if (_taskEngine != null) {
				_taskEngine.pauseTasks();
			}
		}

		public void resume() throws PlatformException {
			LOGGER.info("TaskRunner.resume()");
			if (_taskEngine != null) {
				_taskEngine.resumeTasks();
			}
		}

		public void start() throws PlatformException {
			LOGGER.info("TaskRunner.start()");
			// new thread is created before each tasks execution
			_taskEngine = new TaskEngine();
			fireTasksInitialized(_taskList.size());
			_taskEngine.start();
		}

		public void stop() throws PlatformException {
			LOGGER.info("TaskRunner.stop()");
			if (_taskEngine != null) {
				_taskEngine.stopCurrentTask();
				_taskEngine.interrupt();
			}
			_taskEngine = null;
		}
	}
}