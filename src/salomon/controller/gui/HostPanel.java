
package salomon.controller.gui;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import salomon.core.remote.IRemoteController;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class HostPanel
{
	private JList _hostList;

	private DefaultListModel _hostListModel;

	private JPopupMenu _pluginPopup;

	public HostPanel()
	{
		_hostListModel = new DefaultListModel();
		_hostList = new JList(_hostListModel);
	}

	public void addController(IRemoteController controller)
	{
		_hostListModel.addElement(controller);
	}
    
    public void removeController(IRemoteController controller)
    {
        _hostListModel.removeElement(controller);
    }

	/**
	 * @return Returns the hostList.
	 */
	public JComponent getHostPanel()
	{
		return new JScrollPane(_hostList);
	}
}