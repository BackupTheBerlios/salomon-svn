/*
 * Created on 2004-05-07 
 */
package ks.core.event;

/**
 * @author nico
 * 
 * Interface used to enable communication between IManager and ControllerGUI.
 * 
 */
public interface TaskListener
{
	/**
	 * Called when tasks are ready to be run. 
	 * It means when user pressed "run" button
	 * and wants to run them 
	 * (he must apply list of them before)
	 * @param e
	 */
	void runTasks(TaskEvent e);
	
	/**
	 * Called when tasks are going to be applied in tasks list. 
	 * 
	 * @param e
	 */
	void applyTasks(TaskEvent e);
	
}
