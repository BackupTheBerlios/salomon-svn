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

package salomon.engine.platform.data.tree;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.data.attribute.AttributeDescription;
import salomon.engine.platform.data.attribute.AttributeManager;
import salomon.engine.platform.data.attribute.AttributeSet;
import salomon.engine.solution.ShortSolutionInfo;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.data.tree.ITreeNode;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * Implementacja Tree manager`a
 * 
 * @author Mateusz Nowakowski
 *
 */
public final class TreeManager implements ITreeManager
{

    private static final Logger LOGGER = Logger.getLogger(TreeManager.class);

    private AttributeManager _attributeManager;

    private DBManager _dbManager;

    private ExternalDBManager _externalDBManager;

    private ShortSolutionInfo _solutionInfo;

    public TreeManager(AttributeManager attributeManager, DBManager dbManager,
            ShortSolutionInfo solutionInfo, ExternalDBManager externalDBManager)
    {
        _attributeManager = attributeManager;
        _dbManager = dbManager;
        _solutionInfo = solutionInfo;
        _externalDBManager = externalDBManager;
    }

    public void add(ITree tree) throws PlatformException
    {
        try {
            // saving it in DB
            ((Tree) tree).getInfo().save();
            _dbManager.commit();
        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new DBException(e);
        }
    }

    public ITreeNode createNode(ITree tree, IAttributeDescription description)
            throws PlatformException
    {
        return new TreeNode((Tree) tree, description, new TreeNodeInfo(
                _dbManager, true));
    }

    public ITree createTree() throws PlatformException
    {
        Tree tree = new Tree(_dbManager);
        tree.getInfo().setSolutionID(_solutionInfo.getId());
        return tree;
    }

    public ITree[] getAll() throws PlatformException
    {
        // enforcing getting all attribute sets
        return getTrees(-1);
    }

    public ITree getTree(int treeId) throws PlatformException
    {
        ITree[] trees = getTrees(treeId);
        ITree tree = trees.length > 0 ? trees[0] : null;
        return tree;
    }

    public ITree getTree(String name) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method TreeManager.getTree() not implemented yet!");
    }

    public void remove(ITree tree) throws PlatformException
    {
        // deleting tree set
        // others should be deleted cascadly
        try {
            ((Tree) tree).getInfo().delete();
            _dbManager.commit();
        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
        }
    }

    /**
     * Method builds the tree basing on its database definition.
     * 
     * @param tree
     * @throws PlatformException
     */
    private void buildTree(Tree tree) throws PlatformException
    {
        TreeInfo treeInfo = (TreeInfo) tree.getInfo();

        // loading nodes
        List<TreeNodeInfo> nodeInfos = treeInfo.getNodes();

        // loading attribute set
        AttributeSet attributeSet = (AttributeSet) _attributeManager.getAttributeSet(treeInfo.getAttributeSetID());
        // getting attribute desciptions
        AttributeDescription[] descriptions = attributeSet.getDesciptions();

        // putting attributes to the map to speed up searching
        Map<Integer, AttributeDescription> attributeMap = new HashMap<Integer, AttributeDescription>();
        for (AttributeDescription description : descriptions) {
            attributeMap.put(description.getDescriptionID(), description);
        }

        // getting root node
        // according to the sorting order, it is the first node in the list od nodes
        // it is removed to avoid complications while building tree
        TreeNodeInfo rootNodeInfo = nodeInfos.remove(0);
        TreeNode rootNode = new TreeNode(tree,
                attributeMap.get(rootNodeInfo.getAttributeItemID()),
                rootNodeInfo);
        // setting root node for given tree
        tree.setRootNode(rootNode);

        // creating nodes
        MultiMap nodesMap = new MultiHashMap();

        for (TreeNodeInfo nodeInfo : nodeInfos) {
            AttributeDescription description = attributeMap.get(nodeInfo.getAttributeItemID());
            TreeNode treeNode = new TreeNode(tree, description, nodeInfo);
            // put it in map?
            // putting node to the map
            // it is indexed by parent node id (thats why root node was removed, cause it's parent id == 0
            nodesMap.put(treeNode.getInfo().getParentNodeID(), treeNode);
        }

        // getting all child nodes starting from the root node
        connectNodes(nodesMap, rootNode);
    }

    @SuppressWarnings("unchecked")
    private void connectNodes(MultiMap nodesMap, TreeNode parentNode)
    {
        LOGGER.info("TreeManager.connectNodes()");
        if (!nodesMap.isEmpty()) {
            int parentNodeID = parentNode.getInfo().getId();
            LOGGER.debug("parentNodeID: " + parentNodeID);
            Collection<TreeNode> childNodes = (Collection<TreeNode>) nodesMap.remove(parentNodeID);
            if (childNodes != null) {
                for (Iterator iter = childNodes.iterator(); iter.hasNext();) {
                    TreeNode childNode = (TreeNode) iter.next();
                    LOGGER.debug("childNodeID: " + childNode.getInfo().getId());
                    parentNode.addChildNode(childNode,
                            childNode.getInfo().getParentEdgeValue());
                    connectNodes(nodesMap, childNode);
                }
            }
        }
    }

    private ITree[] getTrees(int treeID) throws PlatformException
    {
        List<ITree> trees = new LinkedList<ITree>();

        SQLSelect select = new SQLSelect();
        select.addTable(TreeInfo.TABLE_NAME + " t");
        select.addTable(TreeNodeInfo.TABLE_NAME + " tn");
        select.addColumn("t.tree_id");
        select.addColumn("t.attributeset_id");
        select.addColumn("t.root_node_id");
        select.addColumn("t.tree_name");
        select.addColumn("t.tree_info");
        select.addColumn("tn.node_id");
        select.addColumn("tn.parent_node_id");
        select.addColumn("tn.attribute_item_id");
        select.addColumn("tn.parent_edge_value");
        select.addColumn("tn.node_value");
        select.addCondition("t.tree_id = tn.tree_id");
        select.addCondition("t.solution_id = ", _solutionInfo.getId());
        // to avoid selecting default tree
        select.addCondition("t.tree_id > ", 0);
        // if attributeSetID >= 0 it means, that certain attribute set should be selected
        if (treeID > 0) {
            select.addCondition("t.tree_id = ", treeID);
        }
        select.addOrderBy("tn.tree_id");
        select.addOrderBy("tn.node_id");
        select.addOrderBy("tn.parent_node_id");

        ResultSet resultSet = null;
        // always set default
        int firstTreeID = -1;
        try {
            resultSet = _dbManager.select(select);
            Tree tree = null;
            while (resultSet.next()) {
                int tmpTreeID = resultSet.getInt("tree_id");
                // when tree_id changes, creating new Tree object
                if (tmpTreeID != firstTreeID) {
                    // processing next tree 
                    firstTreeID = tmpTreeID;
                    tree = (Tree) this.createTree();
                    tree.getInfo().load(resultSet);
                    trees.add(tree);
                }
                // loading nodes
                ((TreeInfo) tree.getInfo()).loadNodes(resultSet);
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.fatal("", e);
                }
            }
        }

        // building trees
        for (ITree tree : trees) {
            LOGGER.debug("building tree: " + tree.getName());
            buildTree((Tree) tree);
        }

        LOGGER.info("Trees list successfully loaded.");
        ITree[] treesArray = new ITree[trees.size()];
        return trees.toArray(treesArray);
    }
}
