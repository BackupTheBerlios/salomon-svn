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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;

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

import salomon.util.gui.Utils;

import salomon.platform.exception.PlatformException;

import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.ManagerEngine;

/**
 * Local implementation of IController interface.
 */
public final class LocalController implements IController
{

	/**
	 * 
	 * @uml.property name="_actionManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ActionManager _actionManager;

	/**
	 * 
	 * @uml.property name="_contentPane"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ControllerPanel _contentPane;

	/**
	 * 
	 * @uml.property name="_guiMenu"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private LocalGUIMenu _guiMenu;

	/**
	 * 
	 * @uml.property name="_managerEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IManagerEngine _managerEngine;

	private JMenuBar _menuBar;

	/**
	 * 
	 * @uml.property name="_pluginMangerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private PluginMangerGUI _pluginMangerGUI;

	/**
	 * 
	 * @uml.property name="_projectManagerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ProjectManagerGUI _projectManagerGUI;

	private JFrame _solutionChooserFrame;

	/**
	 * 
	 * @uml.property name="_solutionManagerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private SolutionManagerGUI _solutionManagerGUI;

	/**
	 * 
	 * @uml.property name="_taskManagerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
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

	public JComponent getJContentPane()
	{
		if (_contentPane == null) {
			_contentPane = new ControllerPanel(_solutionManagerGUI,
					_projectManagerGUI, _taskManagerGUI, _pluginMangerGUI,
					_actionManager);
		}
		return _contentPane;
	}

	public JMenuBar getJMenuBar()
	{
		if (_menuBar == null) {
			_menuBar = new JMenuBar();

			JMenu solution = new JMenu(Messages.getString("MNU_SOLUTION")); //$NON-NLS-1$
			JMenu project = new JMenu(Messages.getString("MNU_PROJECT")); //$NON-NLS-1$

			solution.add(project);
			solution.addSeparator();
			solution.add(_guiMenu.getItmNewSolution());
			solution.add(_guiMenu.getItmOpenSolution());
			solution.add(_guiMenu.getItmEditSolution());
			solution.addSeparator();
			solution.add(_guiMenu.getItmSaveSolution());
			solution.addSeparator();
			solution.add(_guiMenu.getItmExit());

			project.add(_guiMenu.getItmNewProject());
			project.add(_guiMenu.getItmOpenProject());
			project.add(_guiMenu.getItmEditProject());
			project.addSeparator();
			project.add(_guiMenu.getItmSaveProject());

			JMenu tools = new JMenu(Messages.getString("MNU_TOOLS")); //$NON-NLS-1$
			tools.add(_guiMenu.getItmViewProjects());
			tools.add(_guiMenu.getItmViewSolutions());
			tools.add(_guiMenu.getItmViewPlugins());
			tools.add(_guiMenu.getItmViewTasks());
			tools.addSeparator();
			tools.add(_guiMenu.getItmSQLConsole());

			JMenu help = new JMenu(Messages.getString("MNU_HELP")); //$NON-NLS-1$           
			help.add(_guiMenu.getItmAbout());
			_menuBar.add(solution);
			//_menuBar.add(project);
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

	/**
	 * @see salomon.engine.controller.IController#start(salomon.engine.platform.IManagerEngine)
	 */
	public void start(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
		SplashScreen.show();
		try {
			PlasticLookAndFeel.setTabStyle(PlasticLookAndFeel.TAB_STYLE_METAL_VALUE);
			PlasticLookAndFeel.setMyCurrentTheme(new ExperienceBlue());
			UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
		} catch (Exception e) {
			LOGGER.warn("Cannot set look&feel!", e);
		}
		//TODO: add cascade model support (?)
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

		_actionManager = new ActionManager(_solutionManagerGUI,
				_projectManagerGUI, _taskManagerGUI, _pluginMangerGUI);

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

		frame.setContentPane(getJContentPane());
		frame.setJMenuBar(getJMenuBar());
		frame.setJToolBar(getToolBar());
		frame.setControllerPanel(_contentPane);
		Utils.setParent(getJContentPane());

		//showSolutionChooser();
		_solutionManagerGUI.showSolutionChooser();
	}

	//	private void showSolutionChooser()
	//	{
	//		JPanel panel = new JPanel();
	//
	//		JPanel pnlManagerButtons = new JPanel();
	//		pnlManagerButtons.setLayout(new BoxLayout(pnlManagerButtons,
	//				BoxLayout.Y_AXIS));
	//		pnlManagerButtons.add(Box.createVerticalGlue());
	//		pnlManagerButtons.add(_solutionManagerGUI.getBtnOpen());
	//		pnlManagerButtons.add(_solutionManagerGUI.getBtnNew());
	//		pnlManagerButtons.add(Box.createVerticalStrut(10));
	//		pnlManagerButtons.add(_solutionManagerGUI.getBtnExit());
	//		pnlManagerButtons.add(Box.createVerticalGlue());
	//
	//		panel.setLayout(new BorderLayout());
	//		panel.add(_solutionManagerGUI.getSolutionsPanel(), BorderLayout.CENTER);
	//		panel.add(pnlManagerButtons, BorderLayout.EAST);
	//
	//		_solutionChooserFrame = new JFrame(Messages.getString("TIT_SOLUTIONS"));
	//		_solutionChooserFrame.setResizable(false);
	//
	//		_solutionChooserFrame.getContentPane().add(panel);
	//		_solutionChooserFrame.pack();
	//
	//		_solutionChooserFrame.addWindowListener(new WindowAdapter() {
	//			public void windowClosing(WindowEvent e)
	//			{
	//				Starter.exit();
	//			}
	//		});
	//
	//		Point location = new Point();
	//		location.x = (Toolkit.getDefaultToolkit().getScreenSize().width - _solutionChooserFrame.getWidth()) / 2;
	//		location.y = (Toolkit.getDefaultToolkit().getScreenSize().height - _solutionChooserFrame.getHeight()) / 2;
	//		_solutionChooserFrame.setLocation(location);
	//
	//		_solutionChooserFrame.setVisible(true);
	//
	//	}

	private final class LocalGUIMenu
	{

		private JButton _btnNewProject;

		private JButton _btnNewSolution;

		private JButton _btnOpenProject;

		private JButton _btnOpenSolution;

		private JButton _btnSaveProject;

		private JButton _btnSaveSolution;

		private JMenuItem _itmAbout;

		private JMenuItem _itmEditProject;

		private JMenuItem _itmEditSolution;

		private JMenuItem _itmExit;

		private JMenuItem _itmNewProject;

		private JMenuItem _itmNewSolution;

		private JMenuItem _itmOpenProject;

		private JMenuItem _itmOpenSolution;

		private JMenuItem _itmSaveProject;

		private JMenuItem _itmSaveSolution;

		private JMenuItem _itmSQLConsole;

		private JMenuItem _itmViewPlugins;

		private JMenuItem _itmViewProjects;

		private JMenuItem _itmViewSolutions;

		private JMenuItem _itmViewTasks;

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

		JButton getBtnNewProject()
		{
			if (_btnNewProject == null) {
				_btnNewProject = new JButton(
						_actionManager.getNewProjectAction());
				_btnNewProject.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_PROJECT_NEW"))); //$NON-NLS-1$
			}
			return _btnNewProject;
		}

		JButton getBtnNewSolution()
		{
			if (_btnNewSolution == null) {
				_btnNewSolution = new JButton(
						_actionManager.getNewSolutionAction());
				_btnNewSolution.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_SOLUTION_NEW"))); //$NON-NLS-1$
			}
			return _btnNewSolution;
		}

		JButton getBtnOpenProject()
		{
			if (_btnOpenProject == null) {
				_btnOpenProject = new JButton(
						_actionManager.getOpenProjectAction());
				_btnOpenProject.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_PROJECT_OPEN"))); //$NON-NLS-1$                
			}
			return _btnOpenProject;
		}

		JButton getBtnOpenSolution()
		{
			if (_btnOpenSolution == null) {
				_btnOpenSolution = new JButton(
						_actionManager.getOpenSolutionAction());
				_btnOpenSolution.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_SOLUTION_OPEN"))); //$NON-NLS-1$                
			}
			return _btnOpenSolution;
		}

		JButton getBtnSaveProject()
		{
			if (_btnSaveProject == null) {
				_btnSaveProject = new JButton(
						_actionManager.getSaveProjectAction());
				_btnSaveProject.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_PROJECT_SAVE"))); //$NON-NLS-1$
			}
			return _btnSaveProject;
		}

		JButton getBtnSaveSolution()
		{
			if (_btnSaveSolution == null) {
				_btnSaveSolution = new JButton(
						_actionManager.getSaveSolutionAction());
				_btnSaveSolution.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_SOLUTION_SAVE"))); //$NON-NLS-1$
			}
			return _btnSaveSolution;
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

		JMenuItem getItmEditProject()
		{
			if (_itmEditProject == null) {
				_itmEditProject = new JMenuItem();
				_itmEditProject.setText(Messages.getString("MNU_EDIT")); //$NON-NLS-1$
				_itmEditProject.addActionListener(_actionManager.getEditProjectAction());
			}
			return _itmEditProject;
		}

		JMenuItem getItmEditSolution()
		{
			if (_itmEditSolution == null) {
				_itmEditSolution = new JMenuItem();
				_itmEditSolution.setText(Messages.getString("MNU_EDIT")); //$NON-NLS-1$
				_itmEditSolution.addActionListener(_actionManager.getEditSolutionAction());
			}
			return _itmEditSolution;
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

		JMenuItem getItmNewProject()
		{
			if (_itmNewProject == null) {
				_itmNewProject = new JMenuItem();
				_itmNewProject.setText(Messages.getString("MNU_NEW")); //$NON-NLS-1$
				_itmNewProject.addActionListener(_actionManager.getNewProjectAction());
			}
			return _itmNewProject;
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

		JMenuItem getItmOpenProject()
		{
			if (_itmOpenProject == null) {
				_itmOpenProject = new JMenuItem();
				_itmOpenProject.setText(Messages.getString("MNU_OPEN")); //$NON-NLS-1$
				_itmOpenProject.addActionListener(_actionManager.getOpenProjectAction());
			}
			return _itmOpenProject;
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

		JMenuItem getItmSaveProject()
		{
			if (_itmSaveProject == null) {
				_itmSaveProject = new JMenuItem();
				_itmSaveProject.setText(Messages.getString("MNU_SAVE")); //$NON-NLS-1$
				_itmSaveProject.addActionListener(_actionManager.getSaveProjectAction());
			}
			return _itmSaveProject;
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

		JMenuItem getItmViewPlugins()
		{
			if (_itmViewPlugins == null) {
				_itmViewPlugins = new JMenuItem();
				_itmViewPlugins.setText(Messages.getString("MNU_SHOW_PLUGINS")); //$NON-NLS-1$
				_itmViewPlugins.addActionListener(_actionManager.getViewPluginsAction());
			}
			return _itmViewPlugins;
		}

		JMenuItem getItmViewProjects()
		{
			if (_itmViewProjects == null) {
				_itmViewProjects = new JMenuItem();
				_itmViewProjects.setText(Messages.getString("MNU_SHOW_PROJECTS")); //$NON-NLS-1$
				_itmViewProjects.addActionListener(_actionManager.getViewProjectsAction());
			}
			return _itmViewProjects;
		}

		JMenuItem getItmViewSolutions()
		{
			if (_itmViewSolutions == null) {
				_itmViewSolutions = new JMenuItem();
				_itmViewSolutions.setText(Messages.getString("MNU_SHOW_SOLUTIONS")); //$NON-NLS-1$
				_itmViewSolutions.addActionListener(_actionManager.getViewSolutionAction());
			}
			return _itmViewSolutions;
		}

		JMenuItem getItmViewTasks()
		{
			if (_itmViewTasks == null) {
				_itmViewTasks = new JMenuItem();
				_itmViewTasks.setText(Messages.getString("MNU_SHOW_TASKS")); //$NON-NLS-1$
				_itmViewTasks.addActionListener(_actionManager.getViewTasksAction());
			}
			return _itmViewTasks;
		}

		JPanel getPnlAbout()
		{
			if (_pnlAbout == null) {
				_pnlAbout = getAboutPanel();
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
			new SQLConsole(((ManagerEngine) _managerEngine).getDbManager());
		}

		private JPanel getAboutPanel()
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
				detailsPanel.setLayout(new BoxLayout(detailsPanel,
						BoxLayout.Y_AXIS));

				JLabel lblVersionTitle = new JLabel(
						Messages.getString("TIT_VERSION")); //$NON-NLS-1$
				JLabel lblVersion = new JLabel(Messages.getString("VERSION")); //$NON-NLS-1$
				lblVersion.setForeground(Color.RED);
				Box versionBox = Box.createHorizontalBox();
				versionBox.add(lblVersionTitle);
				versionBox.add(Box.createHorizontalGlue());
				versionBox.add(lblVersion);

				JLabel lblAuthorsTitle = new JLabel(
						Messages.getString("TIT_AUTHORS")); //$NON-NLS-1$
				Box authorsBox = Box.createHorizontalBox();
				authorsBox.add(lblAuthorsTitle);

				detailsPanel.add(versionBox);
				detailsPanel.add(authorsBox);
				detailsPanel.add(this.getAuthorLabel("Nikodem Jura",
						"nico@icslab.agh.edu.pl"));
				detailsPanel.add(this.getAuthorLabel("Krzysztof Rajda",
						"krzysztof@rajda.name"));
				detailsPanel.add(this.getAuthorLabel("Jakub Galkowski",
						"avi@student.uci.agh.edu.pl"));
				detailsPanel.add(this.getAuthorLabel("Leszek Grzanka",
						"grzanka@student.uci.agh.edu.pl"));
				detailsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0,
						0, 0));
				// adding componens
				_pnlAbout.add(lblAppName, BorderLayout.CENTER);
				_pnlAbout.add(detailsPanel, BorderLayout.SOUTH);
			}
			return _pnlAbout;
		}

		private JComponent getAuthorLabel(String name, String email)
		{
			Box box = Box.createHorizontalBox();
			JLabel lblName = new JLabel(name);
			JLabel lblEmail = new JLabel(email);
			lblEmail.setForeground(Color.BLUE);
			box.add(lblName);
			box.add(Box.createHorizontalGlue());
			box.add(lblEmail);
			return box;
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