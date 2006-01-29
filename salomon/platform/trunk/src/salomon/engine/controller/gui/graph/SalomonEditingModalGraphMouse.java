/*
 * Copyright (C) 2006 Salomon Team
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

package salomon.engine.controller.gui.graph;

import java.awt.event.InputEvent;

import edu.uci.ics.jung.visualization.SettableVertexLocationFunction;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;

public final class SalomonEditingModalGraphMouse extends EditingModalGraphMouse
{
	private GraphTaskManagerGUI _graphTaskManagerGUI;

	public SalomonEditingModalGraphMouse(GraphTaskManagerGUI graphTaskManagerGUI)
	{
		_graphTaskManagerGUI = graphTaskManagerGUI;
	}

	public void setVertexLocations(
			SettableVertexLocationFunction vertexLocations)
	{
		loadPluginsImpl();
		((SalomonEditingGraphMousePlugin) editingPlugin).setVertexLocations(vertexLocations);
	}

	//	@Override
	protected void loadPluginsImpl()
	{
		pickingPlugin = new PickingGraphMousePlugin();
		animatedPickingPlugin = new AnimatedPickingGraphMousePlugin();
		translatingPlugin = new TranslatingGraphMousePlugin(
				InputEvent.BUTTON1_MASK);
		scalingPlugin = new CrossoverScalingGraphMousePlugin(in, out);
		rotatingPlugin = new RotatingGraphMousePlugin();
		shearingPlugin = new ShearingGraphMousePlugin();
		editingPlugin = new SalomonEditingGraphMousePlugin(_graphTaskManagerGUI);

		add(scalingPlugin);
		setMode(Mode.EDITING);
	}

	@Override
	protected void loadPlugins()
	{
		// do nothing
	}

}
