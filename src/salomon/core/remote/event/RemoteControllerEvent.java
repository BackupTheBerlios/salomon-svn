
package salomon.core.remote.event;

import java.util.EventObject;

import salomon.core.remote.IRemoteController;
import salomon.core.remote.MasterController;

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
	public RemoteControllerEvent(MasterController source,
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