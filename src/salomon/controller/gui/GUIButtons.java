/*
 * Created on 2004-05-27
 *
 */

package salomon.controller.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.apache.log4j.Logger;

import salomon.core.Config;

/**
 * @author nico Class agregates all buttons used in GUIController.
 */
public final class GUIButtons
{

	private JButton _btnAdd = null;

	private JButton _btnDown = null;

	private JButton _btnFirst = null;

	private JButton _btnLast = null;

	private JButton _btnRemove = null;

	private JButton _btnRemoveAll = null;

	private JButton _btnUp = null;

	private ManipulationListener _manipulationListener = null;

	private String _resourcesDir = null;

	private TaskEditionManager _taskEditionManager = null;

	public GUIButtons(TaskEditionManager taskEditionManager)
	{
		_taskEditionManager = taskEditionManager;
		_manipulationListener = new ManipulationListener();
		_resourcesDir = Config.getString("RESOURCES_DIR");
	}

	public void setEditionManager(TaskEditionManager manager)
	{
		_taskEditionManager = manager;
	}

	/**
	 * This method initializes _btnAdd
	 * 
	 * @return JButton
	 */
	JButton getBtnAdd()
	{
		if (_btnAdd == null) {
			_btnAdd = createManipulationButton(Config.getString("ICO_TASK_ADD")); //$NON-NLS-1$
		}
		return _btnAdd;
	}

	/**
	 * This method initializes _btnDown
	 * 
	 * @return JButton
	 */
	JButton getBtnDown()
	{
		if (_btnDown == null) {
			_btnDown = createManipulationButton(Config.getString("ICO_TASK_DOWN")); //$NON-NLS-1$
		}
		return _btnDown;
	}

	/**
	 * This method initializes _btnFirst
	 * 
	 * @return JButton
	 */
	JButton getBtnFirst()
	{
		if (_btnFirst == null) {
			_btnFirst = createManipulationButton(Config.getString("ICO_TASK_FIRST")); //$NON-NLS-1$
		}
		return _btnFirst;
	}

	/**
	 * This method initializes _btnLast
	 * 
	 * @return JButton
	 */
	JButton getBtnLast()
	{
		if (_btnLast == null) {
			_btnLast = createManipulationButton(Config.getString("ICO_TASK_LAST")); //$NON-NLS-1$
		}
		return _btnLast;
	}

	/**
	 * This method initializes _btnRemove
	 * 
	 * @return JButton
	 */
	JButton getBtnRemove()
	{
		if (_btnRemove == null) {
			_btnRemove = createManipulationButton(Config.getString("ICO_TASK_REMOVE")); //$NON-NLS-1$
		}
		return _btnRemove;
	}

	/**
	 * This method initializes _btnRemoveAll
	 * 
	 * @return JButton
	 */
	JButton getBtnRemoveAll()
	{
		if (_btnRemoveAll == null) {
			_btnRemoveAll = createManipulationButton(Config.getString("ICO_TASK_REMOVEALL")); //$NON-NLS-1$
		}
		return _btnRemoveAll;
	}

	/**
	 * This method initializes _btnUp
	 * 
	 * @return JButton
	 */
	JButton getBtnUp()
	{
		if (_btnUp == null) {
			_btnUp = createManipulationButton(Config.getString("ICO_TASK_UP")); //$NON-NLS-1$
		}
		return _btnUp;
	}

	/**
	 * Creates button with given text and standard settings
	 * 
	 * @param text
	 * @return
	 */
	private JButton createManipulationButton(String text)
	{
		JButton button = new JButton();
		button.setIcon(new ImageIcon(_resourcesDir + Config.FILE_SEPARATOR
				+ text));
		button.addActionListener(_manipulationListener);
		Dimension dim = new Dimension(50, 25);
		button.setPreferredSize(dim);
		button.setMinimumSize(dim);
		button.setMaximumSize(dim);
		return button;
	}

	/**
	 * 
	 * @author nico
	 * 
	 * Class handles events from buttons, which are used to manage tasks.
	 */
	private class ManipulationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object object = e.getSource();
			if (object == _btnAdd) {
				_taskEditionManager.addTask();
			} else if (object == _btnRemove) {
				_taskEditionManager.removeTask();
			} else if (object == _btnRemoveAll) {
				//TODO:
			} else if (object == _btnUp) {
				_taskEditionManager.moveUp();
			} else if (object == _btnDown) {
				_taskEditionManager.moveDown();
			} else if (object == _btnFirst) {
				//TODO:
			} else if (object == _btnLast) {
				//TODO:
			} else {
				_logger.error("Not supported button: " + object); //$NON-NLS-1$
			}
		}
	}
	private static Logger _logger = Logger.getLogger(GUIButtons.class);
}