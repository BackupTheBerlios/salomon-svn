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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import salomon.engine.Config;
import salomon.engine.Messages;
import salomon.engine.Resources;
import salomon.engine.SQLConsole;
import salomon.engine.controller.gui.ControllerFrame;
import salomon.engine.controller.gui.ControllerPanel;
import salomon.engine.controller.gui.PluginMangerGUI;
import salomon.engine.controller.gui.ProjectManagerGUI;
import salomon.engine.controller.gui.RemoteControllerGUI;
import salomon.engine.controller.gui.RemoteControllerPanel;
import salomon.engine.controller.gui.SplashScreen;
import salomon.engine.controller.gui.TaskManagerGUI;
import salomon.engine.controller.gui.action.ActionManager;
import salomon.engine.database.DBManager;
import salomon.engine.holder.ManagerEngineHolder;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.ManagerEngine;

import salomon.engine.remote.CentralController;
import salomon.engine.remote.ICentralController;
import salomon.engine.remote.event.IMasterControllerListener;
import salomon.engine.remote.event.RemoteControllerEvent;
import salomon.util.gui.Utils;

/** * Server side implementation of IController interface. */
public final class MasterController implements IController
{

	/**
	 * 
	 * @uml.property name="_actionManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ActionManager _actionManager;


	private JPanel _contentPane;

	/**
	 * 
	 * @uml.property name="_controllerPanel"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ControllerPanel _controllerPanel;

	/**
	 * 
	 * @uml.property name="_guiMenu"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private MasterGUIMenu _guiMenu;

	/**
	 * 
	 * @uml.property name="_managerEngineHolder"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ManagerEngineHolder _managerEngineHolder;

	/**
	 * 
	 * @uml.property name="_masterController"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private CentralController _masterController;


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


	private Registry _registry;

	/**
	 * 
	 * @uml.property name="_remoteControllerPanel"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private RemoteControllerPanel _remoteControllerPanel;


	private JSplitPane _splitPane;

	/**
	 * 
	 * @uml.property name="_taskManagerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private TaskManagerGUI _taskManagerGUI;


	private JToolBar _toolBar;

	/**
	 *
	 * 
	 * @see salomon.engine.controller.IController#exit()
	 */
	public void exit()
	{
		LOGGER.debug("MasterController.exit()");
		_remoteControllerPanel.removeAllControllers();
	}

	// TODO: change it
	/**
	 * refreshes the controller
	 */
	public void refresh()
	{
		_pluginMangerGUI.refresh();
		_taskManagerGUI.refresh();
	}

	/**
	 * @see salomon.engine.controller.IController#start(salomon.engine.platform.IManagerEngine)
	 */
	public void start(IManagerEngine managerEngine)
	{
		_managerEngineHolder = new ManagerEngineHolder(managerEngine);
		initGUI();
		initRMI();
	}

	private JComponent getJContentPane()
	{
		if (_contentPane == null) {
			_contentPane = new JPanel();
			_contentPane.setLayout(new BorderLayout());
			_splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			_remoteControllerPanel = new RemoteControllerPanel(
					_managerEngineHolder);
			_splitPane.setLeftComponent(_remoteControllerPanel
					.getControllerPanel());
			_controllerPanel = new ControllerPanel(_taskManagerGUI,
					_pluginMangerGUI, _actionManager);
			_splitPane.setRightComponent(_controllerPanel);
			_contentPane.add(_splitPane);
		}
		return _contentPane;
	}

	private JMenuBar getJMenuBar()
	{
		if (_menuBar == null) {
			_menuBar = new JMenuBar();
			JMenu project = new JMenu(Messages.getString("MNU_PROJECT")); //$NON-NLS-1$
			project.add(_guiMenu.getItmNew());
			project.add(_guiMenu.getItmOpen());
			project.add(_guiMenu.getItmSave());
			project.addSeparator();
			project.add(_guiMenu.getItmExit());
			JMenu tools = new JMenu(Messages.getString("MNU_TOOLS")); //$NON-NLS-1$
			tools.add(_guiMenu.getItmSQLConsole());
			JMenu help = new JMenu(Messages.getString("MNU_HELP")); //$NON-NLS-1$           
			help.add(_guiMenu.getItmAbout());
			_menuBar.add(project);
			_menuBar.add(tools);
			_menuBar.add(help);
		}
		return _menuBar;
	}

	private JToolBar getToolBar()
	{
		if (_toolBar == null) {
			_toolBar = new JToolBar();
			_toolBar.add(_guiMenu.getBtnNew());
			_toolBar.add(_guiMenu.getBtnOpen());
			_toolBar.add(_guiMenu.getBtnSave());
		}
		return _toolBar;
	}

	/**
	 * TODO: add comment.
	 * 
	 */
	private void initGUI()
	{
		SplashScreen.show();

		// Creates a new empty project
		// FIXME _managerEngineHolder.getProjectManager().ceateProject();
		
		//FIXME:
//		_projectManagerGUI = new ProjectManagerGUI(_managerEngineHolder.getProjectManager());
//		_taskManagerGUI = new TaskManagerGUI)_managerEngineHolder);
//		_pluginMangerGUI = new PluginMangerGUI(_managerEngineHolder);
		_actionManager = new ActionManager(_projectManagerGUI, _taskManagerGUI,
				_pluginMangerGUI);
		_guiMenu = new MasterGUIMenu(_actionManager);
		ControllerFrame frame = new ControllerFrame();
		// frame.setContentPane(getJContentPane());
		_pluginMangerGUI.setActionManager(_actionManager);
		_taskManagerGUI.setActionManager(_actionManager);
		// FIXME
		_projectManagerGUI.setTaskManagerGUI(_taskManagerGUI);
		frame.setMainPanel(getJContentPane());
		frame.setJMenuBar(getJMenuBar());
		frame.setJToolBar(getToolBar());
		frame.setControllerPanel(_controllerPanel);
		_taskManagerGUI.setParent(frame);
		_projectManagerGUI.setParent(frame);
		_remoteControllerPanel.setParent(frame);

		Utils.setParent(getJContentPane());
		SplashScreen.hide();
		frame.setVisible(true);
	}

//	private void initialize()
//	{
//
//	}

	private void initRMI()
	{
		try {
			System.setSecurityManager(new RMISecurityManager());
			_masterController = new CentralController();
			_masterController
					.addMasterControllerListener(new CentralControllerListener());
			String rmiPortProperty = Config.getString("SERVER_PORT");
			int rmiPort = RMI_PORT;
			if ((rmiPortProperty != null) && (rmiPortProperty.length() != 0)) {
				try {
					rmiPort = Integer.parseInt(rmiPortProperty);
				} catch (NumberFormatException e) {
					LOGGER.fatal("Error while parsing rmiPort property", e);
				}
			}
			_registry = LocateRegistry.createRegistry(rmiPort);

			LOGGER.debug("Registry at  on port: " + rmiPort);
			// TODO: Use bind method
			LOGGER.debug("Registering CentralController...");
			_registry.rebind("CentralController", _masterController);
		} catch (RemoteException e) {
			LOGGER.error(e);
			System.exit(1);
		}
		try {
			ICentralController controller = (ICentralController) _registry
					.lookup("CentralController");
			System.out.println(controller);
		} catch (Exception e1) {
			LOGGER.fatal("Exception was thrown!", e1);
		}

	}

	private final class CentralControllerListener
			implements IMasterControllerListener
	{

		/**
		 * @see salomon.engine.remote.event.IMasterControllerListener#controllerAdded(salomon.engine.remote.event.RemoteControllerEvent)
		 */
		public void controllerAdded(RemoteControllerEvent event)
		{
			RemoteControllerGUI controllerGUI = new RemoteControllerGUI(event
					.getController());
			_remoteControllerPanel.addController(controllerGUI);
		}

		/**
		 * @see salomon.engine.remote.event.IMasterControllerListener#controllerRemoved(salomon.engine.remote.event.RemoteControllerEvent)
		 */
		public void controllerRemoved(RemoteControllerEvent event)
		{
			LOGGER.debug("CentralControllerListener.controllerRemoved()");
			RemoteControllerGUI controllerGUI = new RemoteControllerGUI(event
					.getController());
			controllerGUI.exit();
			_remoteControllerPanel.removeController(controllerGUI);
		}

	}

	private final static class MasterGUIMenu
	{

		/**
		 * 
		 * @uml.property name="_actionManager"
		 * @uml.associationEnd multiplicity="(0 1)"
		 */
		private ActionManager _actionManager;

		private JButton _btnNew;

		private JButton _btnOpen;

		private JButton _btnSave;

		private JMenuItem _itmAbout;

		private JMenuItem _itmExit;

		private JMenuItem _itmNew;

		private JMenuItem _itmOpen;

		private JMenuItem _itmSave;

		private JMenuItem _itmSQLConsole;

		private JPanel _pnlAbout;

		private JComponent _positionComponent;

		private String _resourcesDir;

		/**
		 * creates MasterGUIMenu
		 * @param actionManager manager
		 */
		public MasterGUIMenu(ActionManager actionManager)
		{
			_actionManager = actionManager;
			_resourcesDir = Config.getString("RESOURCES_DIR");
		}

		JButton getBtnNew()
		{
			if (_btnNew == null) {
				_btnNew = new JButton(_actionManager.getNewProjectAction());
				_btnNew.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_PROJECT_NEW"))); //$NON-NLS-1$
			}
			return _btnNew;
		}

		JButton getBtnOpen()
		{
			if (_btnOpen == null) {
				_btnOpen = new JButton(_actionManager.getOpenProjectAction());
				_btnOpen.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_PROJECT_OPEN"))); //$NON-NLS-1$                
			}
			return _btnOpen;
		}

		JButton getBtnSave()
		{
			if (_btnSave == null) {
				_btnSave = new JButton(_actionManager.getSaveProjectAction());
				_btnSave.setIcon(new ImageIcon(_resourcesDir
						+ Config.FILE_SEPARATOR
						+ Resources.getString("ICO_PROJECT_SAVE"))); //$NON-NLS-1$
			}
			return _btnSave;
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
						// exit();
					}
				});
			}
			return _itmExit;
		}

		JMenuItem getItmNew()
		{
			if (_itmNew == null) {
				_itmNew = new JMenuItem();
				_itmNew.setText(Messages.getString("MNU_NEW")); //$NON-NLS-1$
				_itmNew.addActionListener(_actionManager.getNewProjectAction());
			}
			return _itmNew;
		}

		JMenuItem getItmOpen()
		{
			if (_itmOpen == null) {
				_itmOpen = new JMenuItem();
				_itmOpen.setText(Messages.getString("MNU_OPEN")); //$NON-NLS-1$
				_itmOpen.addActionListener(_actionManager
						.getOpenProjectAction());
			}
			return _itmOpen;
		}

		JMenuItem getItmSave()
		{
			if (_itmSave == null) {
				_itmSave = new JMenuItem();
				_itmSave.setText(Messages.getString("MNU_SAVE")); //$NON-NLS-1$
				_itmSave.addActionListener(_actionManager
						.getSaveProjectAction());
			}
			return _itmSave;
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
			throw new UnsupportedOperationException(
					"Method showSQLConsole() not implemented yet!");			
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
				JLabel lblVersionTitle = new JLabel(Messages
						.getString("TIT_VERSION")); //$NON-NLS-1$
				JLabel lblVersion = new JLabel(Messages.getString("VERSION")); //$NON-NLS-1$
				lblVersion.setForeground(Color.RED);
				JLabel lblAuthorsTitle = new JLabel(Messages
						.getString("TIT_AUTHORS")); //$NON-NLS-1$
				JLabel lblStub = new JLabel();
				JLabel lblAuthor1 = new JLabel("Nikodem Jura"); //$NON-NLS-1$
				JLabel lblAuthor2 = new JLabel("Krzysztof Rajda"); //$NON-NLS-1$
				JLabel lblThanksTitle = new JLabel(Messages
						.getString("TIT_THANKS")); //$NON-NLS-1$
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
				JLabel lblVersionTitle = new JLabel(Messages
						.getString("TIT_VERSION")); //$NON-NLS-1$
				JLabel lblVersion = new JLabel(Messages.getString("VERSION")); //$NON-NLS-1$
				lblVersion.setForeground(Color.RED);
				JLabel lblAuthorsTitle = new JLabel(Messages
						.getString("TIT_AUTHORS")); //$NON-NLS-1$
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

	private static final Logger LOGGER = Logger
			.getLogger(MasterController.class);

	private static final int RMI_PORT = 4321;
} // end ServerManager
