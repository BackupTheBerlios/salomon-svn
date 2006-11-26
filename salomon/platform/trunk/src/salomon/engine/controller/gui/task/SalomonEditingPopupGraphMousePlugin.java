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

package salomon.engine.controller.gui.task;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import salomon.engine.task.ITask;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.PickSupport;
import edu.uci.ics.jung.visualization.PickedState;
import edu.uci.ics.jung.visualization.SettableVertexLocationFunction;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;

public final class SalomonEditingPopupGraphMousePlugin
        extends
            AbstractPopupGraphMousePlugin
{
    private SettableVertexLocationFunction _vertexLocations;

    private GraphTaskManagerGUI _managerGUI;

    public SalomonEditingPopupGraphMousePlugin(
            SettableVertexLocationFunction vertexLocations,
            GraphTaskManagerGUI managerGUI)
    {
        _vertexLocations = vertexLocations;
        _managerGUI = managerGUI;
    }

    @Override
    protected void handlePopup(MouseEvent e)
    {
        final VisualizationViewer vv = (VisualizationViewer) e.getSource();
        final Layout layout = vv.getGraphLayout();
        final Graph graph = layout.getGraph();
        final Point2D p = e.getPoint();
        final Point2D ivp = vv.inverseViewTransform(e.getPoint());
        PickSupport pickSupport = vv.getPickSupport();
        if (pickSupport != null) {

            final Vertex vertex = pickSupport.getVertex(ivp.getX(), ivp.getY());
            final Edge edge = pickSupport.getEdge(ivp.getX(), ivp.getY());
            final PickedState pickedState = vv.getPickedState();
            JPopupMenu popup = new JPopupMenu();

            if (vertex != null) {
                Set picked = pickedState.getPickedVertices();
                if (picked.size() > 0) {
                    JMenu directedMenu = new JMenu("Create Edge");
                    popup.add(directedMenu);
                    for (Iterator iterator = picked.iterator(); iterator.hasNext();) {
                        final Vertex other = (Vertex) iterator.next();
                        directedMenu.add(new AbstractAction("[" + other + ","
                                + vertex + "]") {
                            public void actionPerformed(ActionEvent e)
                            {
                                Edge newEdge = new DirectedSparseEdge(other,
                                        vertex);
                                graph.addEdge(newEdge);
                                vv.repaint();
                            }
                        });
                    }
                }
                popup.add(new AbstractAction("Delete Task") {
                    public void actionPerformed(ActionEvent e)
                    {
                        pickedState.pick(vertex, false);
                        graph.removeVertex(vertex);
                        vv.repaint();
                    }
                });
                popup.add(new AbstractAction("Edit") {
                    public void actionPerformed(ActionEvent e)
                    {
                        TaskVertex taskVertex = (TaskVertex) vertex;
                        _managerGUI.editTask(taskVertex.getTask());
                    }
                });
                popup.addSeparator();
                popup.add(new AbstractAction("Show Settings") {
                    public void actionPerformed(ActionEvent e)
                    {
                        TaskVertex taskVertex = (TaskVertex) vertex;
                        _managerGUI.showSettingsPanel(taskVertex.getTask());
                    }
                });
                popup.add(new AbstractAction("Show Results") {
                    public void actionPerformed(ActionEvent e)
                    {
                        TaskVertex taskVertex = (TaskVertex) vertex;
                        _managerGUI.showResultPanel(taskVertex.getTask());
                    }
                });
            } else if (edge != null) {
                popup.add(new AbstractAction("Delete Edge") {
                    public void actionPerformed(ActionEvent e)
                    {
                        pickedState.pick(edge, false);
                        graph.removeEdge(edge);
                        vv.repaint();
                    }
                });
            } else {
                popup.add(new AbstractAction("Create Task") {
                    public void actionPerformed(ActionEvent e)
                    {
                        ITask task = _managerGUI.createTask();
                        if (task != null) {
                            Vertex newVertex = new TaskVertex(task);
                            _vertexLocations.setLocation(newVertex,
                                    vv.inverseTransform(p));
                            graph.addVertex(newVertex);
                            layout.restart();
                            vv.repaint();
                        }
                    }
                });
            }
            if (popup.getComponentCount() > 0) {
                popup.show(vv, e.getX(), e.getY());
            }
        }
    }
}
