/** Java class "ManagerEngine.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package salomon.core;

import salomon.core.task.*;

/**
 *  
 */
public class ManagerEngine
{
	private TaskManager _taskManager = null;
	
	public ManagerEngine() {
		_taskManager = new TaskManager();
	}
	
	public TaskManager getTasksManager()
	{		
		return _taskManager;
	} 
	
	public Project getKnowledgeSystems()
	{
		// your code here
		return null;
	} // end getKnowledgeSystems
} // end ManagerEngine
