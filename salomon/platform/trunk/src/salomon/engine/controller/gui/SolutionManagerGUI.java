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
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

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
import javax.swing.SwingUtilities;

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

import salomon.util.gui.Utils;

import salomon.platform.IUniqueId;
import salomon.platform.exception.PlatformException;

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

	private JButton _btnEdit;

	private JButton _btnExit;

	private JButton _btnNew;

	private JButton _btnOpen;

	private JComboBox _comboSolutionList;

	private JFrame _parent;

	private JPanel _pnlManagerButtons;

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

	public int chooseSolution()
	{
		int solutionID = 0;

		try {
			//FIXME ugly but quick
			_solutions = (Solution[]) ((SolutionManager) _solutionManager).getSolutions();
			String[] solutionNames = new String[_solutions.length];

			int i;
			for (i = 0; i < _solutions.length; i++) {
				solutionNames[i] = _solutions[i].getInfo().getName();
			}

			_comboSolutionList = new JComboBox(solutionNames);
			_comboSolutionList.setSelectedIndex(0);
			solutionID = showSolutionList();
		} catch (Exception e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot load solution list.");
		}
		_solutionChooserFrame.setVisible(true);
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
			int selectedRow = _comboSolutionList.getSelectedIndex();
			if (selectedRow >= 0) {

				final int solutionID = _solutions[selectedRow].getInfo().getId();

				if (solutionID > 0) {
					solution = _solutionManager.getSolution(new IUniqueId() {
						public int getId()
						{
							return solutionID;
						}
					});
					setSolutionProperties(solution);
				}
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
			LOGGER.info("Solutions " + solutions.size());

			_solutions = (Solution[]) ((SolutionManager) _solutionManager).getSolutions();
			String[] solutionNames = new String[_solutions.length];
			int i;
			for (i = 0; i < _solutions.length; i++) {
				solutionNames[i] = _solutions[i].getInfo().getName();
			}
			_comboSolutionList.addItem((((Solution) solution).getInfo().getName()));
			_comboSolutionList.addItem(solution);
			_comboSolutionList.repaint();
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot create solution");
		}
	}

	public void openSolution()
	{
		
		if (!_solutionChooserFrame.isVisible()) {
			_solutionChooserFrame.setVisible(true);
		}

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
			IProject project = solution.getProjectManager().createProject();
			solution.getProjectManager().addProject(project);
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("ERR_CANNOT_CREATE_PROJECT");
			return;
		}
		_statusBar.setItem(SB_CUR_SOLUTION, solution.getInfo().getName());
		_solutionChooserFrame.setVisible(false);		
		_parent.setVisible(true);		
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
		}
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

	private JFrame getSolutionController()
	{

		if (_solutionChooserFrame == null) {
			_pnlSolutionController = new JPanel();
			_pnlSolutionController.setLayout(new BorderLayout());
			_pnlSolutionController.add(getPnlManagerButtons(),
					BorderLayout.SOUTH);
			_pnlSolutionController.add(_comboSolutionList, BorderLayout.CENTER);
			_solutionChooserFrame = new JFrame(
					Messages.getString("TIT_SOLUTIONS"));
			Point location = new Point();
			location.x = (Toolkit.getDefaultToolkit().getScreenSize().width - _solutionChooserFrame.getWidth()) / 2;
			location.y = (Toolkit.getDefaultToolkit().getScreenSize().height - _solutionChooserFrame.getHeight()) / 2;
			_solutionChooserFrame.setLocation(location);
			_solutionChooserFrame.getContentPane().add(_pnlSolutionController);
			_solutionChooserFrame.pack();
			_solutionChooserFrame.addWindowListener( new WindowAdapter(){
				  public void windowClosing(WindowEvent e) {Starter.exit();}
			});
		}
		return _solutionChooserFrame;
	}

	private int showSolutionList()
	{
		int solutionID = 0;

		if (_solutionChooserFrame == null) {
			_pnlSolutionController = new JPanel();
			_pnlSolutionController.setLayout(new BorderLayout());
			_pnlSolutionController.add(getPnlManagerButtons(),
					BorderLayout.SOUTH);
			_pnlSolutionController.add(_comboSolutionList, BorderLayout.CENTER);
			_solutionChooserFrame = new JFrame(
					Messages.getString("TIT_SOLUTIONS"));
			Point location = new Point();
			location.x = (Toolkit.getDefaultToolkit().getScreenSize().width - _solutionChooserFrame.getWidth()) / 2;
			location.y = (Toolkit.getDefaultToolkit().getScreenSize().height - _solutionChooserFrame.getHeight()) / 2;
			_solutionChooserFrame.setLocation(location);
			_solutionChooserFrame.getContentPane().add(_pnlSolutionController);
			_solutionChooserFrame.pack();
			_solutionChooserFrame.addWindowListener( new WindowAdapter(){
				  public void windowClosing(WindowEvent e) {Starter.exit();}
			});
			
		}
		return solutionID;
	}

	private static final Logger LOGGER = Logger.getLogger(SolutionManagerGUI.class);

	private static final String SB_CUR_SOLUTION = "TT_CURRENT_SOLUTION";
}