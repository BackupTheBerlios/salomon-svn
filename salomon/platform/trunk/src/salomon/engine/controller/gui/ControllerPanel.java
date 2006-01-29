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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.controller.gui.graph.GraphTaskManagerGUI;

/**
 * Class represents main panel - list of tasks and available plugins, buttons to
 * manage them.
 */
public final class ControllerPanel extends JPanel implements IControllerPanel
{
	/**
	 * 
	 * @uml.property name="_guiButtons"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private GUIButtons _guiButtons;

	private JList _lstPlugins;

	private JPanel _graphPanel;

	/**
	 * 
	 * @uml.property name="_pluginMangerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private PluginManagerGUI _pluginMangerGUI;

	private JPanel _pnlInit;

	private JPanel _pnlPluginButtons;

	private JPanel _pnlPlugins;

	private JPanel _pnlTaskButtons;

	private JPanel _pnlTasks;

	private ProjectManagerGUI _projectManagerGUI;

	private SolutionManagerGUI _solutionManagerGUI;

	private StatusBar _statusBar;

	private int _strutHeight = 10;

	/**
	 * 
	 * @uml.property name="_taskManagerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private GraphTaskManagerGUI _taskManagerGUI;

	public ControllerPanel(SolutionManagerGUI solutionManagerGUI,
			ProjectManagerGUI projectManagerGUI, GraphTaskManagerGUI taskManagerGUI,
			PluginManagerGUI pluginMangerGUI)
	{
		_solutionManagerGUI = solutionManagerGUI;
		_projectManagerGUI = projectManagerGUI;
		_taskManagerGUI = taskManagerGUI;
		_pluginMangerGUI = pluginMangerGUI;	
		_guiButtons = new GUIButtons(_taskManagerGUI);
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

	private JPanel getPnlInit()
	{
		if (_pnlInit == null) {
			_pnlInit = new JPanel();
			_pnlInit.setLayout(new GridLayout(1, 2));
			_pnlInit.add(getPnlPlugins());
			_pnlInit.add(getPnlTasks());
		}
		return _pnlInit;
	}

	/**
	 * This method initializes _pnlPluginButtons
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlPluginButtons()
	{
		if (_pnlPluginButtons == null) {
			_pnlPluginButtons = new JPanel();
			_pnlPluginButtons.setLayout(new BoxLayout(_pnlPluginButtons,
					BoxLayout.Y_AXIS));
			_pnlPluginButtons.add(Box.createVerticalGlue());
			_pnlPluginButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlPluginButtons.add(_guiButtons.getBtnAdd());
			_pnlPluginButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlPluginButtons.add(_guiButtons.getBtnRemove());
			_pnlPluginButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlPluginButtons.add(_guiButtons.getBtnRemoveAll());
			_pnlPluginButtons.add(Box.createVerticalGlue());
		}
		return _pnlPluginButtons;
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
			_pnlPlugins.add(getPnlPluginButtons(), BorderLayout.EAST);
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
			_pnlTaskButtons = new JPanel();
			_pnlTaskButtons.setLayout(new BoxLayout(_pnlTaskButtons,
					BoxLayout.Y_AXIS));
			_pnlTaskButtons.add(_guiButtons.getBtnRun());
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
			_pnlTasks.add(getPnlTaskButtons(), BorderLayout.EAST);
			_pnlTasks.add(getLstTasks(), BorderLayout.CENTER);
			_pnlTasks.setBorder(BorderFactory.createTitledBorder(null,
					Messages.getString("TIT_TASKS"), //$NON-NLS-1$
					TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
		}
		return _pnlTasks;
	}

	private static final Logger LOGGER = Logger.getLogger(ControllerFrame.class);

	public GraphTaskManagerGUI getTaskEditionManager()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setTaskEditionManager(GraphTaskManagerGUI taskEditionManager)
	{
		// TODO Auto-generated method stub
		
	}
}