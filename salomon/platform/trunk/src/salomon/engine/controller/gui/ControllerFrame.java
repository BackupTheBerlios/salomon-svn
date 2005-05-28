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
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.Starter;

/**
 * Class represents main program frame. All components are put on its content
 * pane.
 */
public final class ControllerFrame extends JFrame
{

	/**
	 * 
	 * @uml.property name="_controllerPanel"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ControllerPanel _controllerPanel;

	//	private String _resourcesDir;

	/**
	 * creates new ControllerFrame
	 */
	public ControllerFrame()
	{
		super();
		//		_resourcesDir = Config.getString("RESOURCES_DIR");
		initialize();
	}

	/**
	 * Called after project save/load/creation
	 * 
	 */
	public void refreshGui()
	{
		LOGGER.debug("refreshing GUI");
		_controllerPanel.refresh();
	}

	/**
	 * TODO: add comment.
	 * @param controllerPanel
	 */
	public void setControllerPanel(ControllerPanel controllerPanel)
	{
		_controllerPanel = controllerPanel;
	}

	/**
	 * TODO: add comment.
	 * @param toolBar
	 */
	public void setJToolBar(JToolBar toolBar)
	{
		getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * TODO: add comment.
	 * @param panel
	 */
	public void setMainPanel(JComponent panel)
	{
		//TODO: getContentPane().remove();
		getContentPane().add(panel, BorderLayout.CENTER);
	}

	private void exit()
	{
		Starter.exit();
	}

	/**
	 * This method initializes this
	 */
	private void initialize()
	{
		getContentPane().setLayout(new BorderLayout());
		this.setSize(600, 500);
		//
		// center frame
		//
		Point location = new Point();
		location.x = (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2;
		location.y = (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2;
		this.setLocation(location);
		this.setTitle(Messages.getString("APP_NAME")); //$NON-NLS-1$        
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				exit();
			}
		});
		LOGGER.debug("initialize end");
	}

	private static final Logger LOGGER = Logger.getLogger(ControllerFrame.class);
}