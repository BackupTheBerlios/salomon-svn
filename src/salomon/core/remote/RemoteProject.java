
package salomon.core.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import salomon.core.project.IProject;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class RemoteProject extends UnicastRemoteObject
		implements IRemoteProject
{

	IProject _project;

	/**
	 *  
	 */
	public RemoteProject(IProject project) throws RemoteException
	{
		_project = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.remote.IRemoteProject#getInfo()
	 */
	public String getInfo() throws RemoteException
	{
		return _project.getInfo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.remote.IRemoteProject#getName()
	 */
	public String getName() throws RemoteException
	{
		return _project.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.remote.IRemoteProject#getProjectID()
	 */
	public int getProjectID() throws RemoteException
	{
		return _project.getProjectID();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.remote.IRemoteProject#setInfo(java.lang.String)
	 */
	public void setInfo(String info) throws RemoteException
	{
		_project.setInfo(info);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.remote.IRemoteProject#setName(java.lang.String)
	 */
	public void setName(String name) throws RemoteException
	{
		_project.setName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.remote.IRemoteProject#setProjectID(int)
	 */
	public void setProjectID(int projectId) throws RemoteException
	{
		_project.setProjectID(projectId);
	}

}