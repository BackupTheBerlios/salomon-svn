/*
 * Created on 2004-05-27
 *
 */

package salomon.controller.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.apache.log4j.Logger;

/**
 * @author nico Class agregates all buttons used in GUIController.
 */
public class GUIButtons
{
	private static Logger _logger = Logger.getLogger(GUIButtons.class);

	private JButton _btnAdd = null;

	private JButton _btnAddAll = null;

	private JButton _btnDown = null;

	private JButton _btnFirst = null;

	private JButton _btnLast = null;

	private JButton _btnRemove = null;

	private JButton _btnRemoveAll = null;

	private JButton _btnUp = null;

	private ManipulationListener _manipulationListener = null;

	private TaskEditionManager _taskEditionManager = null;

	public GUIButtons()
	{
		_manipulationListener = new ManipulationListener();
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
			_btnAdd = createManipulationButton(">"); //$NON-NLS-1$
		}
		return _btnAdd;
	}

	/**
	 * This method initializes _btnAddAll
	 * 
	 * @return JButton
	 */
	JButton getBtnAddAll()
	{
		if (_btnAddAll == null) {
			_btnAddAll = createManipulationButton(">>"); //$NON-NLS-1$
		}
		return _btnAddAll;
	}

	/**
	 * This method initializes _btnDown
	 * 
	 * @return JButton
	 */
	JButton getBtnDown()
	{
		if (_btnDown == null) {
			_btnDown = createManipulationButton("v"); //$NON-NLS-1$
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
			_btnFirst = createManipulationButton("^^"); //$NON-NLS-1$
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
			_btnLast = createManipulationButton("vv"); //$NON-NLS-1$
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
			_btnRemove = createManipulationButton("X"); //$NON-NLS-1$
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
			_btnRemoveAll = createManipulationButton("XX"); //$NON-NLS-1$
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
			_btnUp = createManipulationButton("^"); //$NON-NLS-1$
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
		button.setText(text);
		button.setFont(new Font("Dialog", Font.BOLD, 10)); //$NON-NLS-1$
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
			} else if (object == _btnAddAll) {
				// TODO:
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
}