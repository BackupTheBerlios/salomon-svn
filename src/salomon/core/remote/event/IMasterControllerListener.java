
package salomon.core.remote.event;

/**
 * An interface used to manage with connection and disconnection from server.
 */
public interface IMasterControllerListener
{
	void controllerAdded(RemoteControllerEvent event);

	void controllerRemoved(RemoteControllerEvent event);
}