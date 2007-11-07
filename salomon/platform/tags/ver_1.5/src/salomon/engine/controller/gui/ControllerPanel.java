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

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.controller.gui.common.statusbar.StatusBarManager;
import salomon.engine.controller.gui.plugin.PluginManagerGUI;
import salomon.engine.controller.gui.project.ProjectManagerGUI;
import salomon.engine.controller.gui.solution.SolutionManagerGUI;
import salomon.engine.controller.gui.task.GraphTaskManagerGUI;
import salomon.engine.controller.gui.task.TaskControlPane;

/**
 * Class represents main panel - list of tasks and available plugins, buttons to
 * manage them.
 */
public final class ControllerPanel extends JPanel implements IControllerPanel
{
    private static final Logger LOGGER = Logger.getLogger(ControllerFrame.class);

    private JPanel _graphPanel;

    private JList _lstPlugins;

    private JComponent _mainPanel;

    /**
     * 
     * @uml.property name="_pluginMangerGUI"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private PluginManagerGUI _pluginMangerGUI;

    private JPanel _pnlPlugins;

    private JPanel _pnlTasks;

    private ProjectManagerGUI _projectManagerGUI;

    private SolutionManagerGUI _solutionManagerGUI;

    private StatusBarManager _statusBarManager;

    private JPanel _taskControlPane;

    /**
     * 
     * @uml.property name="_taskManagerGUI"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private GraphTaskManagerGUI _taskManagerGUI;

    public ControllerPanel(SolutionManagerGUI solutionManagerGUI,
            ProjectManagerGUI projectManagerGUI,
            GraphTaskManagerGUI taskManagerGUI, PluginManagerGUI pluginMangerGUI)
    {
        _solutionManagerGUI = solutionManagerGUI;
        _projectManagerGUI = projectManagerGUI;
        _taskManagerGUI = taskManagerGUI;
        _pluginMangerGUI = pluginMangerGUI;
        _statusBarManager = new StatusBarManager();

        _solutionManagerGUI.addSolutionListener(_statusBarManager);
        _projectManagerGUI.addProjectListener(_statusBarManager);
        _taskManagerGUI.addTaskListener(_statusBarManager);
        //        _pluginMangerGUI.setStatusBar(_statusBarManager);

        this.setLayout(new BorderLayout());
        this.add(_statusBarManager.getStatusBar(), BorderLayout.SOUTH);
        this.add(getMainPanel(), BorderLayout.CENTER);
    }

    public JComponent getComponent()
    {
        return this;
    }

    public GraphTaskManagerGUI getTaskEditionManager()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see salomon.engine.controller.gui.IControllerPanel#refresh()
     */
    public void refresh()
    {
        LOGGER.debug("ControllerPane: refreshing...");
        super.validate();
        _pluginMangerGUI.refresh();
        _taskManagerGUI.refresh();
    }

    /**
     * This method initializes _lstPlugins
     * 
     * @return JList
     */
    private JList getLstPlugins()
    {
        if (_lstPlugins == null) {
            _lstPlugins = _pluginMangerGUI.getPluginList();
            _lstPlugins.setBorder(BorderFactory.createLoweredBevelBorder());
            _lstPlugins.setPreferredSize(new Dimension(100, 200));
        }
        return _lstPlugins;
    }

    /**
     * This method initializes _lstTasks
     * 
     * @return JPanel
     */
    private JPanel getGraphPanel()
    {
        if (_graphPanel == null) {
            _graphPanel = _taskManagerGUI.getGraphPanel();
            _graphPanel.setBorder(BorderFactory.createLoweredBevelBorder());
            _graphPanel.setPreferredSize(new Dimension(100, 200));
        }
        return _graphPanel;
    }

    private JComponent getMainPanel()
    {
        if (_mainPanel == null) {
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                    getPnlPlugins(), getTasksPanel());
            splitPane.setOneTouchExpandable(true);
            _mainPanel = splitPane;
        }
        return _mainPanel;
    }

    /**
     * This method initializes _pnlPlugins
     * 
     * @return JPanel
     */
    private JPanel getPnlPlugins()
    {
        if (_pnlPlugins == null) {
            _pnlPlugins = new JPanel();
            _pnlPlugins.setLayout(new BorderLayout());
            _pnlPlugins.add(getLstPlugins(), BorderLayout.CENTER);
            _pnlPlugins.setBorder(BorderFactory.createTitledBorder(null,
                    Messages.getString("TIT_PLUGINS"), TitledBorder.LEFT, //$NON-NLS-1$
                    TitledBorder.DEFAULT_POSITION));
        }
        return _pnlPlugins;
    }

    /**
     * This method initializes _taskControlPane
     * 
     * @return JPanel
     */
    private JPanel getTaskButtons()
    {
        if (_taskControlPane == null) {
            _taskControlPane = new TaskControlPane(_taskManagerGUI).getPanel();
        }
        return _taskControlPane;
    }

    /**
     * This method initializes _pnlTasks
     * 
     * @return JPanel
     */
    private JPanel getTasksPanel()
    {
        if (_pnlTasks == null) {
            _pnlTasks = new JPanel();
            _pnlTasks.setLayout(new BorderLayout());
            _pnlTasks.add(getTaskButtons(), BorderLayout.NORTH);
            _pnlTasks.add(getGraphPanel(), BorderLayout.CENTER);
            _pnlTasks.setBorder(BorderFactory.createTitledBorder(null,
                    Messages.getString("TIT_TASKS"), //$NON-NLS-1$
                    TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        }
        return _pnlTasks;
    }
}