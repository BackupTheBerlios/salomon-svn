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

package salomon.engine.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import salomon.engine.Config;
import salomon.engine.Messages;
import salomon.engine.Resources;
import salomon.engine.SQLConsole;
import salomon.engine.Starter;
import salomon.engine.controller.gui.ControllerFrame;
import salomon.engine.controller.gui.ControllerPanel;
import salomon.engine.controller.gui.PluginMangerGUI;
import salomon.engine.controller.gui.ProjectManagerGUI;
import salomon.engine.controller.gui.SolutionManagerGUI;
import salomon.engine.controller.gui.SplashScreen;
import salomon.engine.controller.gui.TaskManagerGUI;
import salomon.engine.controller.gui.action.ActionManager;
import salomon.engine.controller.gui.viewer.ProjectViewer;
import salomon.engine.project.IProject;

import salomon.util.gui.Utils;

import salomon.platform.exception.PlatformException;

import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.ManagerEngine;

/**
 * Local implementation of IController interface.
 */
public final class LocalController implements IController
{
	private ActionManager _actionManager;

	private ControllerPanel _contentPane;

	private LocalGUIMenu _guiMenu;

	private IManagerEngine _managerEngine;

	private JMenuBar _menuBar;

	private PluginMangerGUI _pluginMangerGUI;

	private ProjectManagerGUI _projectManagerGUI;
	
	private SolutionManagerGUI _solutionManagerGUI;

	private TaskManagerGUI _taskManagerGUI;

	private JToolBar _toolBar;

	/**
	 * 
	 * @see salomon.engine.controller.IController#exit()
	 */
	public void exit()
	{
		//nothing to do
	}

	/**
	 * @see salomon.engine.controller.IController#start(salomon.engine.platform.IManagerEngine)
	 */
	public void start(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;				
		SplashScreen.show();
		//FIXME: add cascade model support
		try {
			_solutionManagerGUI = new SolutionManagerGUI(
					_managerEngine.getSolutionManager());			
			_projectManagerGUI = new ProjectManagerGUI(
					_managerEngine.getProjectManager());
			_taskManagerGUI = new TaskManagerGUI(
					_managerEngine.getTasksManager());
			_pluginMangerGUI = new PluginMangerGUI(
					_managerEngine.getPluginManager());
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("ERR_CANNOT_SHOW_GUI");
			return;
		}		

		_actionManager = new ActionManager(_solutionManagerGUI, _projectManagerGUI, _taskManagerGUI,
				_pluginMangerGUI);		
		
		_guiMenu = new LocalGUIMenu(_actionManager);
		ControllerFrame frame = new ControllerFrame();
		_solutionManagerGUI.setParent(frame);		
		_projectManagerGUI.setParent(frame);
		_taskManagerGUI.setParent(frame);
		
		_solutionManagerGUI.setActionManager(_actionManager);
		_pluginMangerGUI.setActionManager(_actionManager);
		_taskManagerGUI.setActionManager(_actionManager);
		_projectManagerGUI.setTaskManagerGUI(_taskManagerGUI);
		
		SplashScreen.hide();
		// choosing solution, add support for exiting application
		// if user doesn't choose solution
		_solutionManagerGUI.chooseSolutionOnStart();
		// loading plugins
		_pluginMangerGUI.refresh();
		_taskManagerGUI.refresh();
		
		frame.setContentPane(getJContentPane());
		frame.setJMenuBar(getJMenuBar());
		frame.setJToolBar(getToolBar());
		frame.setControllerPanel(_contentPane);
		Utils.setParent(getJContentPane());		
		frame.setVisible(true);
	}

	public JComponent getJContentPane()
	{
		if (_contentPane == null) {
			_contentPane = new ControllerPanel(_taskManagerGUI,
					_pluginMangerGUI, _actionManager);
		}
		return _contentPane;
	}

	public JMenuBar getJMenuBar()
	{
		if (_menuBar == null) {
			_menuBar = new JMenuBar();
			JMenu solution = new JMenu(Messages.getString("MNU_SOLUTION")); //$NON-NLS-1$
			solution.add(_guiMenu.getItmNewSolution());
			solution.add(_guiMenu.getItmOpenSolution());				
			solution.add(_guiMenu.getItmSaveSolution());
			solution.addSeparator();
			solution.add(_guiMenu.getItmExit());
			JMenu project = new JMenu(Messages.getString("MNU_PROJECT")); //$NON-NLS-1$
			project.add(_guiMenu.getItmNewProject());
			project.add(_guiMenu.getItmOpenProject());
			project.add(_guiMenu.getItmSaveProject());
			JMenu tools = new JMenu(Messages.getString("MNU_TOOLS")); //$NON-NLS-1$
			tools.add(_guiMenu.getItmSQLConsole());
			JMenu help = new JMenu(Messages.getString("MNU_HELP")); //$NON-NLS-1$           
			help.add(_guiMenu.getItmAbout());
			_menuBar.add(solution);
			_menuBar.add(project);
			_menuBar.add(tools);
			_menuBar.add(help);
		}
		return _menuBar;
	}

	public JToolBar getToolBar()
	{
		if (_toolBar == null) {
			_toolBar = new JToolBar();
			_toolBar.add(_guiMenu.getBtnNewSolution());
			_toolBar.add(_guiMenu.getBtnOpenSolution());
			_toolBar.add(_guiMenu.getBtnSaveSolution());
		}
		return _toolBar;
	}

	private final class LocalGUIMenu
	{

		private JButton _btnNewSolution;

		private JButton _btnOpenSolution;

		private JButton _btnSaveSolution;

		private JButton _btnNewProject;

		private JButton _btnOpenProject;

		private JButton _btnSaveProject;
		
		private JMenuItem _itmAbout;

		private JMenuItem _itmExit;

		private JMenuItem _itmNewSolution;

		private JMenuItem _itmOpenSolution;

		private JMenuItem _itmSaveSolution;

		private JMenuItem _itmNewProject;

		private JMenuItem _itmOpenProject;

		private JMenuItem _itmSaveProject;
		
		private JMenuItem _itmSQLConsole;

		private JPanel _pnlAbout;

		private JComponent _positionComponent;

		private String _resourcesDir;

		/**
		 * creates LocalGUIMenu
		 * @param actionManager
		 */
		public LocalGUIMenu(ActionManager actionManager)
		{
			_actionManager = actionManager;
			_resourcesDir = Config.getString("RESOURCES_DIR");
		}

		JButton getBtnNewSolution()
		{
			if (_btnNewSolution == null) {
				_btnNewSolution = new JButton(_actionManager.getNewSolutionAction());
				_btnNewSolution.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_SOLUTION_NEW"))); //$NON-NLS-1$
			}
			return _btnNewSolution;
		}

		JButton getBtnOpenSolution()
		{
			if (_btnOpenSolution == null) {
				_btnOpenSolution = new JButton(_actionManager.getOpenSolutionAction());
				_btnOpenSolution.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_SOLUTION_OPEN"))); //$NON-NLS-1$                
			}
			return _btnOpenSolution;
		}

		JButton getBtnSaveSolution()
		{
			if (_btnSaveSolution == null) {
				_btnSaveSolution = new JButton(_actionManager.getSaveSolutionAction());
				_btnSaveSolution.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_SOLUTION_SAVE"))); //$NON-NLS-1$
			}
			return _btnSaveSolution;
		}

		JButton getBtnNewProject()
		{
			if (_btnNewProject == null) {
				_btnNewProject = new JButton(_actionManager.getNewProjectAction());
				_btnNewProject.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_PROJECT_NEW"))); //$NON-NLS-1$
			}
			return _btnNewProject;
		}

		JButton getBtnOpenProject()
		{
			if (_btnOpenProject == null) {
				_btnOpenProject = new JButton(_actionManager.getOpenProjectAction());
				_btnOpenProject.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_PROJECT_OPEN"))); //$NON-NLS-1$                
			}
			return _btnOpenProject;
		}

		JButton getBtnSaveProject()
		{
			if (_btnSaveProject == null) {
				_btnSaveProject = new JButton(_actionManager.getSaveProjectAction());
				_btnSaveProject.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_PROJECT_SAVE"))); //$NON-NLS-1$
			}
			return _btnSaveProject;
		}
		
		JMenuItem getItmAbout()
		{
			if (_itmAbout == null) {
				_itmAbout = new JMenuItem();
				_itmAbout.setText(Messages.getString("MNU_ABOUT")); //$NON-NLS-1$
				_itmAbout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						showAboutDialog();
					}
				});
			}
			return _itmAbout;
		}

		JMenuItem getItmExit()
		{
			if (_itmExit == null) {
				_itmExit = new JMenuItem();
				_itmExit.setText(Messages.getString("MNU_EXIT")); //$NON-NLS-1$
				_itmExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						Starter.exit();
					}
				});
			}
			return _itmExit;
		}

		JMenuItem getItmNewSolution()
		{
			if (_itmNewSolution == null) {
				_itmNewSolution = new JMenuItem();
				_itmNewSolution.setText(Messages.getString("MNU_NEW")); //$NON-NLS-1$
				_itmNewSolution.addActionListener(_actionManager.getNewSolutionAction());
			}
			return _itmNewSolution;
		}

		JMenuItem getItmOpenSolution()
		{
			if (_itmOpenSolution == null) {
				_itmOpenSolution = new JMenuItem();
				_itmOpenSolution.setText(Messages.getString("MNU_OPEN")); //$NON-NLS-1$
				_itmOpenSolution.addActionListener(_actionManager.getOpenSolutionAction());
			}
			return _itmOpenSolution;
		}

		JMenuItem getItmSaveSolution()
		{
			if (_itmSaveSolution == null) {
				_itmSaveSolution = new JMenuItem();
				_itmSaveSolution.setText(Messages.getString("MNU_SAVE")); //$NON-NLS-1$
				_itmSaveSolution.addActionListener(_actionManager.getSaveSolutionAction());
			}
			return _itmSaveSolution;
		}

		JMenuItem getItmNewProject()
		{
			if (_itmNewProject == null) {
				_itmNewProject = new JMenuItem();
				_itmNewProject.setText(Messages.getString("MNU_NEW")); //$NON-NLS-1$
				_itmNewProject.addActionListener(_actionManager.getNewProjectAction());
			}
			return _itmNewProject;
		}

		JMenuItem getItmOpenProject()
		{
			if (_itmOpenProject == null) {
				_itmOpenProject = new JMenuItem();
				_itmOpenProject.setText(Messages.getString("MNU_OPEN")); //$NON-NLS-1$
				_itmOpenProject.addActionListener(_actionManager.getOpenProjectAction());
			}
			return _itmOpenProject;
		}

		JMenuItem getItmSaveProject()
		{
			if (_itmSaveProject == null) {
				_itmSaveProject = new JMenuItem();
				_itmSaveProject.setText(Messages.getString("MNU_SAVE")); //$NON-NLS-1$
				_itmSaveProject.addActionListener(_actionManager.getSaveProjectAction());
			}
			return _itmSaveProject;
		}
		
		JMenuItem getItmSQLConsole()
		{
			if (_itmSQLConsole == null) {
				_itmSQLConsole = new JMenuItem();
				_itmSQLConsole.setText(Messages.getString("MNU_CONSOLE")); //$NON-NLS-1$
				_itmSQLConsole.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						showSQLConsole();
					}
				});
			}
			return _itmSQLConsole;
		}

		JPanel getPnlAbout()
		{
			if (_pnlAbout == null) {
				if (Config.getString("OFFICIAL").equalsIgnoreCase("Y")) {
					_pnlAbout = getOfficialAbout();
				} else {
					_pnlAbout = getUnofficialAbout();
				}
			}
			return _pnlAbout;
		}

		void setPositionComponent(JComponent component)
		{
			_positionComponent = component;
		}

		/**
		 * Method show SQLConsole.
		 */
		void showSQLConsole()
		{
			new SQLConsole(((ManagerEngine)_managerEngine).getDbManager());
//			JFrame objectFrame = new JFrame();
//			objectFrame.getContentPane().add(new ProjectViewer(((ManagerEngine)_managerEngine).getDbManager()));
//			objectFrame.pack();
//			objectFrame.setVisible(true);
		}

		private JPanel getOfficialAbout()
		{
			if (_pnlAbout == null) {
				_pnlAbout = new JPanel();
				_pnlAbout.setLayout(new BorderLayout());
				_pnlAbout.setBorder(BorderFactory.createEmptyBorder(30, 0, 30,
						0));
				//
				// application name
				//
				JLabel lblAppName = new JLabel(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR + Resources.getString("LOGO"))); //$NON-NLS-1$
				//
				// version and author panel
				//
				JPanel detailsPanel = new JPanel();
				detailsPanel.setLayout(new GridLayout(0, 2));
				JLabel lblVersionTitle = new JLabel(
						Messages.getString("TIT_VERSION")); //$NON-NLS-1$
				JLabel lblVersion = new JLabel(Messages.getString("VERSION")); //$NON-NLS-1$
				lblVersion.setForeground(Color.RED);
				JLabel lblAuthorsTitle = new JLabel(
						Messages.getString("TIT_AUTHORS")); //$NON-NLS-1$
				JLabel lblStub = new JLabel();
				JLabel lblAuthor1 = new JLabel("Nikodem Jura"); //$NON-NLS-1$
				JLabel lblAuthor2 = new JLabel("Krzysztof Rajda"); //$NON-NLS-1$
				JLabel lblThanksTitle = new JLabel(
						Messages.getString("TIT_THANKS")); //$NON-NLS-1$
				JLabel lblThanks = new JLabel("Jakub Galkowski"); //$NON-NLS-1$
				// setting components on panel
				detailsPanel.add(lblVersionTitle);
				detailsPanel.add(lblVersion);
				detailsPanel.add(lblAuthorsTitle);
				detailsPanel.add(lblAuthor1);
				detailsPanel.add(lblStub);
				detailsPanel.add(lblAuthor2);
				detailsPanel.add(lblThanksTitle);
				detailsPanel.add(lblThanks);
				detailsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0,
						0, 0));
				// adding componens
				_pnlAbout.add(lblAppName, BorderLayout.CENTER);
				_pnlAbout.add(detailsPanel, BorderLayout.SOUTH);
			}
			return _pnlAbout;
		}

		private JPanel getUnofficialAbout()
		{
			if (_pnlAbout == null) {
				_pnlAbout = new JPanel();
				_pnlAbout.setLayout(new BorderLayout());
				_pnlAbout.setBorder(BorderFactory.createEmptyBorder(30, 0, 30,
						0));
				//
				// application name
				//
				JLabel lblAppName = new JLabel(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR + Resources.getString("LOGO"))); //$NON-NLS-1$
				//
				// version and author panel
				//
				JPanel detailsPanel = new JPanel();
				detailsPanel.setLayout(new GridLayout(0, 2));
				JLabel lblVersionTitle = new JLabel(
						Messages.getString("TIT_VERSION")); //$NON-NLS-1$
				JLabel lblVersion = new JLabel(Messages.getString("VERSION")); //$NON-NLS-1$
				lblVersion.setForeground(Color.RED);
				JLabel lblAuthorsTitle = new JLabel(
						Messages.getString("TIT_AUTHORS")); //$NON-NLS-1$
				JLabel lblAuthors = new JLabel(Messages.getString("AUTHORS")); //$NON-NLS-1$
				detailsPanel.add(lblVersionTitle);
				detailsPanel.add(lblVersion);
				detailsPanel.add(lblAuthorsTitle);
				detailsPanel.add(lblAuthors);
				detailsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0,
						0, 0));
				// adding componens
				_pnlAbout.add(lblAppName, BorderLayout.CENTER);
				_pnlAbout.add(detailsPanel, BorderLayout.SOUTH);
			}
			return _pnlAbout;
		}

		/** Method shows about dialog. */
		private void showAboutDialog()
		{
			JOptionPane.showMessageDialog(_positionComponent, getPnlAbout(),
					"About", JOptionPane.PLAIN_MESSAGE);
		}

	}

	private static final Logger LOGGER = Logger.getLogger(LocalController.class);
}