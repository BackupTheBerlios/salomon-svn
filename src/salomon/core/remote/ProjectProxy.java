
package salomon.core.remote;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.core.project.IProject;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class ProjectProxy implements IProject
{
	private IRemoteProject _remoteProject;

	/**
	 *  
	 */
	public ProjectProxy(IRemoteProject remoteProject)
	{
		_remoteProject = remoteProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.project.IProject#getInfo()
	 */
	public String getInfo()
	{
		String info = null;
		try {
			info = _remoteProject.getInfo();
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
		return info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.project.IProject#getName()
	 */
	public String getName()
	{
		String name = null;
		try {
			name = _remoteProject.getName();
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.project.IProject#getProjectID()
	 */
	public int getProjectID()
	{
		int id = -1;
		try {
			id = _remoteProject.getProjectID();
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.project.IProject#setInfo(java.lang.String)
	 */
	public void setInfo(String info)
	{
		try {
			_remoteProject.setInfo(info);
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.project.IProject#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		try {
			_remoteProject.setName(name);
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.project.IProject#setProjectID(int)
	 */
	public void setProjectID(int projectId)
	{
		try {
			_remoteProject.setProjectID(projectId);
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}

	}

	private static Logger _logger = Logger.getLogger(ProjectProxy.class);

}