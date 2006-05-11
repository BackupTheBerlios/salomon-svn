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
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.controller.gui.graph.GraphTaskManagerGUI;
import salomon.engine.controller.gui.graph.TaskControlPane;

/**
 * Class represents main panel - list of tasks and available plugins, buttons to
 * manage them.
 */
public final class ControllerPanel extends JPanel implements IControllerPanel
{
    private JPanel _graphPanel;

    /**
     * 
     * @uml.property name="_guiButtons"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private TaskControlPane _guiButtons;

    private JList _lstPlugins;

    /**
     * 
     * @uml.property name="_pluginMangerGUI"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private PluginManagerGUI _pluginMangerGUI;

    private JComponent _pnlInit;

    private JPanel _pnlPlugins;

    private JPanel _pnlTaskButtons;

    private JPanel _pnlTasks;

    private ProjectManagerGUI _projectManagerGUI;

    private SolutionManagerGUI _solutionManagerGUI;

    private StatusBar _statusBar;

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
        _guiButtons = new TaskControlPane(_taskManagerGUI);
        _statusBar = new StatusBar();

        _solutionManagerGUI.setStatusBar(_statusBar);
        _projectManagerGUI.setStatusBar(_statusBar);
        _taskManagerGUI.setStatusBar(_statusBar);
        _pluginMangerGUI.setStatusBar(_statusBar);

        this.setLayout(new BorderLayout());
        //this.add(getPnlManagerButtons(), BorderLayout.SOUTH);
        this.add(_statusBar.getStatusPanel(), BorderLayout.SOUTH);
        this.add(getPnlInit(), BorderLayout.CENTER);
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
    private JPanel getLstTasks()
    {
        if (_graphPanel == null) {
            _graphPanel = _taskManagerGUI.getGraphPanel();
            _graphPanel.setBorder(BorderFactory.createLoweredBevelBorder());
            _graphPanel.setPreferredSize(new Dimension(100, 200));
        }
        return _graphPanel;
    }

    private JComponent getPnlInit()
    {
        if (_pnlInit == null) {
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                    getPnlPlugins(), getPnlTasks());
            splitPane.setOneTouchExpandable(true);
            _pnlInit = splitPane;
            //			_pnlInit.setLayout(new GridLayout(1, 2));
            //			_pnlInit.add(getPnlPlugins());
            //			_pnlInit.add(getPnlTasks());
        }
        return _pnlInit;
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
            // TODO: remove			
            //			_pnlPlugins.add(getPnlPluginButtons(), BorderLayout.EAST);
            _pnlPlugins.add(getLstPlugins(), BorderLayout.CENTER);
            _pnlPlugins.setBorder(BorderFactory.createTitledBorder(null,
                    Messages.getString("TIT_PLUGINS"), TitledBorder.LEFT, //$NON-NLS-1$
                    TitledBorder.DEFAULT_POSITION));
        }
        return _pnlPlugins;
    }

    /**
     * This method initializes _pnlTaskButtons
     * 
     * @return JPanel
     */
    private JPanel getPnlTaskButtons()
    {
        if (_pnlTaskButtons == null) {
            _pnlTaskButtons = new TaskControlPane(_taskManagerGUI);
        }
        return _pnlTaskButtons;
    }

    /**
     * This method initializes _pnlTasks
     * 
     * @return JPanel
     */
    private JPanel getPnlTasks()
    {
        if (_pnlTasks == null) {
            _pnlTasks = new JPanel();
            _pnlTasks.setLayout(new BorderLayout());
            _pnlTasks.add(getPnlTaskButtons(), BorderLayout.NORTH);
            _pnlTasks.add(getLstTasks(), BorderLayout.CENTER);
            _pnlTasks.setBorder(BorderFactory.createTitledBorder(null,
                    Messages.getString("TIT_TASKS"), //$NON-NLS-1$
                    TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        }
        return _pnlTasks;
    }

    private static final Logger LOGGER = Logger.getLogger(ControllerFrame.class);
}