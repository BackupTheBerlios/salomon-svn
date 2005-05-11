/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.controller.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.controller.gui.action.ActionManager;
import salomon.engine.project.IProject;
import salomon.engine.solution.ISolution;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.solution.Solution;
import salomon.engine.solution.SolutionManager;

import salomon.util.gui.Utils;

import salomon.platform.exception.PlatformException;

/**
 * Class used to manage with projects editing.
 * 
 */
public final class SolutionManagerGUI
{
	private ActionManager _actionManager;

	private JButton _btnEdit;

	private JButton _btnExit;

	private JButton _btnNew;

	private JButton _btnOpen;

	private JFrame _parent;

	private JPanel _pnlManagerButtons;

	private JPanel _pnlSolutionController;

	private JPanel _pnlSolutionProperties;

	private ISolutionManager _solutionManager;

	private JTable _tblSolutionList;

	private JTextField _txtDBPath;

	private JTextField _txtHostname;

	private JTextField _txtPasswd;

	private JTextField _txtSolutionInfo;

	private JTextField _txtSolutionName;

	private JTextField _txtUsername;

	/**
	 */
	public SolutionManagerGUI(ISolutionManager solutionManager)
	{
		_solutionManager = solutionManager;
	}

	public int chooseSolution()
	{
		int solutionID = 0;

		try {
			//FIXME ugly but quick
			Collection solutions = ((SolutionManager) _solutionManager).getSolutionList();
			if (_tblSolutionList == null) {
				_tblSolutionList = Utils.createResultTable(solutions);
			}
			solutionID = showSolutionList();
		} catch (Exception e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot load solution list.");
		}
		int result = JOptionPane.showConfirmDialog(getSolutionController(),
				_pnlSolutionController, "Choose solution",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			int selectedRow = _tblSolutionList.getSelectedRow();
			if (selectedRow >= 0) {
				solutionID = ((Integer) _tblSolutionList.getValueAt(
						selectedRow, 0)).intValue();
				LOGGER.info("SolutionManagerGUI.chooseSolution() chosen solution: "
						+ solutionID);
			}
		}
		return solutionID;
	}

	public void chooseSolutionOnStart()
	{
		if (SwingUtilities.isEventDispatchThread()) {
			chooseSolution();
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run()
					{
						chooseSolution();
					}
				});
			} catch (InterruptedException e) {
				LOGGER.fatal("", e);
			} catch (InvocationTargetException e) {
				LOGGER.fatal("", e);
			}
		}
	}

	public void editSolution()
	{
		try {
			ISolution solution = null;
			int solutionID = 0;
			int selectedRow = _tblSolutionList.getSelectedRow();
			if (selectedRow >= 0) {
				solutionID = ((Integer) _tblSolutionList.getValueAt(
						selectedRow, 0)).intValue();
			}
			if (solutionID > 0) {
				solution = _solutionManager.getSolution(solutionID);
				setSolutionProperties(solution);
				Collection solutions = ((SolutionManager) _solutionManager).getSolutionList();
				LOGGER.info("Solutionow " + solutions.size());
				_tblSolutionList = Utils.createResultTable(solutions);
				_pnlSolutionController.removeAll();
				_pnlSolutionController.add(getPnlManagerButtons(),
						BorderLayout.SOUTH);
				_pnlSolutionController.add(_tblSolutionList,
						BorderLayout.CENTER);
				_parent.setVisible(true);
			}
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot create solution");
		}
	}

	public JPanel getPanel()
	{
		return _pnlSolutionController;
	}

	public void newSolution()
	{
		try {
			ISolution solution = _solutionManager.createSolution();
			setSolutionProperties(solution);
			Collection solutions = ((SolutionManager) _solutionManager).getSolutionList();
			LOGGER.info("Solutionow " + solutions.size());
			_tblSolutionList = Utils.createResultTable(solutions);
			_pnlSolutionController.removeAll();
			_pnlSolutionController.add(getPnlManagerButtons(),
					BorderLayout.SOUTH);
			_pnlSolutionController.add(_tblSolutionList, BorderLayout.CENTER);
			_parent.setVisible(true);
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot create solution");
		}
	}

	public void openSolution()
	{
		try {
			int solutionID = 0;
			int selectedRow = _tblSolutionList.getSelectedRow();
			if (selectedRow >= 0) {
				solutionID = ((Integer) _tblSolutionList.getValueAt(
						selectedRow, 0)).intValue();
			}
			if (solutionID > 0) {
				//FIXME - do it in better way
				Solution solution = (Solution) _solutionManager.getSolution(solutionID);
				//_parent.setVisible(false);
				//setParent(_nextFrame);
				//_nextFrame.setVisible(true);
				// Creates a new empty project				
				try {
					IProject project = solution.getProjectManager().createProject();
					solution.getProjectManager().addProject(project);
				} catch (PlatformException e) {
					LOGGER.fatal("", e);
					Utils.showErrorMessage("ERR_CANNOT_CREATE_PROJECT");
					return;
				}
			}
		} catch (Exception e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot load solution.");
		}
	}

	public void saveSolution()
	{
		try {
			Solution solution = (Solution) _solutionManager.getCurrentSolution();

			// setting solution name if neccessary
			// TODO: remove this checking, make user to enter solution name while
			// creating it
			if (solution.getInfo().getName() == null) {
				setSolutionProperties(solution);
			}

		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("ERR_CANNOT_SAVE_SOLUTION");
		}
	}

	public void setActionManager(ActionManager actionManager)
	{
		_actionManager = actionManager;
	}

	/**
	 * @param startframe The parent to set.
	 */
	public void setParent(JFrame startframe)
	{
		_parent = startframe;
	}

	/**
	 * Shows dialog which enables to initialize solution settings.
	 * 
	 * @param solution
	 * @throws PlatformException 
	 */
	public void setSolutionProperties(ISolution iSolution)
			throws PlatformException
	{
		Solution solution = (Solution) iSolution;
		if (_pnlSolutionProperties == null) {
			_pnlSolutionProperties = new JPanel();
			_pnlSolutionProperties.setLayout(new GridLayout(6, 2));
			_txtSolutionName = new JTextField();
			_txtSolutionInfo = new JTextField();
			_txtHostname = new JTextField();
			_txtDBPath = new JTextField();
			_txtUsername = new JTextField();
			_txtPasswd = new JTextField();
			_pnlSolutionProperties.add(new JLabel("Solution name"));
			_pnlSolutionProperties.add(_txtSolutionName);
			_pnlSolutionProperties.add(new JLabel("Solution info"));
			_pnlSolutionProperties.add(_txtSolutionInfo);
			_pnlSolutionProperties.add(new JLabel("Hostname"));
			_pnlSolutionProperties.add(_txtHostname);
			_pnlSolutionProperties.add(new JLabel("DB Path"));
			_pnlSolutionProperties.add(_txtDBPath);
			_pnlSolutionProperties.add(new JLabel("Username"));
			_pnlSolutionProperties.add(_txtUsername);
			_pnlSolutionProperties.add(new JLabel("Password"));
			_pnlSolutionProperties.add(_txtPasswd);
		}

		String name = solution.getInfo().getName();
		String info = solution.getInfo().getInfo();
		String host = solution.getInfo().getHost();
		String path = solution.getInfo().getPath();
		String user = solution.getInfo().getUser();
		String pass = solution.getInfo().getPass();
		_txtSolutionName.setText(name == null ? "" : name);
		_txtSolutionInfo.setText(info == null ? "" : info);
		_txtHostname.setText(host == null ? "" : host);
		_txtDBPath.setText(path == null ? "" : path);
		_txtUsername.setText(user == null ? "" : user);
		_txtPasswd.setText(pass == null ? "" : pass);

		// TODO:
		int result = JOptionPane.showConfirmDialog(_parent,
				_pnlSolutionProperties, "Enter solution properties",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			solution.getInfo().setName(_txtSolutionName.getText());
			solution.getInfo().setInfo(_txtSolutionInfo.getText());
			solution.getInfo().setHost(_txtHostname.getText());
			solution.getInfo().setPath(_txtDBPath.getText());
			solution.getInfo().setUser(_txtUsername.getText());
			solution.getInfo().setPass(_txtPasswd.getText());
			_solutionManager.addSolution(iSolution);
		}
	}

	/**
	 * This method initializes _btnEdit
	 * @return JButton
	 */
	JButton getBtnEdit()
	{
		if (_btnEdit == null) {
			_btnEdit = new JButton(_actionManager.getEditSolutionAction());
			_btnEdit.setText(Messages.getString("BTN_EDIT")); //$NON-NLS-1$
		}
		return _btnEdit;
	}

	/**
	 * This method initializes _btnExit
	 * @return JButton
	 */
	JButton getBtnExit()
	{
		if (_btnExit == null) {
			_btnExit = new JButton(_actionManager.getExitAction());
			_btnExit.setText(Messages.getString("BTN_EXIT")); //$NON-NLS-1$
		}
		return _btnExit;
	}

	/**
	 * This method initializes _btnNew
	 * @return JButton
	 */
	JButton getBtnNew()
	{
		if (_btnNew == null) {
			_btnNew = new JButton(_actionManager.getNewSolutionAction());
			_btnNew.setText(Messages.getString("BTN_NEW")); //$NON-NLS-1$
		}
		return _btnNew;
	}

	/**
	 * This method initializes _btnOpen
	 * @return JButton
	 */
	JButton getBtnOpen()
	{
		if (_btnOpen == null) {
			_btnOpen = new JButton(_actionManager.getOpenSolutionAction());
			_btnOpen.setText(Messages.getString("BTN_OPEN")); //$NON-NLS-1$
		}
		return _btnOpen;
	}

	/**
	 * This method initializes _pnlManagerButtons
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlManagerButtons()
	{
		if (_pnlManagerButtons == null) {
			_pnlManagerButtons = new JPanel();
			_pnlManagerButtons.setLayout(new BoxLayout(_pnlManagerButtons,
					BoxLayout.X_AXIS));
			_pnlManagerButtons.add(Box.createHorizontalGlue());
			_pnlManagerButtons.add(getBtnNew());
			_pnlManagerButtons.add(Box.createHorizontalStrut(10));
			_pnlManagerButtons.add(getBtnEdit());
			_pnlManagerButtons.add(Box.createHorizontalStrut(10));
			_pnlManagerButtons.add(getBtnOpen());
			_pnlManagerButtons.add(Box.createHorizontalStrut(10));
			_pnlManagerButtons.add(getBtnExit());
			_pnlManagerButtons.add(Box.createHorizontalGlue());
		}
		return _pnlManagerButtons;
	}

	private JPanel getSolutionController()
	{
		if (_pnlSolutionController == null) {
			_pnlSolutionController = new JPanel();
			_pnlSolutionController.setLayout(new BorderLayout());
			_pnlSolutionController.add(getPnlManagerButtons(),
					BorderLayout.SOUTH);
			_pnlSolutionController.add(_tblSolutionList, BorderLayout.CENTER);
		}
		return _pnlSolutionController;
	}

	private int showSolutionList()
	{
		int solutionID = 0;

		if (_pnlSolutionController == null) {
			_pnlSolutionController = new JPanel();
			_pnlSolutionController.setLayout(new BorderLayout());
			_pnlSolutionController.add(getPnlManagerButtons(),
					BorderLayout.SOUTH);
			_pnlSolutionController.add(_tblSolutionList, BorderLayout.CENTER);
		}

		//		int result = JOptionPane.showConfirmDialog(_pnlSolutionController, _pnlSolutionController,
		//				"Choose solution", JOptionPane.DEFAULT_OPTION,
		//				JOptionPane.PLAIN_MESSAGE);
		//		if (result == JOptionPane.OK_OPTION) {
		//			int selectedRow = _tblSolutionList.getSelectedRow();
		//			if (selectedRow >= 0) {
		//				solutionID = ((Integer) _tblSolutionList.getValueAt(selectedRow, 0)).intValue();
		//			}
		//		} 

		return solutionID;
	}

	private static final Logger LOGGER = Logger.getLogger(SolutionManagerGUI.class);
}