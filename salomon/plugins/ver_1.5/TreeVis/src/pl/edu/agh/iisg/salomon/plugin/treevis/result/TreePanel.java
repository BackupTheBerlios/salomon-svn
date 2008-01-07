package pl.edu.agh.iisg.salomon.plugin.treevis.result;
/*
 *  Copyright (C) 2006 Salomon Team
 *
 *  This file is part of Salomon.
 *
 *  Salomon is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  Salomon is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with ${project_name}; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.*;

import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeEdge;
import salomon.platform.data.tree.ITreeNode;

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.decorators.*;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseTree;
import edu.uci.ics.jung.utils.UserDataContainer;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.contrib.TreeLayout;
import edu.uci.ics.jung.visualization.control.*;

public class TreePanel extends JPanel
{
    private SparseTree graph;
    private VisualizationViewer vv;

    public TreePanel(ITree tree)
    {

        ITreeNode rootNode = tree.getRootNode();
        TreeNodeVertex root = new TreeNodeVertex(rootNode);
        // create a simple graph for the demo
        graph = new SparseTree(root);

        createVertices(rootNode, root);

        //createEdges(vertices);

        final PluggableRenderer pr = new PluggableRenderer();

        pr.setVertexPaintFunction(new PickableVertexPaintFunction(pr, Color.black, Color.white, Color.yellow));
        pr.setEdgePaintFunction(new PickableEdgePaintFunction(pr, Color.black, Color.cyan));
        pr.setGraphLabelRenderer(new DefaultGraphLabelRenderer(Color.cyan, Color.cyan));

        pr.setVertexShapeFunction(new EllipseVertexShapeFunction());
        pr.setVertexStringer(new VertexStringer()
        {
            public String getLabel(ArchetypeVertex v)
            {
                return v.toString();
            }
        });
        pr.setEdgeStringer(new EdgeStringer()
        {
            public String getLabel(ArchetypeEdge e)
            {
                return (String) e.getUserDatum("edgeValue");
            }
        });

        Layout layout = new TreeLayout(graph);

        vv = new VisualizationViewer(layout, pr, new Dimension(400, 400));
        vv.setPickSupport(new ShapePickSupport());

        pr.setEdgeShapeFunction(new EdgeShape.QuadCurve());
        vv.setBackground(Color.white);

        // add a listener for ToolTips
        vv.setToolTipFunction(new DefaultToolTipFunction());

        Container content = this;
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        content.add(panel);

        final PluggableGraphMouse graphMouse = new PluggableGraphMouse();
        graphMouse.add(new PickingGraphMousePlugin());
        graphMouse.add(new ScalingGraphMousePlugin(new ViewScalingControl(), InputEvent.CTRL_MASK));
        graphMouse.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0));

        vv.setGraphMouse(graphMouse);

        final ScalingControl scaler = new CrossoverScalingControl();

        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                scaler.scale(vv, 1 / 1.1f, vv.getCenter());
            }
        });

        JPanel scaleGrid = new JPanel(new GridLayout(1, 0));
        scaleGrid.setBorder(BorderFactory.createTitledBorder("Zoom"));

        JPanel controls = new JPanel();
        scaleGrid.add(plus);
        scaleGrid.add(minus);
        controls.add(scaleGrid);

        content.add(controls, BorderLayout.SOUTH);
    }

    private void createVertices(ITreeNode rootNode, TreeNodeVertex parentVertex)
    {
        ITreeEdge[] childrenEdges = rootNode.getChildrenEdges();
        for (ITreeEdge childrenEdge : childrenEdges) {
            ITreeNode childNode = childrenEdge.getChildNode();
            TreeNodeVertex childVertex = new TreeNodeVertex(childNode);
            graph.addVertex(childVertex);
            DirectedSparseEdge edge = new DirectedSparseEdge(parentVertex, childVertex);
            edge.setUserDatum("edgeValue", childrenEdge.getValue(), new UserDataContainer.CopyAction.Clone());
            graph.addEdge(edge);
            createVertices(childNode, childVertex);
        }
    }
}

