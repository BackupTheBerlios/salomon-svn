
package salomon.controller.gui;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import salomon.core.holder.ManagerEngineHolder;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class RemoteControllerPanel
{

	private JList _controllerList;

	private DefaultListModel _controllerListModel;

	private ManagerEngineHolder _engineHolder;

	private ControllerFrame _parent;

	private JPopupMenu _pluginPopup;

	public RemoteControllerPanel(ManagerEngineHolder engineHolder)
	{
		_engineHolder = engineHolder;
		_controllerListModel = new DefaultListModel();
		_controllerList = new JList(_controllerListModel);
		_controllerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_controllerList.addListSelectionListener(new RemoteControllerSelectionListener());
	}

	public void addController(RemoteControllerGUI controller)
	{
		_controllerListModel.addElement(controller);
		_logger.debug("controller added.");
	}

	/**
	 * @return Returns the hostList.
	 */
	public JComponent getControllerPanel()
	{
		return new JScrollPane(_controllerList);
	}

	public void removeController(RemoteControllerGUI controller)
	{
		_controllerListModel.removeElement(controller);
		_logger.debug("controller removed.");
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent(ControllerFrame parent)
	{
		_parent = parent;
	}

	private final class RemoteControllerSelectionListener
			implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			RemoteControllerGUI controllerGUI = (RemoteControllerGUI) ((JList) e.getSource()).getSelectedValue();
			_engineHolder.setCurrentManager(controllerGUI.getManagerEngine());
            _parent.refreshGui();
		}

	}

	private static Logger _logger = Logger.getLogger(RemoteControllerPanel.class);
}