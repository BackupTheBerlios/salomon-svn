
package salomon.controller.gui;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.core.IManagerEngine;
import salomon.core.remote.IRemoteController;
import salomon.core.remote.ManagerEngineProxy;

/**
 * Class is graphic representation of client controllers connected to server.
 * It is used to display them on the list of connected clients. 
 *  
 */
public final class RemoteControllerGUI
{
	private IManagerEngine _managerEngine;

	private IRemoteController _remoteController;

	/**
	 *  
	 */
	public RemoteControllerGUI(IRemoteController remoteController)
	{
		_remoteController = remoteController;
		try {
			_managerEngine = new ManagerEngineProxy(
					_remoteController.getManagerEngine());
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object)
	{
		boolean areEqual = false;
		if (object instanceof RemoteControllerGUI) {
			RemoteControllerGUI controllerGUI = (RemoteControllerGUI) object;
			areEqual = _remoteController.equals(controllerGUI._remoteController);
		} else {
			areEqual = false;
		}

		return areEqual;
	}

	/**
	 * @return Returns the controller.
	 */
	public IRemoteController getController()
	{
		return _remoteController;
	}

	public String getDescription()
	{
		String description = null;

		try {
			description = _remoteController.getDescription();
		} catch (RemoteException e) {
			_logger.fatal("", e);
			description = e.getLocalizedMessage();
		}

		return description;
	}

	public IManagerEngine getManagerEngine()
	{
		return _managerEngine;
	}

	public String getName()
	{
		String name = null;
		try {
			name = _remoteController.getName();
		} catch (RemoteException e) {
			_logger.fatal("", e);
			name = e.getLocalizedMessage();
		}

		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return _remoteController.hashCode();
	}

	public String toString()
	{
		String name;
		try {
			name = _remoteController.getName();
		} catch (RemoteException e) {
			_logger.fatal("", e);
			name = e.getLocalizedMessage();
		}
		return name;
	}
    
    public void exit()
    {
        _logger.debug("RemoteController.exit()");
    	try {
			_remoteController.exit();
		} catch (RemoteException e) {
			_logger.debug(e.getLocalizedMessage());
		}
    }
	
	private static Logger _logger = Logger.getLogger(RemoteControllerGUI.class);

}