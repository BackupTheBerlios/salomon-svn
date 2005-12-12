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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.Starter;
import salomon.engine.controller.gui.action.ActionManager;
import salomon.engine.controller.gui.viewer.SolutionViewer;
import salomon.engine.project.IProject;
import salomon.engine.solution.ISolution;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.solution.Solution;
import salomon.engine.solution.SolutionManager;
import salomon.platform.IUniqueId;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.Utils;

/**
 * Class used to manage with projects editing.
 */
public final class SolutionManagerGUI
{
	/**
	 * 
	 * @uml.property name="_actionManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ActionManager _actionManager;

	private JButton _btnExit;

	private JButton _btnNew;

	private JButton _btnOpen;

	private JComboBox _comboSolutionList;

	private ControllerFrame _parent;

	private JPanel _pnlSolutionController;

	private JPanel _pnlSolutionProperties;

	private JFrame _solutionChooserFrame;

	/**
	 * 
	 * @uml.property name="_solutionManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ISolutionManager _solutionManager;

	private Solution[] _solutions;

	private JFrame _solutionViewerFrame;

	private StatusBar _statusBar;

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

	public void editSolution()
	{
		ISolution solution = null;
		try {
			solution = _solutionManager.getCurrentSolution();
			setSolutionProperties(solution);
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("ERR_CANNOT_EDIT_SOLUTION");
		}
	}

	/**
	 * This method initializes _btnExit
	 * @return JButton
	 */
	public JButton getBtnExit()
	{
		if (_btnExit == null) {
			_btnExit = this.createActionButton(_actionManager.getExitAction(),
					Messages.getString("BTN_EXIT"));
			_btnExit.setMnemonic('e');
		}
		return _btnExit;
	}

	/**
	 * This method initializes _btnNew
	 * @return JButton
	 */
	public JButton getBtnNew()
	{
		if (_btnNew == null) {
			_btnNew = this.createActionButton(
					_actionManager.getNewSolutionAction(),
					Messages.getString("BTN_NEW"));
			_btnNew.setMnemonic('n');
		}
		return _btnNew;
	}

	/**
	 * This method initializes _btnOpen
	 * @return JButton
	 */
	public JButton getBtnOpen()
	{
		if (_btnOpen == null) {
			_btnOpen = this.createActionButton(
					_actionManager.getOpenSolutionAction(),
					Messages.getString("BTN_OPEN"));
			_btnOpen.setMnemonic('o');
		}
		return _btnOpen;
	}

	public JPanel getPanel()
	{
		return _pnlSolutionController;
	}

	public JPanel getSolutionsPanel()
	{
		JPanel panel = new JPanel();
		panel.add(getSolutionsCombo());
		_comboSolutionList.setPreferredSize(new Dimension(150, 25));
		panel.setBorder(BorderFactory.createTitledBorder(Messages.getString("TIT_SOLUTIONS")));
		
		return panel;
	}

	public void newSolution()
	{
		try {
			ISolution solution = _solutionManager.createSolution();
			if (setSolutionProperties(solution)) {
				_solutionManager.addSolution(solution);
				_solutions = (Solution[]) ((SolutionManager) _solutionManager).getSolutions();
				_comboSolutionList.addItem(solution);
				_comboSolutionList.setSelectedItem(solution);
				_comboSolutionList.repaint();
			}
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("ERR_CANNOT_CREATE_SOLUTION");
		}
	}

	public void openSolution()
	{
		boolean approved = false;

		// if _solutionChooserFrame is null
		// the dialog to choose solution should be shown	
		if (_solutionChooserFrame == null) {
			int result = JOptionPane.showConfirmDialog(_parent,
					getSolutionsPanel(), "Choose solution",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			approved = (result == JOptionPane.OK_OPTION);
		} else {
			// if mehod is called during application starting
			// solution was choosen
			approved = true;
		}

		if (approved) {

			int selectedRow = _comboSolutionList.getSelectedIndex();
			final int solutionID = _solutions[selectedRow].getInfo().getId();
			LOGGER.info("chosen solution: " + solutionID);
			//FIXME - do it in better way
			Solution solution;
			try {
				solution = (Solution) _solutionManager.getSolution(new IUniqueId() {
					public int getId()
					{
						return solutionID;
					}
				});
				// forcing connecting to external database
				try {
					solution.getDataEngine();
				} catch (Exception e) {
					LOGGER.fatal("", e);
					Utils.showErrorMessage(Messages.getString("ERR_CANNOT_OPEN_SOLUTION"));
					return;
				}
				LOGGER.info("Connected to external data base");

				IProject project = solution.getProjectManager().createProject();
				solution.getProjectManager().addProject(project);
			} catch (PlatformException e) {
				LOGGER.fatal("", e);
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_CREATE_PROJECT"));
				return;
			}
			_statusBar.setItem(SB_CUR_SOLUTION, solution.getInfo().getName());
		}
		// if method is called at start of application (_solutionChooserFrame == null)
		// the behaviour is diffrent -- main frame shout be shown etc.
		if (_solutionChooserFrame != null) {
			_solutionChooserFrame.setVisible(false);
			_solutionChooserFrame = null;		
		}
		_parent.refreshGui();
		_parent.setVisible(true);
	}

	public void showSolutionChooser()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		JPanel pnlManagerButtons = new JPanel();
		pnlManagerButtons.setLayout(new BoxLayout(pnlManagerButtons,
				BoxLayout.Y_AXIS));
		pnlManagerButtons.add(Box.createVerticalGlue());
		pnlManagerButtons.add(getBtnOpen());
		pnlManagerButtons.add(getBtnNew());
		pnlManagerButtons.add(Box.createVerticalStrut(10));
		pnlManagerButtons.add(getBtnExit());
		pnlManagerButtons.add(Box.createVerticalGlue());

		panel.setLayout(new BorderLayout());
		panel.add(getSolutionsPanel(), BorderLayout.CENTER);
		panel.add(pnlManagerButtons, BorderLayout.EAST);

		_solutionChooserFrame = new JFrame(Messages.getString("TIT_SOLUTIONS"));
		_solutionChooserFrame.setResizable(false);

		_solutionChooserFrame.getContentPane().add(panel);
		_solutionChooserFrame.pack();

		_solutionChooserFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				Starter.exit();
			}
		});

		Point location = new Point();
		location.x = (Toolkit.getDefaultToolkit().getScreenSize().width - _solutionChooserFrame.getWidth()) / 2;
		location.y = (Toolkit.getDefaultToolkit().getScreenSize().height - _solutionChooserFrame.getHeight()) / 2;
		_solutionChooserFrame.setLocation(location);

		_solutionChooserFrame.setVisible(true);
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
	public void setParent(ControllerFrame startframe)
	{
		_parent = startframe;
	}

	/**
	 * Shows dialog which enables to initialize solution settings.
	 * 
	 * @param solution
	 * @throws PlatformException 
	 */
	public boolean setSolutionProperties(ISolution iSolution)
			throws PlatformException
	{
		boolean approved = false;
		Solution solution = (Solution) iSolution;
		if (_pnlSolutionProperties == null) {
			_pnlSolutionProperties = new JPanel();
			_pnlSolutionProperties.setLayout(new GridLayout(6, 2));
			_txtSolutionName = new JTextField();
			_txtSolutionInfo = new JTextField();
			_txtHostname = new JTextField();
			_txtDBPath = new JTextField();
			_txtUsername = new JTextField();
			_txtPasswd = new JPasswordField();
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
		String pass = solution.getInfo().getPasswd();
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
			solution.getInfo().setPasswd(_txtPasswd.getText());
			_solutionManager.addSolution(iSolution);
			_statusBar.setItem(SB_CUR_SOLUTION, solution.getInfo().getName());
			approved = true;
		}
		return approved;
	}

	/**
	 * Set the value of statusBar field.
	 * @param statusBar The statusBar to set
	 */
	public void setStatusBar(StatusBar statusBar)
	{
		_statusBar = statusBar;
		_statusBar.addItem(SB_CUR_SOLUTION);
	}

	public void viewSolutionList()
	{

		if (_solutionViewerFrame == null) {
			_solutionViewerFrame = new JFrame(
					Messages.getString("TIT_SOLUTIONS"));
			_solutionViewerFrame.getContentPane().add(
					new SolutionViewer(
							((SolutionManager) _solutionManager).getDBManager()));
			_solutionViewerFrame.pack();
		}

		_solutionViewerFrame.setVisible(true);
	}

	private JButton createActionButton(Action action, String text)
	{
		JButton button = new JButton(action);
		button.setText(text);
		Dimension dim = new Dimension(75, 25);
		button.setPreferredSize(dim);
		button.setMinimumSize(dim);
		button.setMaximumSize(dim);
		return button;
	}

	private JComboBox getSolutionsCombo()
	{
		if (_comboSolutionList == null) {
			try {
				_solutions = (Solution[]) ((SolutionManager) _solutionManager).getSolutions();
				_comboSolutionList = new JComboBox(_solutions);
			} catch (Exception e) {
				LOGGER.fatal("", e);
				Utils.showErrorMessage("Cannot load solution list.");
			}
		}
		return _comboSolutionList;
	}

	private static final Logger LOGGER = Logger.getLogger(SolutionManagerGUI.class);

	private static final String SB_CUR_SOLUTION = "TT_CURRENT_SOLUTION";
}