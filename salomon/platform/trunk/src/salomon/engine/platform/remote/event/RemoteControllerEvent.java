
package salomon.platform.remote.event;

import java.util.EventObject;

import salomon.platform.remote.IRemoteController;
import salomon.platform.remote.CentralController;

/**
 * An event used to notify about client's connection or disconnection from
 * server.
 *  
 */
public final class RemoteControllerEvent extends EventObject
{
	private IRemoteController _controller;

	/**
	 * @pre $none
	 * @post $none
	 */
	public RemoteControllerEvent(CentralController source,
			IRemoteController controller)
	{
		super(source);
		_controller = controller;

	}

	public IRemoteController getController()
	{
		return _controller;
	}
}