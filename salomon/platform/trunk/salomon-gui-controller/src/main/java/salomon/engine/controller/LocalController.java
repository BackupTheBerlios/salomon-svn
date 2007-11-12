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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import salomon.engine.Config;
import salomon.engine.Messages;
import salomon.engine.Resources;
import salomon.engine.SQLConsole;
import salomon.engine.controller.gui.ControllerFrame;
import salomon.engine.controller.gui.ControllerPanel;
import salomon.engine.controller.gui.IControllerPanel;
import salomon.engine.controller.gui.agentconfig.AgentConfigManagerGUI;
import salomon.engine.controller.gui.common.AboutPanel;
import salomon.engine.controller.gui.common.SplashScreen;
import salomon.engine.controller.gui.common.action.ActionManager;
import salomon.engine.controller.gui.domain.SolutionManagerGUI;
import salomon.engine.controller.gui.plugin.PluginManagerGUI;
import salomon.engine.controller.gui.project.ProjectManagerGUI;
import salomon.engine.controller.gui.task.GraphTaskManagerGUI;
import salomon.engine.database.DBManager;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.ManagerEngine;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.Utils;
import salomon.util.gui.editor.JavaEditorFrame;

/**
 * Local implementation of IController interface.
 */
public final class LocalController implements IController
{

    private static final Logger LOGGER = Logger.getLogger(LocalController.class);

    /**
     * 
     * @uml.property name="_actionManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ActionManager _actionManager;

    // private JPanel _contentPane;

    /**
     * 
     * @uml.property name="_contentPane"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IControllerPanel _contentPane;

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
    private PluginManagerGUI _pluginMangerGUI;

    private AgentConfigManagerGUI _agentConfigManagerGUI;

    /**
     * 
     * @uml.property name="_projectManagerGUI"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ProjectManagerGUI _projectManagerGUI;

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
    private GraphTaskManagerGUI _taskManagerGUI;

    private JToolBar _toolBar;

    /**
     * @see salomon.engine.controller.gui.IControllerPanel#exit()
     */
    public void exit()
    {
        if (_managerEngine != null) {
            DBManager dbManager = ((ManagerEngine) _managerEngine).getDbManager();
            try {
                dbManager.disconnect();
            } catch (SQLException e) {
                LOGGER.fatal("", e);
            }
        }
    }

    /**
     * @see salomon.engine.controller.gui.IControllerPanel#getJContentPane()
     */
    public JComponent getJContentPane()
    {
        _contentPane = new ControllerPanel(_solutionManagerGUI,
                _projectManagerGUI, _taskManagerGUI, _pluginMangerGUI);
        return _contentPane.getComponent();
    }

    /**
     * @see salomon.engine.controller.gui.IControllerPanel#getJMenuBar()
     */
    public JMenuBar getJMenuBar()
    {
        if (_menuBar == null) {
            _menuBar = new JMenuBar();

            JMenu solution = new JMenu(Messages.getString("MNU_SOLUTION")); //$NON-NLS-1$
            solution.setMnemonic('s');
            JMenu project = new JMenu(Messages.getString("MNU_PROJECT")); //$NON-NLS-1$
            project.setMnemonic('p');

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
            project.add(_guiMenu.getItmSaveAsProject());

            JMenu tools = new JMenu(Messages.getString("MNU_TOOLS")); //$NON-NLS-1$
            tools.setMnemonic('t');
            tools.add(_guiMenu.getItmViewProjects());
            tools.add(_guiMenu.getItmViewSolutions());
            tools.add(_guiMenu.getItmViewPlugins());
            tools.add(_guiMenu.getItmViewTasks());
            tools.addSeparator();
            tools.add(_guiMenu.getItmSQLConsole());
            tools.add(_guiMenu.getItmJavaEditor());

            JMenu help = new JMenu(Messages.getString("MNU_HELP")); //$NON-NLS-1$
            help.setMnemonic('h');
            help.add(_guiMenu.getItmAbout());
            _menuBar.add(solution);
            // _menuBar.add(project);
            _menuBar.add(tools);
            _menuBar.add(help);
        }
        return _menuBar;
    }

    /**
     * @see salomon.engine.controller.gui.IControllerPanel#getToolBar()
     */
    public JToolBar getToolBar()
    {
        if (_toolBar == null) {
            _toolBar = new JToolBar();
            _toolBar.setFloatable(true);
            _toolBar.setRollover(true);
            _toolBar.add(_guiMenu.getBtnNewSolution());
            _toolBar.add(_guiMenu.getBtnOpenSolution());
            _toolBar.add(_guiMenu.getBtnSaveSolution());
        }
        return _toolBar;
    }

    /**
     * @throws PlatformException 
     * @see salomon.engine.controller.gui.IControllerPanel#start(salomon.engine.platform.IManagerEngine)
     */
    public void start() throws PlatformException
    {
        SplashScreen.show();
        _managerEngine = new ManagerEngine();
        // TODO: add cascade model support (?)
        try {
            _solutionManagerGUI = new SolutionManagerGUI(
                    _managerEngine.getSolutionManager());
            _projectManagerGUI = new ProjectManagerGUI(
                    _managerEngine.getProjectManager());
            _taskManagerGUI = new GraphTaskManagerGUI(
                    _managerEngine.getTasksManager(),
                    _managerEngine.getPluginManager());
            _pluginMangerGUI = new PluginManagerGUI(
                    _managerEngine.getPluginManager());
            _agentConfigManagerGUI = new AgentConfigManagerGUI(
                    _managerEngine.getAgentManager(),
                    _managerEngine.getAgentConfigManager());

        } catch (PlatformException e) {
            LOGGER.fatal("", e); //$NON-NLS-1$
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SHOW_GUI")); //$NON-NLS-1$
            return;
        }

        _actionManager = new ActionManager(_solutionManagerGUI,
                _projectManagerGUI, _taskManagerGUI, _pluginMangerGUI,
                _agentConfigManagerGUI);

        _guiMenu = new LocalGUIMenu(_actionManager);
        ControllerFrame frame = new ControllerFrame();
        _solutionManagerGUI.setParent(frame);
        _projectManagerGUI.setParent(frame);
        _pluginMangerGUI.setParent(frame);
        _taskManagerGUI.setParent(frame);
        _agentConfigManagerGUI.setParent(frame);

        _solutionManagerGUI.setActionManager(_actionManager);
        _projectManagerGUI.setActionManager(_actionManager);
        _pluginMangerGUI.setActionManager(_actionManager);
        _taskManagerGUI.setActionManager(_actionManager);
        _agentConfigManagerGUI.setActionManager(_actionManager);

        frame.setContentPane(getJContentPane());
        frame.setJMenuBar(getJMenuBar());
        frame.setJToolBar(getToolBar());
        frame.setControllerPanel(_contentPane);
        Utils.setParent(frame);

        // initializing solution frame before hiding splash screen
        _solutionManagerGUI.initSolutionFrame();
        SplashScreen.hide();
        _solutionManagerGUI.showSolutionChooser();
    }

    private final class LocalGUIMenu
    {
        private SQLConsole _console;

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

        private JMenuItem _itmJavaEditor;

        private JMenuItem _itmNewProject;

        private JMenuItem _itmNewSolution;

        private JMenuItem _itmOpenProject;

        private JMenuItem _itmOpenSolution;

        private JMenuItem _itmSaveAsProject;

        private JMenuItem _itmSaveProject;

        private JMenuItem _itmSaveSolution;

        private JMenuItem _itmSQLConsole;

        private JMenuItem _itmViewPlugins;

        private JMenuItem _itmViewProjects;

        private JMenuItem _itmViewSolutions;

        private JMenuItem _itmViewTasks;

        private JPanel _pnlAbout;

        private JComponent _positionComponent;

        /**
         * creates LocalGUIMenu
         * 
         * @param actionManager
         */
        public LocalGUIMenu(ActionManager actionManager)
        {
            _actionManager = actionManager;
        }

        JButton getBtnNewProject()
        {
            if (_btnNewProject == null) {
                _btnNewProject = new JButton(
                        _actionManager.getNewProjectAction());
                _btnNewProject.setIcon(new ImageIcon(Config.RESOURCES_DIR
                        + Config.FILE_SEPARATOR + "")); //$NON-NLS-1$
            }
            return _btnNewProject;
        }

        JButton getBtnNewSolution()
        {
            if (_btnNewSolution == null) {
                _btnNewSolution = new JButton(
                        _actionManager.getNewSolutionAction());
                _btnNewSolution.setIcon(getMenuIcon("ICO_SOLUTION_NEW")); //$NON-NLS-1$
                _btnNewSolution.setToolTipText(Messages.getString("TOOLTIP_NEW_SOLUTION")); //$NON-NLS-1$
            }
            return _btnNewSolution;
        }

        JButton getBtnOpenProject()
        {
            if (_btnOpenProject == null) {
                _btnOpenProject = new JButton(
                        _actionManager.getOpenProjectAction());
                _btnOpenProject.setIcon(new ImageIcon(Config.RESOURCES_DIR
                        + Config.FILE_SEPARATOR + "")); //$NON-NLS-1$                
            }
            return _btnOpenProject;
        }

        JButton getBtnOpenSolution()
        {
            if (_btnOpenSolution == null) {
                _btnOpenSolution = new JButton(
                        _actionManager.getOpenSolutionAction());
                _btnOpenSolution.setIcon(getMenuIcon("ICO_SOLUTION_OPEN")); //$NON-NLS-1$
                _btnOpenSolution.setToolTipText(Messages.getString("TOOLTIP_OPEN_SOLUTION")); //$NON-NLS-1$

            }
            return _btnOpenSolution;
        }

        JButton getBtnSaveProject()
        {
            if (_btnSaveProject == null) {
                _btnSaveProject = new JButton(
                        _actionManager.getSaveProjectAction());
                _btnSaveProject.setIcon(new ImageIcon(Config.RESOURCES_DIR
                        + Config.FILE_SEPARATOR + "")); //$NON-NLS-1$
            }
            return _btnSaveProject;
        }

        JButton getBtnSaveSolution()
        {
            if (_btnSaveSolution == null) {
                _btnSaveSolution = new JButton(
                        _actionManager.getSaveSolutionAction());
                _btnSaveSolution.setIcon(getMenuIcon("ICO_SOLUTION_SAVE")); //$NON-NLS-1$
                _btnSaveSolution.setToolTipText(Messages.getString("TOOLTIP_SAVE_SOLUTION")); //$NON-NLS-1$
            }
            return _btnSaveSolution;
        }

        JMenuItem getItmAbout()
        {
            if (_itmAbout == null) {
                _itmAbout = new JMenuItem(Messages.getString("MNU_ABOUT"));
                _itmAbout.setMnemonic('a');

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
                _itmEditSolution.setMnemonic('e');
                _itmEditSolution.setAccelerator(KeyStroke.getKeyStroke(
                        KeyEvent.VK_E, InputEvent.CTRL_MASK));

                _itmEditSolution.addActionListener(_actionManager.getEditSolutionAction());
            }
            return _itmEditSolution;
        }

        JMenuItem getItmExit()
        {
            if (_itmExit == null) {
                _itmExit = new JMenuItem(Messages.getString("MNU_EXIT"));
                _itmExit.setMnemonic('x');
                _itmExit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        //FIXME: tarter.exit();
                        System.exit(0);
                    }
                });
            }
            return _itmExit;
        }

        JMenuItem getItmJavaEditor()
        {
            if (_itmJavaEditor == null) {
                _itmJavaEditor = new JMenuItem();
                _itmJavaEditor.setText(Messages.getString("MNU_JAVA_EDITOR")); //$NON-NLS-1$
                _itmJavaEditor.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        showJavaEditor();
                    }
                });
            }

            return _itmJavaEditor;
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
                _itmNewSolution = new JMenuItem(Messages.getString("MNU_NEW"), //$NON-NLS-1$
                        getMenuIcon("ICO_PROJECT_NEW")); //$NON-NLS-1$
                _itmNewSolution.setMnemonic('n');
                _itmNewSolution.setAccelerator(KeyStroke.getKeyStroke(
                        KeyEvent.VK_N, InputEvent.CTRL_MASK));

                _itmNewSolution.addActionListener(_actionManager.getNewSolutionAction());
            }
            return _itmNewSolution;
        }

        JMenuItem getItmOpenProject()
        {
            if (_itmOpenProject == null) {
                _itmOpenProject = new JMenuItem(Messages.getString("MNU_OPEN")); //$NON-NLS-1$
                _itmOpenProject.addActionListener(_actionManager.getOpenProjectAction());
            }
            return _itmOpenProject;
        }

        JMenuItem getItmOpenSolution()
        {
            if (_itmOpenSolution == null) {
                _itmOpenSolution = new JMenuItem(
                        Messages.getString("MNU_OPEN"), getMenuIcon("ICO_PROJECT_OPEN")); //$NON-NLS-1$ //$NON-NLS-2$
                _itmOpenSolution.setMnemonic('o');
                _itmOpenSolution.setAccelerator(KeyStroke.getKeyStroke(
                        KeyEvent.VK_O, InputEvent.CTRL_MASK));

                _itmOpenSolution.addActionListener(_actionManager.getOpenSolutionAction());
            }
            return _itmOpenSolution;
        }

        JMenuItem getItmSaveAsProject()
        {
            if (_itmSaveAsProject == null) {
                _itmSaveAsProject = new JMenuItem();
                _itmSaveAsProject.setText(Messages.getString("MNU_SAVE_AS")); //$NON-NLS-1$
                _itmSaveAsProject.addActionListener(_actionManager.getSaveAsProjectAction());
            }
            return _itmSaveAsProject;
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
                _itmSaveSolution = new JMenuItem(
                        Messages.getString("MNU_SAVE"), getMenuIcon("ICO_PROJECT_SAVE")); //$NON-NLS-1$ //$NON-NLS-2$
                _itmSaveSolution.setMnemonic('s');
                _itmSaveSolution.setAccelerator(KeyStroke.getKeyStroke(
                        KeyEvent.VK_S, InputEvent.CTRL_MASK));

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
                _pnlAbout = new AboutPanel().getPanel();
            }
            return _pnlAbout;
        }

        void setPositionComponent(JComponent component)
        {
            _positionComponent = component;
        }

        /**
         * Shows Java Editor
         */
        void showJavaEditor()
        {
            new JavaEditorFrame(_managerEngine);
        }

        /**
         * Method shows SQLConsole.
         */
        void showSQLConsole()
        {
            if (_console == null) {
                _console = new SQLConsole(
                        ((ManagerEngine) _managerEngine).getDbManager());
            }
            _console.showConsole();
        }

        private ImageIcon getMenuIcon(String iconKey)
        {
            String iconFileName = Resources.getString(iconKey);
            String iconPath = Config.RESOURCES_DIR + Config.FILE_SEPARATOR
                    + iconFileName;

            return new ImageIcon(iconPath);
        }

        /** Method shows about dialog. */
        private void showAboutDialog()
        {
            JOptionPane.showMessageDialog(_positionComponent, getPnlAbout(),
                    "About", JOptionPane.PLAIN_MESSAGE); //$NON-NLS-1$
        }

    }
}