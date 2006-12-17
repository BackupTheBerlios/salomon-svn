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

package salomon.util.weka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.attribute.description.IEnumAttributeDescription;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeEdge;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.data.tree.ITreeNode;
import salomon.platform.exception.PlatformException;

import weka.core.Drawable;

/**
 * J48 pruned tree
 * ------------------
 * <p/>
 * wage-increase-first-year <= 2.5: bad (15.27/2.27)
 * wage-increase-first-year > 2.5
 * |   statutory-holidays <= 10: bad (10.77/4.77)
 * |   statutory-holidays > 10: good (30.96/1.0)
 * <p/>
 * Number of Leaves  : 	3
 * <p/>
 * Size of the tree : 	5
 * <p/>
 * digraph J48Tree {
 * N0 [label="wage-increase-first-year" ]
 * N0->N1 [label="<= 2.5"]
 * N1 [label="bad (15.27/2.27)" shape=box style=filled ]
 * N0->N2 [label="> 2.5"]
 * N2 [label="statutory-holidays" ]
 * N2->N3 [label="<= 10"]
 * N3 [label="bad (10.77/4.77)" shape=box style=filled ]
 * N2->N4 [label="> 10"]
 * N4 [label="good (30.96/1.0)" shape=box style=filled ]
 * }
 */
public final class TreeConverter
{
    private ITree _tree;

    private BufferedReader _reader;

    private Map<String, ITreeNode> _nodesMap = new HashMap<String, ITreeNode>();

    private ITreeManager _treeManager;

    private Map<String, IAttributeDescription> _attributesMap = new HashMap<String, IAttributeDescription>();

    private IAttributeDescription _outputAttributeDescription;

    private TreeConverter(ITree outputTree,
                          IAttributeSet attributeSet, Drawable wekaTree) throws Exception
    {
        String tree = wekaTree.graph();
        _tree = outputTree;
        _reader = new BufferedReader(new StringReader(tree));
        LOGGER.debug(tree);

        IAttributeDescription[] attributeDescriptions = attributeSet.getDesciptions();
        for (IAttributeDescription attributeDescription : attributeDescriptions) {
//            if (attributeDescription.getType() != AttributeType.ENUM) {
//                
//            }
            _attributesMap.put(attributeDescription.getName(),
                attributeDescription);

            if (attributeDescription.isOutput()) {
                _outputAttributeDescription = attributeDescription;
            }
        }

        parse();

    }

    private void parse() throws IOException, PlatformException
    {
        // read first line
        _reader.readLine();
        String line = _reader.readLine();
        if (line.trim().equals("}")) {
            return;
        }
        _tree.setRootNode(parseNode(line));
        List<String> edges = new ArrayList<String>();
        while (true) {
            line = _reader.readLine();
            if (line.trim().equals("}")) {
                break;
            }

            if (isEdge(line)) {
                edges.add(line);
            } else {
                _tree.addNode(parseNode(line));
            }
        }

        for (String edge : edges) {
            parseEdge(edge);
        }
    }

    private static boolean isEdge(String line)
    {
        return EDGE_REGEXP.matcher(line).matches();
    }

    private void parseEdge(String line) throws WekaTreeParseException
    {
        Matcher matcher = EDGE_REGEXP.matcher(line);
        if (!matcher.matches() && (matcher.groupCount() != 4)) {
            throw new WekaTreeParseException(
                "Invalid tree format: Cannot parse edge: " + line);
        }

        String parentNodeName = matcher.group(1);
        String childNodeName = matcher.group(2);
        String conditionLabel = matcher.group(3);
        ITreeNode parentNode = _nodesMap.get(parentNodeName);
        ITreeNode childNode = _nodesMap.get(childNodeName);
        //TODO: add support for other types of attributes
        IEnumAttributeDescription attributeDescription = (IEnumAttributeDescription) parentNode.getAttributeDescription();

        parentNode.addChildNode(childNode, findCondition(attributeDescription, conditionLabel));
    }

    private String findCondition(IEnumAttributeDescription attributeDescription, String label)
    {
        String result = null;
        String[] values = attributeDescription.getValues();
        for (String val : values) {
            if (label.contains(val)) {
                result = val;
            }
        }

        return result;
    }

    private ITreeNode parseNode(String line) throws PlatformException
    {
        Matcher matcher = NODE_REGEXP.matcher(line);
        if (!matcher.matches() && (matcher.groupCount() != 3)) {
            throw new WekaTreeParseException(
                "Invalid tree format: Cannot parse node: " + line);
        }

        String nodeName = matcher.group(1);
        String attributeName = matcher.group(2);
        IAttributeDescription attributeDescription = _attributesMap.get(attributeName);
        ITreeNode node = null;
        if (attributeDescription == null) {
            // todo soon flaw KRA add support for more types of attribute
            String leafValue = findCondition((IEnumAttributeDescription) _outputAttributeDescription, attributeName);
            node = _tree.createNode(_outputAttributeDescription);
            node.setLeafValue(leafValue);
        } else {
            node = _tree.createNode(attributeDescription);
        }
        _nodesMap.put(nodeName, node);

        return node;
    }

    public static void convert(ITree outputTree,
                               IAttributeSet attributeSet, Drawable wekaTree)
        throws PlatformException
    {
        if (wekaTree.graphType() != Drawable.TREE) {
            throw new PlatformException("Invalid type of graph! "
                + wekaTree.graphType());
        }

        //removeOldData(outputTree);

        try {
            new TreeConverter(outputTree, attributeSet, wekaTree);
        } catch (Exception e) {
            throw new PlatformException("Cannot covert weka tree!", e);
        }
    }

    public static void main(String[] args) throws WekaTreeParseException
    {
        String line = "N0 [label=\"wage-increase-first-year\" ]";
        Matcher matcher = NODE_REGEXP.matcher(line);
        if (!matcher.matches() && (matcher.groupCount() != 3)) {
            throw new WekaTreeParseException(
                "Invalid tree format: Cannot parse line: " + line);
        }

        String nodeName = matcher.group(1);
        String attributeName = matcher.group(2);

        System.out.println("NodeName = " + nodeName + ": attributeName = "
            + attributeName);

        line = "N0->N1 [label=\"<= 2.5\"]";
        matcher = EDGE_REGEXP.matcher(line);
        if (!matcher.matches() && (matcher.groupCount() != 4)) {
            throw new WekaTreeParseException(
                "Invalid tree format: Cannot parse line: " + line);
        }

        String node1Name = matcher.group(1);
        String node2Name = matcher.group(2);
        String condition = matcher.group(3);

        System.out.println("Node1 = " + node1Name + " Node2 = " + node2Name
            + " : condition = " + condition);

        //        _treeManager.createNode(_tree, null);

    }

    private static void removeOldData(ITree tree)
    {
        ITreeNode rootNode = tree.getRootNode();
        ITreeEdge[] treeEdges = rootNode.getChildrenEdges();
        for (ITreeEdge treeEdge : treeEdges) {
            rootNode.removeChildNode(treeEdge.getChildNode());
        }
    }

    private static final Pattern NODE_REGEXP = Pattern.compile("^(\\w+) +\\[label=\"(.+)\".*\\]$");

    private static final Pattern EDGE_REGEXP = Pattern.compile("^(\\w+)->(\\w+) +\\[label=\"(.+)\".*\\]$");

    private static final Logger LOGGER = Logger.getLogger(TreeConverter.class);
}
