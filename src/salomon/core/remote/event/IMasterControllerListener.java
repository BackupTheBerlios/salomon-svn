
package salomon.core.remote.event;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IMasterControllerListener
{
	void controllerAdded(RemoteControllerEvent event);

	void controllerRemoved(RemoteControllerEvent event);
}