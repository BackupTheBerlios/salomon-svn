/** Java class "StandAloneManager.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.controller;

import java.util.Iterator;
import java.util.List;

import ks.core.ManagerEngine;
import ks.core.event.TaskEvent;
import ks.core.event.TaskListener;
import ks.core.task.Task;
import ks.core.task.TaskManager;
import ks.manager.gui.ManagerGUI;

import org.apache.log4j.Logger;

/**
 *  
 */
public final class LocalController implements IController
{
	private ManagerEngine _managerEngine = null;

	private static Logger _logger = Logger.getLogger(LocalController.class);

	public void start(ManagerEngine engine)
	{
		_managerEngine = engine;
		ManagerGUI managerGUI = new ManagerGUI();
		managerGUI.addTaskListener(new SAMTaskListener());
		managerGUI.setVisible(true);
	}

	private class SAMTaskListener implements TaskListener
	{
		public void runTasks(TaskEvent e)
		{
			_managerEngine.getTasksManager().start();
		}

		public void applyTasks(TaskEvent e)
		{
			List taskList = e.getTaskList();
			_logger.info("taskList" + taskList);
			TaskManager taskManager = _managerEngine.getTasksManager();
			for (Iterator iter = taskList.iterator(); iter.hasNext();) {
				taskManager.addTask((Task) iter.next());
			}
		}
	}
}
