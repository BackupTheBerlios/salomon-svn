/*
 * Copyright (C) 2006 TreeVis Team
 *
 * This file is part of TreeVis.
 *
 * TreeVis is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * TreeVis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with TreeVis; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.treevis.result;

import java.awt.Component;
import java.awt.Point;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JPanel;

import pl.edu.agh.iisg.salomon.plugin.treevis.settings.TreeVisSettings;

import salomon.engine.controller.gui.graph.TaskVertex;
import salomon.engine.task.ITask;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.utils.GraphUtils;

import salomon.platform.IDataEngine;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeEdge;
import salomon.platform.data.tree.ITreeNode;
import salomon.platform.exception.PlatformException;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

public final class TreeVisResultComponent implements IResultComponent
{
    private Graph _graph;

    private JComponent _resultComponent;

    public Component getComponent(IResult result, IDataEngine dataEngine)
            throws PlatformException
    {
        if (_resultComponent == null) {
            _resultComponent = createComponent(dataEngine,
                    (TreeVisResult) result);
        }

        return _resultComponent;
    }

    public IResult getDefaultResult()
    {
        return new TreeVisResult();
    }

    private JComponent createComponent(IDataEngine dataEngine,
            TreeVisResult result) throws PlatformException
    {
        JComponent component = new JPanel();
        ITree tree = dataEngine.getTreeManager().getTree(result.getTreeName());
        createGraph(tree);

        //TODO: add graph component
        return component;
    }

    private void createGraph(ITree tree)
    {
        _graph = new DirectedSparseGraph();

        ITreeNode node = tree.getRootNode();
        if (node == null) {
            return;
        }

        createVertex(null, node);
    }

    private void createVertex(TreeNodeVertex parentVertex, ITreeNode node)
    {
        TreeNodeVertex vertex = new TreeNodeVertex(node);
        //        _vertexLocations.setLocation(vertex, new Point(0, 0));
        _graph.addVertex(vertex);
        if (parentVertex != null) {
            Edge edge = GraphUtils.addEdge(_graph, parentVertex, vertex);
            //TODO add label
        }

        ITreeEdge[] edges = node.getChildrenEdges();

        for (ITreeEdge edge : edges) {
            createVertex(vertex, edge.getChildNode());
        }
    }

}
