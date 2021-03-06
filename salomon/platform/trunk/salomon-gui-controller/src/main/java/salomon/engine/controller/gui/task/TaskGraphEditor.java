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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.log4j.Logger;

import salomon.engine.task.ITask;
import salomon.engine.task.TaskInfo;

import salomon.platform.exception.PlatformException;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.DefaultToolTipFunction;
import edu.uci.ics.jung.graph.decorators.EdgeShape;
import edu.uci.ics.jung.graph.decorators.PickableVertexPaintFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.utils.GraphUtils;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;

public final class TaskGraphEditor extends JPanel
{
    private AbstractLayout _automaticLayout;

    /**
     * the graph
     */
    private Graph _graph;

    private StaticLayout _staticLayout;

    private DefaultSettableVertexLocationFunction _vertexLocations;

    /**
     * the visual component and renderer for the graph
     */
    private VisualizationViewer _visualazationViewer;

    private String instructions = "<html>"
        + "<h3>All Modes:</h3>"
        + "<ul>"
        + "<li>Right-click an empty area for <b>Create Task</b> popup"
        + "<li>Right-click on a Vertex for <b>Delete Task</b> popup"
        + "<li>Right-click on a Vertex for <b>Add Edge</b> menus <br>(if there are selected Vertices)"
        + "<li>Right-click on an Edge for <b>Delete Edge</b> popup"
        + "<li>Mousewheel scales with a crossover value of 1.0.<p>"
        + "     - scales the graph layout when the combined scale is greater than 1<p>"
        + "     - scales the graph view when the combined scale is less than 1"
        +

        "</ul>"
        + "<h3>Editing Mode:</h3>"
        + "<ul>"
        + "<li>Left-click an empty area to create a new Task"
        + "<li>Left-click on a Vertex and drag to another Task to create an Edge"
        + "</ul>"
        + "<h3>Picking Mode:</h3>"
        + "<ul>"
        + "<li>Mouse1 on a Task selects the vertex"
        + "<li>Mouse1 elsewhere unselects all Tasks"
        + "<li>Mouse1+Shift on a Task adds/removes Task selection"
        + "<li>Mouse1+drag on a Task moves all selected Tasks"
        + "<li>Mouse1+drag elsewhere selects Tasks in a region"
        + "<li>Mouse1+Shift+drag adds selection of Tasks in a new region"
        + "<li>Mouse1+CTRL on a Task selects the vertex and centers the display on it"
        + "</ul>" + "<h3>Transforming Mode:</h3>" + "<ul>"
        + "<li>Mouse1+drag pans the graph"
        + "<li>Mouse1+Shift+drag rotates the graph"
        + "<li>Mouse1+CTRL(or Command)+drag shears the graph" + "</ul>"
        + "</html>";

    /**
     * create an instance of a simple graph with popup controls to
     * create a graph.
     */
    public TaskGraphEditor(GraphTaskManagerGUI graphTaskManagerGUI)
    {

        // allows the precise setting of initial vertex locations
        _vertexLocations = new DefaultSettableVertexLocationFunction();

        // create a simple graph for the demo
        _graph = new DirectedSparseGraph();// SparseGraph();

        PluggableRenderer pluggableRenderer = new PluggableRenderer();
        _staticLayout = new StaticLayout(_graph);
        _staticLayout.initialize(new Dimension(600, 600), _vertexLocations);

        FRLayout frLayout = new FRLayout(_graph);
        frLayout.setRepulsionMultiplier(0.2); // distance between vertexes
        _automaticLayout = frLayout;
        _automaticLayout.initialize(new Dimension(600, 600), _vertexLocations);

        _visualazationViewer = new VisualizationViewer(_staticLayout, pluggableRenderer);
        _visualazationViewer.setBackground(Color.white);
        _visualazationViewer.setPickSupport(new ShapePickSupport());
        pluggableRenderer.setEdgeShapeFunction(new EdgeShape.QuadCurve());
        //        pluggableRenderer.setEdgeShapeFunction(new EdgeShape.Wedge(2));
        pluggableRenderer.setVertexStringer(new VertexStringer()
        {

            public String getLabel(ArchetypeVertex v)
            {
                return v.toString();
            }
        });
        pluggableRenderer.setVertexPaintFunction(new SalomonPickableVertexPaintFunction(pluggableRenderer));

        _visualazationViewer.setToolTipFunction(new DefaultToolTipFunction());

        final GraphZoomScrollPane panel = new GraphZoomScrollPane(
            _visualazationViewer);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        final EditingModalGraphMouse graphMouse = new SalomonEditingModalGraphMouse(
            graphTaskManagerGUI);

        // the EditingGraphMouse will pass mouse event coordinates to the
        // vertexLocations function to set the locations of the vertices as
        // they are created
        graphMouse.setVertexLocations(_vertexLocations);
        _visualazationViewer.setGraphMouse(graphMouse);
        //        graphMouse.add(new EditingPopupGraphMousePlugin(_vertexLocations));
        graphMouse.add(new SalomonEditingPopupGraphMousePlugin(
            _vertexLocations, graphTaskManagerGUI));
        graphMouse.setMode(ModalGraphMouse.Mode.EDITING);

        final ScalingControl scaler = new CrossoverScalingControl();
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                scaler.scale(_visualazationViewer, 1.1f,
                    _visualazationViewer.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                scaler.scale(_visualazationViewer, 0.9f,
                    _visualazationViewer.getCenter());
            }
        });

        JButton help = new JButton("Help");
        help.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(_visualazationViewer,
                    instructions);
            }
        });


        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        JComboBox modeBox = graphMouse.getModeComboBox();
        controls.add(modeBox);
        controls.add(help);
        add(controls, BorderLayout.SOUTH);
    }

    public void loadTasks(ITask[] tasks)
    {
        //TODO: clean below code!!!
        _graph.removeAllEdges();
        _graph.removeAllVertices();
        _vertexLocations.reset();
        Vertex previousVertex = null;

        _visualazationViewer.stop();
        _visualazationViewer.setGraphLayout(_automaticLayout);
        _visualazationViewer.restart();

        for (ITask task : tasks) {
            LOGGER.debug("adding task");
            TaskVertex vertex = new TaskVertex(task);
            _vertexLocations.setLocation(vertex, new Point(0, 0));
            _graph.addVertex(vertex);
            if (previousVertex != null) {
                GraphUtils.addEdge(_graph, previousVertex, vertex);
            }
            previousVertex = vertex;
        }
        _automaticLayout.restart();
        _visualazationViewer.getModel().restart();

        // copy locations from automatiLyouter to _vertexLocations
        for (Object o : _graph.getVertices()) {
            Vertex vertex = (Vertex) o;
            Point2D position = _automaticLayout.getLocation(vertex);
            _vertexLocations.setLocation(vertex, position);
        }

        _visualazationViewer.stop();
        _visualazationViewer.setGraphLayout(_staticLayout);
        _visualazationViewer.restart();
        _visualazationViewer.getModel().restart();
        _visualazationViewer.repaint();

    }

    public Graph getGraph()
    {
        return _graph;
    }

    /**
     * copy the visible part of the graph to a file as a jpeg image
     *
     * @param file
     */
    public void writeJPEGImage(File file)
    {
        int width = _visualazationViewer.getWidth();
        int height = _visualazationViewer.getHeight();

        BufferedImage bi = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();
        _visualazationViewer.paint(graphics);
        graphics.dispose();

        try {
            ImageIO.write(bi, "jpeg", file);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }


    private static class SalomonPickableVertexPaintFunction extends PickableVertexPaintFunction {

        /**
         * @param pi           specifies which vertices report as "picked"
         */
        public SalomonPickableVertexPaintFunction(PickedInfo pi)
        {
            super(pi, Color.BLACK, Color.RED, Color.ORANGE);
        }


        @Override
        public Paint getFillPaint(Vertex v)
        {
            if (pi.isPicked(v)) {
                return picked_paint;
            } else {
                TaskVertex taskVertex = (TaskVertex) v;
                ITask task = taskVertex.getTask();
                try {
                    TaskInfo taskInfo = null; //FIXME:(TaskInfo) task.getInfo();
                    String status = taskInfo.getStatus();
                    if (TaskInfo.FINISHED.equals(status)) {
                        return Color.GREEN;
                    } else if (TaskInfo.EXCEPTION.equals(status)) {
                        return Color.RED;
                    } else if (TaskInfo.INTERRUPTED.equals(status)) {
                        return Color.YELLOW;
                    } else {
                        return Color.BLUE;
                    }
                } catch (PlatformException e) {
                    LOGGER.error("", e);
                }
            }

            return Color.PINK;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(TaskGraphEditor.class);
}
