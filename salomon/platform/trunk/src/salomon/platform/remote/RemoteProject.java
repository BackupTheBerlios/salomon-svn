
package salomon.platform.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import salomon.platform.project.IProject;

/** 
 * Class representing remote instance of IProject.  
 */
public final class RemoteProject extends UnicastRemoteObject
		implements IRemoteProject
{

	IProject _project;

	/**
     * @pre project != null
     * @post $none
     */
	public RemoteProject(IProject project) throws RemoteException
	{
		_project = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.remote.IRemoteProject#getInfo()
	 */
	public String getInfo() throws RemoteException
	{
		return _project.getInfo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.remote.IRemoteProject#getName()
	 */
	public String getName() throws RemoteException
	{
		return _project.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.remote.IRemoteProject#getProjectID()
	 */
	public int getProjectID() throws RemoteException
	{
		return _project.getProjectID();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.remote.IRemoteProject#setInfo(java.lang.String)
	 */
	public void setInfo(String info) throws RemoteException
	{
		_project.setInfo(info);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.remote.IRemoteProject#setName(java.lang.String)
	 */
	public void setName(String name) throws RemoteException
	{
		_project.setName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.remote.IRemoteProject#setProjectID(int)
	 */
	public void setProjectID(int projectId) throws RemoteException
	{
		_project.setProjectID(projectId);
	}

}